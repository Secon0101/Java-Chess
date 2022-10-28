package chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    private boolean isFirstMove = true;
    
    
    Pawn(Piece[][] board, Position position, Team team) {
        super(board, position, team);
    }
    
    
    @Override
    public List<Position> getMovement() {
        List<Position> movement = new LinkedList<>();
        if (1 <= pos.y && pos.y < 7-1) {
            movement.add(pos.add(0, 1));
        }
        if (isFirstMove) {
            movement.add(pos.add(0, 2));
        }
        return movement;
    }
    
    @Override
    void onMoved() {
        isFirstMove = false;
    }
}
