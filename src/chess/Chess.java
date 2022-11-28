package chess;

public class Chess {
    private final Board board = new Board();
    
    private boolean playing;
    private Team turn = Team.WHITE;
    
    /** @see #removeIllegalMoves() */
    private final Board tempBoard = new Board();
    
    
    public Chess() {
        // 말 놓기
        for (int y = 8; y >= 1; y -= 7) {
            Team team = y == 8 ? Team.BLACK : Team.WHITE;
            placePiece(new Rook(team, new Position(1, y)));
            placePiece(new Knight(team, new Position(2, y)));
            placePiece(new Bishop(team, new Position(3, y)));
            placePiece(new Queen(team, new Position(4, y)));
            placePiece(new King(team, new Position(5, y)));
            placePiece(new Bishop(team, new Position(6, y)));
            placePiece(new Knight(team, new Position(7, y)));
            placePiece(new Rook(team, new Position(8, y)));
            for (int x = 1; x <= 8; x++) {
                placePiece(new Pawn(team, new Position(x, y == 8 ? 7 : 2)));
            }
        }
        
        calculateMoves(board, null);
    }
    
    
    /** 어느 쪽 팀의 차례인지 반환한다. */
    public Team getTurn() { return turn; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(int x, int y) { return board.getPiece(x, y); }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(Position pos) { return board.getPiece(pos); }
    
    
    /** 게임을 시작한다. 이후로 {@code move()} 메서드를 사용할 수 있다. */
    public void startGame() {
        playing = true;
    }
    
    /** 게임을 끝낸다. */
    public void endGame() {
        playing = false;
    }
    
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(int x, int y) {
        return 1 <= x && x <= 8 && 1 <= y && y <= 8;
    }
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(Position position) {
        return 1 <= position.x && position.x <= 8 && 1 <= position.y && position.y <= 8;
    }
    
    /** from 위치에 있는 말을 to 위치로 옮긴다.
     * @return 이동 성공 여부 + 실패했다면 실패 원인
     * @see MoveResult */
    public MoveResult move(Position from, Position to) {
        if (!playing) return MoveResult.NOT_PLAYING;
        if (!inBoard(from) || !inBoard(to)) return MoveResult.OUT_OF_BOARD;
        if (to.equals(board.getKingPosition(turn.opponent()))) return MoveResult.ATTACKED_KING;
        
        Piece piece = getPiece(from);
        if (piece == null) return MoveResult.NO_PIECE;
        if (piece.team != turn) return MoveResult.INVALID_TURN;
        if (!piece.hasMove(to)) return MoveResult.INVALID_MOVE;
        
        // 말 옮기기
        board.setPiece(from, null);
        board.setPiece(to, piece);
        
        if (piece instanceof OnMovedListener p) {
            p.onMoved(this);
        }
        
        // 다음 이동 계산
        boolean check = calculateMoves(board, null);
        
        // 턴 교체
        turn = turn == Team.BLACK ? Team.WHITE : Team.BLACK;
        
        // 잘못된 이동 방지
        removeIllegalMoves();
        
        return check ? MoveResult.CHECK : MoveResult.SUCCESS;
    }
    
    /** 폰을 퀸으로 승격시킨다. */
    void promote(Pawn pawn) {
        placePiece(new Queen(pawn.team, pawn.position));
    }
    
    /** 말을 보드 위에 놓는다. */
    private void placePiece(Piece piece) {
        board.setPiece(piece.position, piece);
    }
    
    /** 주어진 판 위에 있는 모든 말들의 현재 이동 가능 위치를 계산해서, 그 말에 저장해 놓는다. 특정한 팀만 계산할 수도 있다.
     * <p> 하는 김에 체크 여부도 계산한다. </p>
     * @param board 계산할 보드
     * @param team 계산할 팀. null이면 모든 팀 계산
     * @return 체크 여부 */
    private boolean calculateMoves(Board board, Team team) {
        boolean check = false;
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null) continue;
            if (team != null && piece.team != team) continue;
            
            if (piece.calculateMoves(board) == true) {
                check = true;
            }
        }
        
        return check;
    }
    
    /** 말들의 이동 가능 위치 중 이동했을 때 우리 편이 체크당하는 경우를 제거한다.
     * <p> {@link #tempBoard}에서 이동 위치를 전부 시뮬레이션하여, 체크가 된다면 제거한다. </p> */
    private void removeIllegalMoves() {
        // board를 tempBoard에 복사
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempBoard.setPiece(i, j, board.getPiece(i, j));
            }
        }
        
        // 다음에 이동할 팀의 모든 말에 대해
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null || piece.team != turn) continue;
            
            System.out.printf("계산 대상: %s\n", piece); // debug~~~~~~~~~~~~~~~
            
            // 말을 모든 이동 가능 위치로 이동시키고 체크 여부 확인
            var iter = piece.moves.iterator();
            while (iter.hasNext()) {
                Position to = iter.next();
                System.out.printf("  위치: %s\n", to); // debug~~~~~~~~~~~~~~~
                
                tempBoard.setPiece(piece.position, null);
                tempBoard.setPiece(to, piece);
                piece.position.set(to);
                
                System.out.println(tempBoard); // debug~~~~~~~~~~~~~~~
                
                // 체크라면 이동 가능 위치 제거
                System.out.printf("%s\n", turn);
                if (calculateMoves(tempBoard, turn.opponent())) {
                    iter.remove();
                    System.out.println("    Invalid"); // debug!!!!!!!!!!!!!!!!
                } else {
                    System.out.println("    Valid"); // debug!!!!!!!!!!!!!!!!
                }
            }
        }
    }
    
    
    /** 혹시라도 게임 오류로 인해 킹이 잡히는 경우 터뜨릴 예외이다. */
    public static class AttackedKingException extends RuntimeException { }
    
    public String boardToString() { return board.toString(); }
}
