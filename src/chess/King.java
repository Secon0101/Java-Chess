package chess;

public class King extends Piece {
    King(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (Position dir : new Position[] {
            Position.upleft, Position.up, Position.upright, Position.left, Position.right, Position.downleft, Position.down, Position.downright
        }) {
            Position pos = position.add(dir);
            if (chess.inBoard(pos) && (chess.getPiece(pos) == null || chess.getPiece(pos).getTeam() != team)) {
                moves.add(pos);
            }
        }
    }
}
