package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    public Piece[][] getBoard() {
        return board.clone();
    }
    
    // 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8)
    private Piece getPiece(Position pos) {
        return board[pos.x-1][pos.y-1];
    }
    private Piece getPiece(int x, int y) {
        return board[x-1][y-1];
    }
    private void setPiece(Position pos, Piece piece) {
        board[pos.y-1][pos.x-1] = piece;
    }
    private void setPiece(int x, int y, Piece piece) {
        board[x-1][y-1] = piece;
    }
    
    
    public Chess() {
        for (int x = 1; x <= 8; x++) {
            setPiece(x, 7, new Pawn(this, Team.Black, new Position(x, 6)));
            setPiece(x, 2, new Pawn(this, Team.White, new Position(x, 1)));
        }
        
        calculateMoves();
    }
    
    
    /** from 위치에 있는 말을 to 위치로 옮긴다. */
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
    
    
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    private boolean isOutOfBoard(Position pos) {
        return !(1 <= pos.x && pos.x <= 8 && 1 <= pos.y && pos.y <= 8);
    }
    
    /** 판 위에 있는 모든 말들의 이동 가능 위치를 계산해서, 그 말에 저장해 놓는다. */
    private void calculateMoves() {
        Piece piece;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                piece = getPiece(x, y);
                if (piece != null) {
                    piece.calculateMoves();
                }
            }
        }
    }
}
