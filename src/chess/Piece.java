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
    
    
    public abstract List<Position> getMovement();
    
    @Override
    public abstract String toString();
}
