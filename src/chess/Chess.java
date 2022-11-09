package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(Position pos) {
        return board[pos.y-1][pos.x-1];
    }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(int x, int y) {
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
            setPiece(2, y, new Knight(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(2, y)));
            setPiece(3, y, new Bishop(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(3, y)));
            setPiece(4, y, new Queen(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(4, y)));
            setPiece(5, y, new King(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(5, y)));
            setPiece(6, y, new Bishop(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(6, y)));
            setPiece(7, y, new Knight(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(7, y)));
            setPiece(8, y, new Rook(this, y == 8 ? Team.BLACK : Team.WHITE, new Position(8, y)));
        }
        for (int x = 1; x <= 8; x++) {
            setPiece(x, 7, new Pawn(this, Team.BLACK, new Position(x, 7)));
            setPiece(x, 2, new Pawn(this, Team.WHITE, new Position(x, 2)));
        }
        
        calculateMoves();
    }
    
    
    /** from 위치에 있는 말을 to 위치로 옮긴다.
     * @return 이동 성공 여부 + 실패했다면 실패 원인
     * @see MoveResult */
    public MoveResult move(Position from, Position to) {
        if (!inBoard(from) || !inBoard(to))
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
    boolean inBoard(Position pos) {
        return 1 <= pos.x && pos.x <= 8 && 1 <= pos.y && pos.y <= 8;
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
        
        moveWithResult(chess, 4, 2, 4, 3);
        moveWithResult(chess, 3, 1, 6, 4);
        moveWithResult(chess, 5, 7, 5, 6);
    }
    
    private static void moveWithResult(Chess chess, int x1, int y1, int x2, int y2) {
        System.out.printf("(%d, %d) -> (%d, %d)\n", x1, y1, x2, y2);
        MoveResult result = chess.move(new Position(x1, y1), new Position(x2, y2));
        if (result != MoveResult.Success) System.out.println(result);
        
        for (int y = 8; y >= 1; y--) {
            for (int x = 1; x <= 8; x++) {
                Piece piece = chess.getPiece(x, y);
                if (piece == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(piece.getTeam() == Team.BLACK ? "b" : "w");
                    if (piece instanceof Pawn)
                        System.out.print("P");
                    else if (piece instanceof Rook)
                        System.out.print("R");
                    else if (piece instanceof King)
                        System.out.print("K");
                    else if (piece instanceof Bishop)
                        System.out.print("B");
                    else if (piece instanceof Knight)
                        System.out.print("N");
                    else if (piece instanceof Queen)
                        System.out.print("Q");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------");
    }
}
