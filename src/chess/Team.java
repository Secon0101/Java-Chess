package chess;

/** 흑(위쪽) / 백(아래쪽) */
public enum Team {
    /** 위쪽, 후공 */
    BLACK(0),
    /** 아래쪽, 선공 */
    WHITE(1);
    
    private final int value;
    private Team(int value) { this.value = value; }
    public int value() { return value; }
    
    /** 상대 팀을 구한다. */
    public Team opponent() { return value == 0 ? WHITE : BLACK; }
}
