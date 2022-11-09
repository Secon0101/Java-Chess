package chess;

/** {@code (x, y)} */
public class Position {
    public int x;
    public int y;
    
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    public static final Position up = new Position(0, 1);
    public static final Position down = new Position(0, -1);
    public static final Position left = new Position(-1, 0);
    public static final Position right = new Position(1, 0);
    public static final Position upleft = new Position(-1, 1);
    public static final Position upright = new Position(1, 1);
    public static final Position downleft = new Position(-1, -1);
    public static final Position downright = new Position(1, -1);
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        
        Position other = (Position) obj;
        return other.x == x && other.y == y;
    }
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }
    public Position multiply(int n) {
        return new Position(x * n, y * n);
    }
}
