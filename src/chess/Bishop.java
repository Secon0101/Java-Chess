package chess;

public class Bishop extends Piece {
    Bishop(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (Position dir : new Position[] {
            Position.upleft, Position.upright, Position.downleft, Position.downright
        }) {
            Position pos = position.add(dir);
            while (chess.inBoard(pos) && (chess.getPiece(pos) == null || chess.getPiece(pos).getTeam() != team)) {
                moves.add(pos);
                pos = pos.add(dir);
            }
        }
    }
}
