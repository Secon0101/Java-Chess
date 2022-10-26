package chess;

import java.util.List;

public abstract class Piece {
    protected Team team;
    
    
    public Team getTeam() { return team; }
    
    
    public Piece(Team team) {
        this.team = team;
    }
    
    @Override
    public abstract String toString();
    
    public abstract List<Position> getMovement();
}
