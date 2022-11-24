package chess;

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
    
    public Position plus(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Position))
            return false;

        Position pos = (Position) obj;
        return pos.x == x && pos.y == y;
    }
    @Override
    public String toString() { return String.format("(%d, %d)", x, y); }
}
