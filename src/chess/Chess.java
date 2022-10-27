package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];

    private boolean isPlaying;
    private Team turn = Team.White;
    
    
    public Piece getPiece(int x, int y) {
        return board[y][x];
    }
    public Piece getPiece(Position pos) {
        return getPiece(pos.x, pos.y);
    }
    private void setPiece(Position pos, Piece piece) {
        board[pos.y][pos.x] = piece;
    }
    
    public Team getTurn() { return turn; }
    
    
    public Chess() {
        for (int i = 0; i < 8; i++)
            board[6][i] = new Pawn(board, new Position(i, 6), Team.Black);
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(board, new Position(i, 1), Team.White);
    }
    
    /** 로컬 멀티플레이 게임을 시작한다. */
    public void play() {
        isPlaying = true;
    }
    
    
    /** 말을 from 위치에서 to 위치로 이동시킨다. 그리고 턴을 상대방에게 넘긴다.
     *  @return 이동에 성공했으면 true, 실패했으면 false */
    public boolean move(Position from, Position to) throws IllegalStateException, NullPointerException {
        if (!isPlaying)
            throw new IllegalStateException("게임이 시작되지 않았습니다.");
        if (getPiece(from) == null)
            throw new NullPointerException("움직일 기물이 없습니다.");
        if (getPiece(from).getTeam() != turn)
            throw new IllegalStateException("상대방의 턴입니다.");
        
        Piece piece = getPiece(from);
        setPiece(to, piece);
        piece.pos = to;
        setPiece(from, null);
        piece.onMoved();
        
        turn = piece.getTeam() == Team.White ? Team.Black : Team.White;
        
        return true;
    }
}
