package chess;

public class Chess {
    private final Piece[][] board = new Piece[8][8];

    private boolean isPlaying;
    private Team turn = Team.White;
    
    
    public Piece getPiece(int x, int y) { return board[y][x]; }
    public Piece getPiece(Position pos) { return getPiece(pos.x, pos.y); }
    private void setPiece(Position pos, Piece piece) { board[pos.y][pos.x] = piece; }
    
    public Team getTurn() { return turn; }
    
    
    public Chess() {
        for (int i = 0; i < 8; i++)
            board[6][i] = new Pawn(board, new Position(i, 6), Team.Black);
        for (int i = 0; i < 8; i++)
            board[1][i] = new Pawn(board, new Position(i, 1), Team.White);
    }
    
    /** 로컬 멀티플레이 게임을 시작한다. */
    public void play() {
        isPlaying = true;
    }
    
    
    /** 말을 from 위치에서 to 위치로 이동시킨다. 그리고 턴을 상대방에게 넘긴다.
     *  @return 이동 성공 여부 */
    public MovementResult move(Position from, Position to) {
        if (!isPlaying)
            return MovementResult.GameNotStarted;
        if (!from.in(8, 8) || !to.in(8, 8))
            return MovementResult.InvalidPosition;
        if (getPiece(from) == null)
            return MovementResult.NoPiece;
        if (getPiece(from).getTeam() != turn)
            return MovementResult.NotYourTurn;
        
        Piece piece = getPiece(from);
        setPiece(to, piece);
        piece.pos = to;
        setPiece(from, null);
        piece.onMoved();
        
        turn = piece.getTeam() == Team.White ? Team.Black : Team.White;
        
        return MovementResult.Succeed;
    }
    
    private enum MovementResult {
        Succeed,
        GameNotStarted,
        InvalidPosition,
        NoPiece,
        NotYourTurn,
    }
}
