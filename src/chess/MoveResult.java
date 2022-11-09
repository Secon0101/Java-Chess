package chess;

/** {@code Success} - 이동 성공 <br/>
 *  {@code InvalidPosition} - 좌표가 체스판 밖에 있음 <br/>
 *  {@code NoPiece} - 이동할 말이 없음 <br/>
 *  {@code InvalidMove} - 말을 그 위치로 옮길 수 없음 */
public enum MoveResult {
    Success,
    InvalidPosition,
    NoPiece,
    InvalidMove,
}
