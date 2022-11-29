package chess;

/** 폰 */
public class Pawn extends Piece implements OnMovedListener {
    Pawn(Team team, Position position) { super(team, position); }
    
    private final int forward = team == Team.BLACK ? -1 : 1;
    private boolean isFirstMove = true;
    
    
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
            if (piece != null && piece.team != team) {
                Position pos = new Position(x, y);
                moves.add(pos);
                check = check || pos.equals(kingPos);
            }
        }
        
        return check;
    }
    
    @Override
    public void onMoved(Chess chess) { // OnMovedListener
        isFirstMove = false;
        
        // 프로모션
        if (position.y == (team == Team.BLACK ? 1 : 8)) {
            chess.promote(this);
        }
    }
}
