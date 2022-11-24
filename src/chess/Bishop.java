package chess;

public class Bishop extends Piece {
    Bishop(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    private final Position[] directions = new Position[] {
        Position.upleft, Position.upright, Position.downleft, Position.downright
    };
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (Position dir : directions) {
            Position pos = position.add(dir);
            while (chess.inBoard(pos) && (chess.getPiece(pos) == null || chess.getPiece(pos).getTeam() != team)) {
                moves.add(pos);
                pos = pos.add(dir);
            }
        }
    }
}
