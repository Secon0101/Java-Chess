package chess;

/** 비숍 */
public class Bishop extends SlidingPiece {
    Bishop(Team team, Position position) { super(team, position); }
    
    // X 4방향
    private final Position[] directions = {
        new Position(1, 1),
        new Position(-1, 1),
        new Position(-1, -1),
        new Position(1, -1),
    };
    
    @Override
    protected Position[] getDirections() { return directions; }
}
