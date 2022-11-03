package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    // 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8)
    private Piece getPiece(Position pos) {
        return board[pos.y-1][pos.x-1];
    }
    private Piece getPiece(int x, int y) {
        return board[y-1][x-1];
    }
    private void setPiece(Position pos, Piece piece) {
        board[pos.y-1][pos.x-1] = piece;
    }
    private void setPiece(int x, int y, Piece piece) {
        board[y-1][x-1] = piece;
    }
    
    
    public Chess() {
        // 말 놓기
        for (int y = 8; y >= 1; y -= 7) {
            setPiece(1, y, new Rook(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(1, y)));
            setPiece(5, y, new King(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(5, y)));
            setPiece(8, y, new Rook(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(8, y)));
        }
        for (int x = 1; x <= 8; x++) {
            setPiece(x, 7, new Pawn(this, Team.BLACK, new Position(1, 2)));
            setPiece(x, 2, new Pawn(this, Team.WHITE, new Position(1, 2)));
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
    
    
    /** 위치(좌표)가 8x8 체스판 밖에 있는지 판단한다. */
    boolean isOutOfBoard(Position pos) {
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
    
    
    // debug
    public static void main(String[] args) {
        Chess chess = new Chess();
        
        Position from = new Position(1, 2);
        Position to = new Position(1, 3);
        MoveResult result = chess.move(from, to);
        
        for (int y = 8; y >= 1; y--) {
            for (int x = 1; x <= 8; x++) {
                Piece piece = chess.getPiece(x, y);
                if (piece == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(piece.getTeam() == Team.BLACK ? "B" : "W");
                    if (piece instanceof Pawn)
                        System.out.print("P");
                    else if (piece instanceof Rook)
                        System.out.print("R");
                    else if (piece instanceof King)
                        System.out.print("K");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
