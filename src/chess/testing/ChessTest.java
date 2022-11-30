package chess.testing;

import chess.*;

class ChessTest {
    public static void main(String[] args) {
        final Chess chess = new Chess();
        chess.startGame();
        
        System.out.println(chess);
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            while (true) {
                System.out.printf("\n%s\nmove (x1y1x2y2) - ", chess.getTurn());
                int num = scanner.nextInt();
                moveWithResult(chess, num / 1000, num / 100 % 10, num / 10 % 10, num % 10);
            }
        }
    }
    
    // 이동 + 체스판 + MoveResult
    private static void moveWithResult(Chess chess, int x1, int y1, int x2, int y2) {
        MoveResult result = chess.move(new Position(x1, y1), new Position(x2, y2));
        System.out.println(chess);
        System.out.println(result);
    }
}
