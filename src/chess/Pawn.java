package chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    Pawn(Piece[][] board, Position position, Team team) {
        super(board, position, team);
    }
    
    
    @Override
    public List<Position> getMovement() {
        // 임시
        return new LinkedList<>(List.of(
            new Position(0, 3)
        ));
    }
    
    @Override
    public String toString() {
        return team == Team.Black ? "♟" : "♙";
    }
}
