package chess;

/** <ul>
 *   <li> {@link #BLACK} - 흑 (위쪽, 디폴트 후공) </li>
 *   <li> {@link #WHITE} - 백 (아래쪽, 디폴트 선공) </li>
 * </ul> */
public enum Team {
    /** 위쪽, 후공 */
    BLACK(0),
    /** 아래쪽, 선공 */
    WHITE(1);
    
    private final int value;
    private Team(int value) { this.value = value; }
    
    /** 상대 팀을 구한다. */
    public Team opponent() { return value == 0 ? WHITE : BLACK; }
}
