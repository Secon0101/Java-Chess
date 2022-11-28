package chess;

/** (x, y) 좌표를 저장할 수 있는 클래스 */
public class Position {
    public int x;
    public int y;
    
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    public void add(int x, int y) { this.x += x; this.y += y; }
    public void set(int x, int y) { this.x = x; this.y = y; }
    public void set(Position pos) { x = pos.x; y = pos.y; }
    
    
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
