package chess;

public class King extends Piece {
    public King(Chess board, Team team, Position position) {
        super(board, team, position);
    }

    @Override
    void calculateMoves() {
        super.calculateMoves();

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0)
                    continue;

                int x = position.x + dx;
                int y = position.y + dy;
                if (Chess.inBoard(x, y)) {
                    Piece piece = board.getPiece(x, y);
                    if (piece == null || piece.team != team) {
                        moves.add(new Position(x, y));
                    }
                }
            }
        }
    }
}
