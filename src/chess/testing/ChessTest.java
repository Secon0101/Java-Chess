package chess.testing;

import chess.*;

class ChessTest {
    private static final Chess chess = new Chess();
    
    public static void main(String[] args) {
        chess.addMoveResultListener(moveResultListener);
        chess.setAI(Team.BLACK, Team.WHITE);
        chess.startGame(Team.WHITE);
    }
    
    private static final MoveResultListener moveResultListener = result -> {
        System.out.println(chess);
    };
}
