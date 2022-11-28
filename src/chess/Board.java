package chess;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/** <b>이차원 배열</b> + <b>말 리스트</b> + <b>왕 위치</b>를 하나로 묶은 클래스
 * <p> 말을 저장하는 방식에는 두 가지가 있다. 이차원 배열 방식은 <b>특정 위치를 참조</b>할 때 빠르고, 리스트 방식은 <b>모든 말을 순회</b>할 때 빠르다.
 * 이 둘을 모두 활용하기 위해 만든 클래스이다. </p> */
class Board {
    private final Piece[][] board = new Piece[8][8];
    protected final List<Piece> pieces = new LinkedList<>();
    private final King[] kings = new King[Team.values().length];
    
    
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    Piece getPiece(int x, int y) { return board[y-1][x-1]; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    Piece getPiece(Position pos) { return board[pos.y-1][pos.x-1]; }
    
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8)
     * @throws Chess.AttackedKingException {@code (x, y)} 위치에 킹이 있다는 건 뭔가 잘못된 것이다. */
    void setPiece(int x, int y, Piece piece) throws Chess.AttackedKingException {
        if (getPiece(x, y) != null) {
            if (getPiece(x, y).equals(piece)) return; // 변화 없음
            
            // 전에 있던 말 제거
            if (piece != null && piece instanceof King) {
                throw new Chess.AttackedKingException();
            }
            pieces.remove(getPiece(x, y));
        }
        
        // 말 추가
        board[y-1][x-1] = piece;
        if (piece != null) {
            piece.position.set(x, y);
            if (!pieces.contains(piece))
                pieces.add(piece);
        }
        
        // 킹이면 따로 저장
        if (piece instanceof King king) {
            kings[king.team.ordinal()] = king;
        }
    }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8)
     * @throws Chess.AttackedKingException {@code (x, y)} 위치에 킹이 있다는 건 뭔가 잘못된 것이다. */
    void setPiece(Position pos, Piece piece) throws Chess.AttackedKingException {
        try { setPiece(pos.x, pos.y, piece); }
        catch (Chess.AttackedKingException e) { throw e; }
    }
    
    /** 그 팀의 킹의 위치를 리턴한다. */
    Position getKingPosition(Team team) { return kings[team.ordinal()].position; }
    
    /** 보드에 있는 모든 말을 순회하는 이터레이터를 리턴한다. */
    Iterable<Piece> getPieceIterator() {
        return iterable;
    }
    private final Iterable<Piece> iterable = new Iterable<Piece>() {
        @Override
        public Iterator<Piece> iterator() {
            return pieces.iterator();
        }
    };
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   1  2  3  4  5  6  7  8\n");
        for (int y = 8; y >= 1; y--) {
            sb.append(String.format("%d ", y));
            for (int x = 1; x <= 8; x++) {
                Piece piece = getPiece(x, y);
                if (piece == null) {
                    sb.append("   ");
                } else {
                    sb.append(piece.getTeam() == Team.BLACK ? "b" : "w");
                    if (piece instanceof Pawn)
                        sb.append("P ");
                    else if (piece instanceof Rook)
                        sb.append("R ");
                    else if (piece instanceof King)
                        sb.append("K ");
                    else if (piece instanceof Bishop)
                        sb.append("B ");
                    else if (piece instanceof Knight)
                        sb.append("N ");
                    else if (piece instanceof Queen)
                        sb.append("Q ");
                }
            }
            sb.append(String.format("%d\n", y));
        }
        sb.append("   1  2  3  4  5  6  7  8");
        return sb.toString();
    }
}
