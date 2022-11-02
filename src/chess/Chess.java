package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    private Piece getPiece(Position pos) { return board[pos.x][pos.y]; }
    private Piece getPiece(int x, int y) { return board[x][y]; }
    private void setPiece(Position pos, Piece piece) { board[pos.y][pos.x] = piece; }
    private void setPiece(int x, int y, Piece piece) { board[x][y] = piece; }
    
    
    public Chess() {
        for (int x = 0; x < 8; x++) {
            setPiece(x, 6, new Pawn(this, Team.Black, new Position(x, 6)));
            setPiece(x, 1, new Pawn(this, Team.White, new Position(x, 1)));
        }
        
        calculateMoves();
    }
    
    
    public MoveResult move(Position from, Position to) {
        if (isOutOfBoard(from) || isOutOfBoard(to))
            return MoveResult.InvalidPosition;
        
        Piece piece = getPiece(from);
        if (piece == null)
            return MoveResult.NoPiece;
        if (!piece.getMoves().contains(to))
            return MoveResult.InvalidMove;
        
        setPiece(from, null);
        setPiece(to, piece);
        piece.setPosition(to);
        
        calculateMoves();
        
        return MoveResult.Success;
    }
    
    
    private boolean isOutOfBoard(Position pos) {
        return 0 > pos.x || pos.x >= 8 || 0 > pos.y || pos.y >= 8;
    }
    
    private void calculateMoves() {
        Piece piece;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                piece = getPiece(x, y);
                if (piece != null) {
                    piece.calculateMoves();
                }
            }
        }
    }
}
