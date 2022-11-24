import chess.Chess;

public class Main {
    public static void main(String[] args) {
        final Chess chess = new Chess();
        new MyFrame(chess);
        chess.startGame();
    }
}
