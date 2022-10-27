package chess;

public class Position {
    public int x, y;
    
    public Position(int x, int y) { 
        this.x = x; 
        this.y = y;
    }
    
    
    public Position add(Position pos) {
        return new Position(x + pos.x, y + pos.y);
    }
    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position && ((Position)obj).x == x && ((Position)obj).y == y;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
