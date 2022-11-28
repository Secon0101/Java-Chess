package chess;

public enum Team {
    BLACK(0),
    WHITE(1);
    
    private final int value;
    private Team(int value) { this.value = value; }
    public int value() { return value; }
    
    /** 상대 팀을 구한다. */
    public Team opponent() { return value == 0 ? WHITE : BLACK; }
}
