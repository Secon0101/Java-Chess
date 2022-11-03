package chess;

public class Pawn extends Piece {
    private final Position forward = team == Team.BLACK ? Position.down : Position.up;
    
    
    Pawn(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }
    
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        Position pos = position.add(forward);
        if (chess.inBoard(pos) && chess.getPiece(pos) == null) {
            moves.add(pos);
        }
    }
}
