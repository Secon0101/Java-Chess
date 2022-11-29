package chess;

/** 폰 */
public class Pawn extends Piece implements OnMovedListener {
    Pawn(Team team, Position position) { super(team, position); }
    
    private final int forward = team == Team.BLACK ? -1 : 1;
    /** 한 번도 움직이지 않았다면 {@code true}, 이때 폰은 앞으로 2칸까지 움직일 수 있다. {@link #onMoved}가 실행될 때 {@code false}가 된다. */
    private boolean isFirstMove = true;
    /** 바로 전 턴에 움직인 말이라면 {@code true}. {@link #onMoved}가 실행될 때 {@code true}가 되고, {@link Chess#move()} 실행 이후 {@code false}가 된다. 앙파상 체크용. */
    boolean wasMovedRightBefore = false;
    
    
    /** 폰의 전진 방향을 리턴한다. */
    int forward() { return forward; }
    
    
    @Override
    boolean calculateMoves(Board board) {
        moves.clear();
        final Position kingPos = board.getKingPosition(team.opponent());
        boolean check = false;
        
        // 앞 1칸 또는 2칸에 말이 없다면 이동
        for (int i = 1; i <= (isFirstMove ? 2 : 1); i++) {
            int x = position.x;
            int y = position.y + forward * i;
            
            if (Chess.inBoard(x, y)) {
                if (board.getPiece(x, y) == null) {
                    moves.add(new Position(x, y));
                } else {
                    break;
                }
            }
        }
        
        // 옆쪽에 상대편 말이 있으면 이동
        for (int dx = -1; dx <= 1; dx += 2) {
            int x = position.x + dx;
            int y = position.y + forward;
            if (!Chess.inBoard(x, y)) continue;
            
            Piece piece = board.getPiece(x, y);
            if (piece != null) {
                if (piece.team != team) {
                    Position pos = new Position(x, y);
                    moves.add(pos);
                    check = check || pos.equals(kingPos);
                }
            } else {
                // 앙파상
                y = position.y;
                piece = board.getPiece(x, y);
                if (piece == null || piece.team == team) continue;
                
                if (piece instanceof Pawn pawn) {
                    if (pawn.wasMovedRightBefore) {
                        moves.add(new Position(x, y + forward));
                    }
                }
            }
        }
        
        return check;
    }
    
    @Override
    public void onMoved(Chess chess) { // OnMovedListener
        isFirstMove = false;
        wasMovedRightBefore = true;
        
        // 프로모션
        if (position.y == (team == Team.BLACK ? 1 : 8)) {
            chess.promote(this);
        }
    }
}
