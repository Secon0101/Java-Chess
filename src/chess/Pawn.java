package chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return team == Team.Black ? "♟" : "♙";
    }
    
    @Override
    public List<Position> getMovement() {
        List<Position> movement = new LinkedList<Position>();
        movement.add(new Position(0, 1));
        return movement;
    }
}
