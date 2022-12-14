package chess;

/**
 * {@link Chess#move move()}의 성공 및 체크 등의 여부, 실패했다면 실패 원인을 표시하는 열거형
 * <ul>
 *   <li>{@link #NOT_PLAYING} - 게임이 시작되지 않음</li>
 *   <li>{@link #OUT_OF_BOARD} - 좌표가 체스판을 벗어남</li>
 *   <li>{@link #NO_PIECE} - 이동할 말이 없음</li>
 *   <li>{@link #INVALID_TURN} - 현재 턴이 아님</li>
 *   <li>{@link #INVALID_MOVE} - 말이 그 위치로 이동할 수 없음</li>
 *   <br>
 *   <li>{@link #SUCCESS} - 이동 성공</li>
 *   <li>{@link #CHECK} - 이동 성공 + 체크</li>
 *   <li>{@link #CHECKMATE} - 이동 성공 + 체크메이트</li>
 *   <li>{@link #STALEMATE} - 이동 성공 + 스테일메이트</li>
 *   <br>
 *   <li>{@link #ATTACKED_KING} - 킹을 잡으려고 시도함 (오류) </li>
 * </ul>
 */
public enum MoveResult {
    /** 게임이 시작되지 않음 */
    NOT_PLAYING,
    /** 좌표가 체스판을 벗어남 */
    OUT_OF_BOARD,
    /** 이동할 말이 없음 */
    NO_PIECE,
    /** 현재 턴이 아님 */
    INVALID_TURN,
    /** 말이 그 위치로 이동할 수 없음 */
    INVALID_MOVE,
    
    /** 이동 성공 */
    SUCCESS,
    /** 이동 성공 + 체크 */
    CHECK,
    /** 이동 성공 + 체크메이트 */
    CHECKMATE,
    /** 이동 성공 + 스테일메이트 */
    STALEMATE,
    
    /** 킹을 잡으려고 시도함 (오류) */
    ATTACKED_KING,
}
