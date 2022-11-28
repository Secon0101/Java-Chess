package chess;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public abstract class Piece {
    protected final List<Position> moves = new LinkedList<>();
    protected final Team team;
    protected final Position position;
    
    
    Piece(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
    
    
    /** 말의 팀을 리턴한다. */
    public Team getTeam() { return team; }
    /** 말의 위치를 리턴한다. */
    public Position gePosition() { return position; }
    /** 말이 이동 가능한 모든 위치 리스트를 리턴한다. ({@link Collections#unmodifiableList 불변 리스트}) */
    public List<Position> getMoves() { return Collections.unmodifiableList(moves); }
    
    /** 주어진 보드에서 움직일 수 있는 모든 위치를 계산하고, 저장해 놓는다. {@link #getMoves()} 메서드로 그 리스트를 얻을 수 있다.
     * <p> 하는 김에 체크 여부도 계산한다. </p>
     * @param board 위치를 계산할 보드. 현재 플레이 중인 체스 보드일 수도 있고, 이동 위치 유효성을 체크하기 위한 임시 보드일 수도 있다.
     * @return 체크 여부
     * @implSpec {@code moves.clear()} 이후 {@code moves}에 적절한 {@code Position}들을 계산해서 넣는다. */
    abstract boolean calculateMoves(Board board);
    
    /** 그 위치로 이동 가능하다면 true, 아니면 false ({@code List.contains(pos)}) */
    boolean hasMove(Position pos) {
        return moves.contains(pos);
    }
    
    
    @Override
    public String toString() { return String.format("%s team %s at %s", team, getClass().getSimpleName(), position); }
}
