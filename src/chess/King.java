package chess;

/** 킹 */
public class King extends Piece {
    King(Team team, Position position) { super(team, position); }
    
    
    @Override
    void calculateMoves(Board board) {
        super.calculateMoves(board);
        
        // 8방향 1칸에 말이 없거나 상대편 말이 있으면 이동
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                
                int x = position.x + dx;
                int y = position.y + dy;
                if (!Chess.inBoard(x, y)) continue;
                
                Piece piece = board.getPiece(x, y);
                if (piece == null || piece.getTeam() != team) {
                    moves.add(new Position(x, y));
                }
            }
        }
    }
}
