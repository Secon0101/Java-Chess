package chess;

/** (x, y) 좌표를 저장할 수 있는 클래스 */
public class Position {
    public int x;
    public int y;
    
    
    public Position() { }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    /** {@link (x, y)}만큼 더한다. */
    public void add(int x, int y) { this.x += x; this.y += y; }
    /** {@link (x, y)} 값을 설정한다. */
    public void set(int x, int y) { this.x = x; this.y = y; }
    /** {@link (x, y)} 값을 설정한다. */
    public void set(Position pos) { x = pos.x; y = pos.y; }
    /** {@link (x, y)} 값이 동일한지 체크한다. */
    public boolean equals(int x, int y) { return this.x == x && this.y == y; }
    /** 자신의 복사본을 리턴한다. */
    public Position copy() { return new Position(x, y); }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Position)) return false;

        Position pos = (Position) obj;
        return pos.x == x && pos.y == y;
    }
    @Override
    public String toString() { return String.format("(%d, %d)", x, y); }
}
