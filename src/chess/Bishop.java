package chess;

public class Bishop extends SlidingPiece {
    Bishop(Chess board, Team team, Position position) {
        super(board, team, position);
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
