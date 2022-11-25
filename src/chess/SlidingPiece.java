package chess;

public abstract class SlidingPiece extends Piece {
    SlidingPiece(Chess board, Team team, Position position) {
        super(board, team, position);
    }
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        final Position[] directions = getDirections();
        
        for (int i = 0; i < directions.length; i++) {
            int x = position.x + directions[i].x;
            int y = position.y + directions[i].y;
            
            while (Chess.inBoard(x, y)) {
                Piece piece = board.getPiece(x, y);
                if (piece == null) {
                    moves.add(new Position(x, y));
                } else if (piece.getTeam() != team) {
                    moves.add(new Position(x, y));
                    break;
                }
                x += directions[i].x;
                y += directions[i].y;
            }
        }
    }
    
    protected abstract Position[] getDirections();
}
