package chess;

/** 룩 */
public class Rook extends SlidingPiece {
    Rook(Team team, Position position) { super(team, position); }
    
    // + 4방향
    private final Position[] directions = {
        new Position(1, 0),
        new Position(0, 1),
        new Position(-1, 0),
        new Position(0, -1),
    };
    
    @Override
    protected Position[] getDirections() { return directions; }
}
