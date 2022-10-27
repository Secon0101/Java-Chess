import chess.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.play();
        
        printBoard(chess);
        
        Position from = new Position(0, 1);
        Position to = new Position(0, 3);
        
        List<Position> movement = chess.getPiece(from).getMovement();
        if (movement.contains(to)) {
            chess.move(from, to);
            printBoard(chess);
        }
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
