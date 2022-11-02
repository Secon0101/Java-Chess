package chess;

import java.util.List;

abstract class Piece {
    protected final Chess chess;
    protected final Team team;
    protected Position position;
    protected List<Position> moves;
    
    
    Piece(Chess chess, Team team, Position position) {
        this.chess = chess;
        this.team = team;
        this.position = position;
    }
    
    void setPosition(Position pos) {
        position = pos;
    }
    
    List<Position> getMoves() {
        return moves;
    }
    
    abstract void calculateMoves();
}
