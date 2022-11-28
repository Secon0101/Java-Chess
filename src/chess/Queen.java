package chess;

/** 퀸 */
public class Queen extends SlidingPiece {
    Queen(Team team, Position position) { super(team, position); }
    
    // 8방향
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
    protected Position[] getDirections() { return directions; }
}
