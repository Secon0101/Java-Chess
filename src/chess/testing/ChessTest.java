package chess.testing;

import chess.*;

class ChessTest {
    public static void main(String[] args) {
        final Chess chess = new Chess();
        chess.startGame();
        
        System.out.println(chess.boardToString());
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
        System.out.println(chess.boardToString());
        System.out.println(result);
    }
}
