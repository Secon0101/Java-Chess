package chess;

import java.util.List;
import java.util.LinkedList;

public class Piece {
    protected final Chess board;
    protected final List<Position> moves = new LinkedList<>();
    protected final Team team;
    protected final Position position;
    
    
    Piece(Chess board, Team team, Position position) {
        this.board = board;
        this.team = team;
        this.position = position;
    }
    
    
    public Team getTeam() { return team; }
    Position getPosition() { return position; }
    
    void calculateMoves() {
        moves.clear();
    }
    
    boolean hasMove(Position pos) {
        return moves.contains(pos);
    }
}
