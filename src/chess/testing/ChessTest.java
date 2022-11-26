package chess.testing;

import chess.*;

class ChessTest {
    public static void main(String[] args) {
        final Chess chess = new Chess();
        chess.startGame();
        
        printBoard(chess);
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            while (true) {
                System.out.printf("\n%s\nmove (x1 y1 x2 y2) - ", chess.getTurn());
                moveWithResult(chess, scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            }
        }
    }
    
    // 이동 + 체스판 + MoveResult
    private static void moveWithResult(Chess chess, int x1, int y1, int x2, int y2) {
        MoveResult result = chess.move(new Position(x1, y1), new Position(x2, y2));
        printBoard(chess);
        System.out.println(result);
    }
    
    // 체스판 콘솔에 출력
    private static void printBoard(Chess board) {
        System.out.println("   1  2  3  4  5  6  7  8");
        for (int y = 8; y >= 1; y--) {
            System.out.printf("%d ", y);
            for (int x = 1; x <= 8; x++) {
                Piece piece = board.getPiece(x, y);
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
            System.out.printf("%d\n", y);
        }
        System.out.println("   1  2  3  4  5  6  7  8");
    }
}
