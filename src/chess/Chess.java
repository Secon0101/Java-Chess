package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];
    
    
    public Chess() {
        for (int i = 0; i < 8; i++)
            board[6][i] = new Pawn(Team.Black);
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(Team.White);
    }
    
    
    public void play() {
        
    }
    
    
    public Piece getPiece(int x, int y) { return board[y][x]; }
    
    public void move(Position from, Position to) {
        board[to.y][to.x] = board[from.y][from.x];
        board[from.y][from.x] = null;
    }
    
    
    // debug
    public static void main(String[] args) {
        Chess chess = new Chess();
        printBoard(chess);
        Position pos = new Position(0, 1);
        chess.move(new Position(0, 1), new Position(0, 3));
        printBoard(chess);
    }
    
    private static void printBoard(Chess chess) {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (chess.getPiece(x, y) != null) {
                    System.out.print(chess.getPiece(x, y));
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
