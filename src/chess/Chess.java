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
    
    
    public int move(Position from, Position to) {
        if (!isPlaying) return -1;
        
        Piece piece = getPiece(from);
        setPiece(to, piece);
        piece.pos = to;
        setPiece(from, null);
        
        turn = piece.getTeam() == Team.White ? Team.Black : Team.White;
        
        return 0;
    }
}
