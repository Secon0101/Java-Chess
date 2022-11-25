package chess;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class Piece {
    protected final Chess chess;
    protected final List<Position> moves = new LinkedList<>();
    protected final Team team;
    protected final Position position;
    
    
    Piece(Chess chess, Team team, Position position) {
        this.chess = chess;
        this.team = team;
        this.position = position;
    }
    
    
    /** 말의 팀을 리턴한다. */
    public Team getTeam() { return team; }
    /** 말의 위치를 리턴한다. */
    public Position getPosition() { return position; }
    /** 말이 이동 가능한 모든 위치 리스트를 리턴한다. (불변) */
    public List<Position> getMoves() { return Collections.unmodifiableList(moves); }
    
    void calculateMoves() {
        moves.clear();
    }
    
    boolean hasMove(Position pos) {
        return moves.contains(pos);
    }
}
