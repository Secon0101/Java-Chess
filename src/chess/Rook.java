package chess;

/** 룩 */
public class Rook extends SlidingPiece implements OnMovedListener {
    Rook(Team team, Position position) { super(team, position); }
    
    // + 4방향
    private final Position[] directions = {
        new Position(1, 0),
        new Position(0, 1),
        new Position(-1, 0),
        new Position(0, -1),
    };
    private boolean notMoved = true;
    
    
    @Override
    protected Position[] getDirections() { return directions; }

    /** 룩이 한 번도 움직이지 않았으면 {@code true}. 캐슬링 체크 용 */
    boolean notMoved() { return notMoved; }
    
    @Override
    public void onMoved(Chess chess) {
        notMoved = false;
    }
}
