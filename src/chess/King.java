package chess;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
    King(Piece[][] board, Position position, Team team) {
        super(board, position, team);
    }
    
    @Override
    public List<Position> getMovement() {
        List<Position> movement = new LinkedList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1 && y != 0; x++) {
                if (pos.add(x, y).in(8, 8)) {
                    movement.add(pos.add(x, y));
                }
            }
        }
        return movement;
    }
    
    @Override
    void onMoved() {
        
    }
}
