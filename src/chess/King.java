package chess;

public class King extends Piece {
    King(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                
                Position pos = new Position(position.x + dx, position.y + dy);
                if (chess.getPiece(pos) == null || chess.getPiece(pos).getTeam() != team) {
                    addMove(pos);
                }
            }
        }
    }
}
