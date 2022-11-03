package chess;

import java.util.LinkedList;
import java.util.List;

public abstract class Piece {
    protected final Chess chess;
    protected final Team team;
    protected Position position;
    protected List<Position> moves = new LinkedList<>();
    
    
    Piece(Chess chess, Team team, Position position) {
        this.chess = chess;
        this.team = team;
        this.position = position;
    }
    
    
    public Team getTeam() {
        return team;
    }
    public List<Position> getMoves() {
        return moves;
    }
    void setPosition(Position pos) {
        position = pos;
    }
    
    
    /** 현재 말이 이동 가능한 위치 리스트를 새로 계산한다. */
    void calculateMoves() {
        moves.clear();
    }
}
