package chess;

public class Knight extends Piece {
    Knight(Chess chess, Team team, Position position) {
        super(chess, team, position);
    }

    @Override
    void calculateMoves() {
        super.calculateMoves();

        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                if (Math.abs(dx) + Math.abs(dy) != 3)
                    continue;

                int x = position.x + dx;
                int y = position.y + dy;
                if (Chess.inBoard(x, y)) {
                    Piece piece = chess.getPiece(x, y);
                    if (piece == null || piece.getTeam() != team) {
                        moves.add(new Position(x, y));
                    }
                }
            }
        }
    }
}
