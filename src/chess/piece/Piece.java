package chess.piece;

import chess.Team;

public abstract class Piece {
    protected Team team;
    
    
    public Team getTeam() { return team; }
    
    
    public Piece(Team team) {
        this.team = team;
    }
}
