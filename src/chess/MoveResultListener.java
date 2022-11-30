package chess;

/** {@link Chess#move move()}가 완료됐을 때 {@link MoveResult}를 받는 리스너 */
public interface MoveResultListener {
    /** {@link Chess#move move()}가 완료됐을 때 호출된다. ({@link MoveResultListener}) */
    void onMoved(MoveResult result);
}
