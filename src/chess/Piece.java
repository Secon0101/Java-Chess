package chess;

import java.util.LinkedList;

public abstract class Piece {
    protected final Chess chess;
    protected final Team team;
    protected Position position;
    protected LinkedList<Position> moves = new LinkedList<>();
    
    
    Piece(Chess chess, Team team, Position position) {
        this.chess = chess;
        this.team = team;
        this.position = position;
    }
    
    
    public Team getTeam() {
        return team;
    }
    public LinkedList<Position> getMoves() {
        return moves;
    }
    public Position getPosition() {
        return position;
    }
    void setPosition(Position pos) {
        position = pos;
    }
    
    
    /** 현재 말이 이동 가능한 위치 리스트를 새로 계산한다. */
     void calculateMoves() {
        moves.clear();
    }
}
