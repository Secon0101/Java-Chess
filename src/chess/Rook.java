package chess;

public class Rook extends Piece {
    Rook(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (Position dir : new Position[] {
            Position.up, Position.down, Position.left, Position.right
        }) {
            Position pos = position.add(dir);
            while (chess.inBoard(pos) && (chess.getPiece(pos) == null || chess.getPiece(pos).getTeam() != team)) {
                moves.add(pos);
                pos = pos.add(dir);
            }
        }
    }
}
