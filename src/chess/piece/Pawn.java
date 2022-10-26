package chess.piece;

import chess.*;
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
        return null;
    }
}
