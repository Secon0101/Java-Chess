package chess;

public class Chess {
    private final Board board = new Board();
    
    private boolean playing;
    private Team turn = Team.WHITE;
    
    
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
        
        calculateMoves();
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
        MoveResult result = calculateMoves();
        System.out.println(result);
        
        // 턴 교체
        turn = turn == Team.BLACK ? Team.WHITE : Team.BLACK;
        
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
    
    /** 판 위에 있는 모든 말들의 현재 이동 가능 위치를 계산해서, 그 말에 저장해 놓는다.
     * <p> 체크 등 여부도 계산한다. </p>
     * @return 체크 / 체크메이트 / 스테일메이트면 그에 맞는 {@link MoveResult}, 아니면 {@link MoveResult#SUCCESS} */
    private MoveResult calculateMoves() {
        boolean check = false;
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null) continue;
            
            if (piece.calculateMoves(board) == true) {
                check = true;
            }
        }
        
        return check ? MoveResult.CHECK : MoveResult.SUCCESS;
    }
    
    
    /** 혹시라도 게임 오류로 인해 킹이 잡히는 경우 터지는 예외이다. */
    public static class AttackedKingException extends RuntimeException { }
}
