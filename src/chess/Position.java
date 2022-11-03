package chess;

class Position {
    int x;
    int y;
    
    
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        
        Position other = (Position) obj;
        return other.x == x && other.y == y;
    }
    
    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }
    public Position multiply(int n) {
        return new Position(x * n, y * n);
    }
}
