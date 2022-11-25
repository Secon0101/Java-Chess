package chess;

public class Queen extends SlidingPiece {
    Queen(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    private final Position[] directions = {
        new Position(1, 0),
        new Position(1, 1),
        new Position(0, 1),
        new Position(-1, 1),
        new Position(-1, 0),
        new Position(-1, -1),
        new Position(0, -1),
        new Position(1, -1),
    };
    
    @Override
    protected Position[] getDirections() {
        return directions;
    }
}
