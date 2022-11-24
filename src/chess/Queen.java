package chess;

public class Queen extends SlidingPiece {
    Queen(Chess board, Team team, Position position) {
        super(board, team, position);
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
