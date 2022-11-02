package chess;

import java.util.List;

public abstract class Piece {
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
    
    /** 현재 말이 이동 가능한 위치 리스트를 계산한다. */
    abstract void calculateMoves();
}
