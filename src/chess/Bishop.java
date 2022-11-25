package chess;

public class Bishop extends SlidingPiece {
    Bishop(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    private final Position[] directions = {
        new Position(1, 1),
        new Position(-1, 1),
        new Position(-1, -1),
        new Position(1, -1),
    };
    
    @Override
    protected Position[] getDirections() {
        return directions;
    }
}
