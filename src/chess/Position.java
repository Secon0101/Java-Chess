package chess;

public class Position {
    public int x, y;
    
    public Position(int x, int y) { 
        this.x = x; 
        this.y = y;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position && ((Position)obj).x == x && ((Position)obj).y == y;
    }
}
