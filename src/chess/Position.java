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
    
    public boolean in(Position rect) {
        return 0 <= x && x < rect.x && 0 <= y && y < rect.y;
    }
    public boolean in(int x, int y) {
        return 0 <= this.x && this.x < x && 0 <= this.y && this.y < y;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position pos && pos.x == x && pos.y == y;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
