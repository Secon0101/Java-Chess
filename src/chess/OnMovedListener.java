package chess;

/** 말이 이동을 완료했을 때 이벤트를 받을 수 있는 리스너 */
interface OnMovedListener {
    /** 말이 이동을 완료했을 때 호출된다. ({@link OnMovedListener}) */
    void onMoved(Chess chess);
}
