package chess;

public class Pawn extends Piece {
    private final Position forward = team == Team.WHITE ? new Position(0, 1) : new Position(0, -1);
    
    
    Pawn(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    
    @Override
    void calculateMoves() {
        moves.clear();
        
        moves.add(position.add(forward));
    }
}
