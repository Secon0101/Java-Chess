package chess;

/** 슬라이딩(특정 방향으로 막히지 않을 때까지 자유롭게 이동)하는 말. 룩, 비숍, 퀸 */
abstract class SlidingPiece extends Piece {
    SlidingPiece(Team team, Position position) { super(team, position); }
    
    @Override
    boolean calculateMoves(Board board) {
        moves.clear();
        final Position kingPos = board.getKingPosition(team.opponent());
        boolean check = false;
        
        // 이동 방향으로, 상대 말을 만나서 막히기 전까지의 모든 칸을 이동 가능
        for (Position direction : getDirections()) {
            int x = position.x + direction.x;
            int y = position.y + direction.y;

            while (Chess.inBoard(x, y)) {
                Piece piece = board.getPiece(x, y);
                if (piece == null) {
                    moves.add(new Position(x, y));
                } else {
                    if (piece.team != team) {
                        Position pos = new Position(x, y);
                        moves.add(pos);
                        check = check || pos.equals(kingPos);
                    }
                    break;
                }
                x += direction.x;
                y += direction.y;
            }
        }
        
        return check;
    }
    
    /** 말이 이동할 수 있는 방향(단위벡터)들을 리턴한다.
     * 슬라이딩 말들은 이동 방향만 다르기 때문에 하위 클래스에 구현을 맡긴다.
     * @implSpec <pre> {@code
     * private final Position[] directions;
     * return directions;
     * } </pre> */
    protected abstract Position[] getDirections();
}
