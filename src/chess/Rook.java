package chess;

import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {
    Rook(Piece[][] board, Position position, Team team) {
        super(board, position, team);
    }
    
    @Override
    public List<Position> getMovement() {
        List<Position> movement = new LinkedList<>();
        for (int y = 0; y < 8; y++) {
            if (y != pos.y) {
                movement.add(new Position(pos.x, y));
            }
        }
        for (int x = 0; x < 8; x++) {
            if (x != pos.x) {
                movement.add(new Position(x, pos.y));
            }
        }
        return movement;
    }
    
    @Override
    void onMoved() {
        
    }
}
