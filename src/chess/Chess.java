package chess;

public class Chess {
    private final Board board = new Board();
    
    private boolean playing;
    private Team turn = Team.WHITE;
    
    // 임시 변수들
    /** @see #removeIllegalMoves() */
    private final Board tempBoard = new Board();
    /** @see #removeIllegalMoves() */
    private final Position tempPos = new Position();
    
    
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
        
        // 캐슬링 (이동이 된다는 것 자체가 캐슬링 가능이므로 따로 검사는 안 한다)
        if (piece instanceof King king) {
            if (from.equals(5, 1)) {
                // 킹 사이드
                if (to.equals(7, 1)) {
                    Piece rook = getPiece(8, to.y);
                    board.setPiece(8, to.y, null);
                    board.setPiece(6, to.y, rook);
                    ((OnMovedListener) rook).onMoved(this);
                }
                // 퀸 사이드
                else if (to.equals(3, 1)) {
                    Piece rook = getPiece(1, to.y);
                    board.setPiece(1, to.y, null);
                    board.setPiece(4, to.y, rook);
                    ((OnMovedListener) rook).onMoved(this);
                }
            }
        }
        
        if (piece instanceof OnMovedListener p) {
            p.onMoved(this);
        }
        
        // 다음 이동 계산 + 체크 확인
        boolean check = calculateMoves(board, null);
        
        // 턴 교체
        turn = turn == Team.BLACK ? Team.WHITE : Team.BLACK;
        
        // 잘못된 이동 제거 + 최종 판정
        MoveResult result = removeIllegalMoves(check);
        if (result == MoveResult.CHECKMATE || result == MoveResult.STALEMATE) {
            endGame();
        }
        return result;
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
    
    /** 말들의 이동 가능 위치 중 이동했을 때 우리 편이 체크당하는 경우를 제거한다. {@link #tempBoard}에서 이동 위치를 전부 시뮬레이션하여, 체크가 된다면 제거한다.
     * <p> 체크, 체크메이트, 스테일메이트 또는 셋 다 아닌 상태를 최종적으로 결정한다. </p>
     * @param isCheckNow 현재 체크 상태. 체크메이트/스테일메이트 구분에 쓰인다.
     * @return 체크/체크메이트/스테일메이트라면 그에 맞는 {@link MoveResult}, 아니면 {@link MoveResult#SUCCESS} */
    private MoveResult removeIllegalMoves(boolean isCheckNow) {
        // board를 tempBoard에 복사
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                tempBoard.setPiece(i, j, board.getPiece(i, j));
            }
        }
        
        System.out.println("\nremoveIllegalMoves():"); // debug
        int moveCount = 0; // 말 하나를 계산한 후 최종 이동 경로의 개수 저장 (to check checkmate)
        
        // 다음에 이동할 팀의 모든 말에 대해
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null || piece.team != turn) continue;
            
            System.out.printf("  piece: %s\n", piece); // debug
            
            // 말을 모든 이동 가능 위치로 이동시키고 체크 여부 확인
            tempPos.set(piece.position); // 이전 위치 백업
            var iter = piece.moves.iterator();
            while (iter.hasNext()) {
                Position to = iter.next();
                System.out.printf("    %s - ", to); // debug
                
                // 이동
                tempBoard.setPiece(piece.position, null);
                Piece tempPiece = tempBoard.getPiece(to); // 롤백하려고 얻어놓음
                tempBoard.setPiece(to, piece);
                
                // 체크라면 이동 가능 위치 제거
                boolean check = calculateMoves(tempBoard, turn.opponent());
                if (check) {
                    iter.remove();
                    System.out.println("Invalid"); // debug
                } else {
                    System.out.println("Valid"); // debug
                }
                
                // 다시 원래대로
                tempBoard.setPiece(to, tempPiece);
                tempBoard.setPiece(tempPos, piece);
            }
            
            // 최종 이동 위치 개수 저장
            if (moveCount == 0) {
                moveCount += piece.getMoveCount();
            }
        }
        System.out.println("end\n"); // debug
        
        // 현재 체크 상태, 이동 가능 위치 개수에 따라 최종 결과 리턴
        if (moveCount == 0) return isCheckNow ? MoveResult.CHECKMATE : MoveResult.STALEMATE;
        else return isCheckNow ? MoveResult.CHECK : MoveResult.SUCCESS;
    }
    
    
    /** {@code board.toString()} */
    public String boardToString() { return board.toString(); }
}
