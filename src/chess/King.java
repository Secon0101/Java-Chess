package chess;

/** 킹 */
public class King extends Piece implements OnMovedListener {
    King(Team team, Position position) { super(team, position); }
    
    private boolean notMoved = true;
    private final int originY = team == Team.BLACK ? 8 : 1;
    
    
    @Override
    boolean calculateMoves(Board board) {
        moves.clear();
        final Position kingPos = board.getKingPosition(team.opponent());
        boolean check = false;
        
        // 8방향 1칸에 말이 없거나 상대편 말이 있으면 이동
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                
                int x = position.x + dx;
                int y = position.y + dy;
                if (!Chess.inBoard(x, y)) continue;
                
                Piece piece = board.getPiece(x, y);
                if (piece == null || piece.team != team) {
                    Position pos = new Position(x, y);
                    moves.add(pos);
                    check = check || pos.equals(kingPos);
                }
            }
        }
        
        // 캐슬링
        if (notMoved && position.equals(5, originY)) {
            // 킹 사이드
            if (board.getPiece(6, originY) == null
             && board.getPiece(7, originY) == null
             && board.getPiece(8, originY) instanceof Rook rook
             && rook.notMoved()) {
                Position pos = new Position(7, originY);
                moves.add(pos);
                check = check || pos.equals(kingPos);
            }
            // 퀸 사이드
            if (board.getPiece(4, originY) == null
             && board.getPiece(3, originY) == null
             && board.getPiece(2, originY) == null
             && board.getPiece(1, originY) instanceof Rook rook
             && rook.notMoved()) {
                Position pos = new Position(3, originY);
                moves.add(pos);
                check = check || pos.equals(kingPos);
            }
        }
        
        return check;
    }
    
    /** 킹이 한 번도 움직이지 않았으면 {@code true}. 캐슬링 체크 용 */
    boolean moved() { return notMoved; }
    
    @Override
    public void onMoved(Chess chess) {
        notMoved = false;
    }
}
