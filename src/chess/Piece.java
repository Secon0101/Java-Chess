package chess;

import java.util.List;

public abstract class Piece {
    protected Piece[][] board;
    protected Position pos;
    protected Team team;
    
    public Team getTeam() { return team; }
    
    
    Piece(Piece[][] board, Position position, Team team) {
        this.board = board;
        pos = position;
        this.team = team;
    }
    
    
    /** 현재 기물이 움직일 수 있는 모든 위치를 구한다. */
    public abstract List<Position> getMovement();
    
    abstract void onMoved();
}
