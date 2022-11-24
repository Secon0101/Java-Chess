package chess;

public class Pawn extends Piece implements OnMovedListener {
    Pawn(Chess board, Team team, Position position) {
        super(board, team, position);
    }
    
    private final int forward = team == Team.BLACK ? -1 : 1;
    private boolean isFirstMove = true;
    
    @Override
    void calculateMoves() {
        super.calculateMoves();
        
        for (int i = 1; i <= (isFirstMove ? 2 : 1); i++) {
            int x = position.x;
            int y = position.y + forward * i;
            if (Chess.inBoard(x, y) && board.getPiece(x, y) == null) {
                moves.add(new Position(x, y));
            }
        }
        
        for (int dx = -1; dx <= 1; dx += 2) {
            int x = position.x + dx;
            int y = position.y + forward;
            if (Chess.inBoard(x, y)) {
                Piece piece = board.getPiece(x, y);
                if (piece != null && piece.team != team) {
                    moves.add(new Position(x, y));
                }
            }
        }
    }
    
    @Override
    public void onMoved() {
        isFirstMove = false;
        
        if (position.y == (team == Team.BLACK ? 1 : 8)) {
            board.promote(this);
        }
    }
}
