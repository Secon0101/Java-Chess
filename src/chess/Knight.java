package chess;

/** 나이트 */
public class Knight extends Piece {
    Knight(Team team, Position position) {
        super(team, position);
    }

    @Override
    boolean calculateMoves(Board board) {
        moves.clear();
        final Position kingPos = board.getKingPosition(team.opponent());
        boolean check = false;
        
        // 앞으로 2칸 + 옆으로 1칸에 말이 없거나 상대편 말이 있으면 이동
        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                if (Math.abs(dx) + Math.abs(dy) != 3) continue; // 1+2=3
                
                int x = position.x + dx;
                int y = position.y + dy;
                if (!Chess.inBoard(x, y)) continue;
                
                Piece piece = board.getPiece(x, y);
                if (piece == null || piece.team != team) {
                    Position pos = new Position(x, y);
                    moves.add(pos);
                    return !check && pos.equals(kingPos);
                }
            }
        }
        
        return check;
    }
}
