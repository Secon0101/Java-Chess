package chess;

import java.util.List;
import java.util.Random;
import java.util.LinkedList;

public class Chess {
    private final Board board = new Board();
    
    /** 현재 게임이 플레이 중이라면 {@code true}
     * @see #startGame
     * @see #startAIGame
     * @see #endGame */
    private boolean playing;
    private Team turn = Team.WHITE;
    /** ex) {@code isAITeam[Team.BLACK] == true} */
    private final boolean[] isAITeam = new boolean[2];
    
    /** 바로 전 턴에 움직인 말. 앙파상 체크에 사용됨
     *  @see #move */
    private Piece lastMovedPiece;
    /** @see #removeIllegalMoves */
    private final Board tempBoard = new Board();
    /** @see #removeIllegalMoves */
    private final Position tempPos = new Position();
    private final List<Piece> aiPieces = new LinkedList<>();
    private final Random rand = new Random();
    
    /** {@link #move move()}가 완료됐을 때 {@link MoveResult}를 받는 구독자 */
    private final List<MoveResultListener> moveResultListener = new LinkedList<>();
    
    
    public Chess() {
        // 말 놓기
        for (int y = 8; y >= 1; y -= 7) {
            Team team = y == 8 ? Team.BLACK : Team.WHITE;
            board.setPiece(1, y, new Rook(team, new Position(1, y)));
            board.setPiece(2, y, new Knight(team, new Position(2, y)));
            board.setPiece(3, y, new Bishop(team, new Position(3, y)));
            board.setPiece(4, y, new Queen(team, new Position(4, y)));
            board.setPiece(5, y, new King(team, new Position(5, y)));
            board.setPiece(6, y, new Bishop(team, new Position(6, y)));
            board.setPiece(7, y, new Knight(team, new Position(7, y)));
            board.setPiece(8, y, new Rook(team, new Position(8, y)));
            for (int x = 1; x <= 8; x++) {
                int py = y == 8 ? 7 : 2;
                board.setPiece(x, py, new Pawn(team, new Position(x, py)));
            }
        }
        
        calculateMoves(board, null);
    }
    
    
    /** 로컬 멀티플레이(2인용) 게임을 시작한다. 이후로 {@link #move move()} 메서드를 사용할 수 있다. 백 팀(아래쪽)이 선공한다.
     * @see #startAIGame */
    public void startGame() {
        startGame(Team.WHITE);
    }
    /** 로컬 멀티플레이(2인용) 게임을 시작한다. 이후로 {@link #move move()} 메서드를 사용할 수 있다.
     * @param firstTurn 선공하는 팀
     * @see #startAIGame */
    public void startGame(Team firstTurn) {
        turn = firstTurn;
        playing = true;
    }
    
    /** AI 대전(1인용) 게임을 시작한다. 이후로 {@link #move move()} 메서드를 사용할 수 있다. 백 팀(아래쪽)이 선공한다.
     * @param aiTeam AI의 팀. 반대편은 자동적으로 플레이어 팀이 된다.
     * @see #startGame() */
    public void startAIGame(Team aiTeam) {
        startAIGame(aiTeam, Team.WHITE);
    }
    /** AI 대전(1인용) 게임을 시작한다. 이후로 {@link #move move()} 메서드를 사용할 수 있다.
     * @param aiTeam AI의 팀. 반대편은 자동적으로 플레이어 팀이 된다.
     * @param firstTurn 선공하는 팀
     * @see #startGame() */
    public void startAIGame(Team aiTeam, Team firstTurn) {
        turn = firstTurn;
        isAITeam[aiTeam.ordinal()] = true;
        playing = true;
        
        if (turn == aiTeam) {
            aiMove();
        }
    }
    
    /** 게임을 끝낸다. 이후로 {@link #move move()}를 사용할 시 {@link MoveResult#NOT_PLAYING}이 리턴된다. */
    public void endGame() {
        playing = false;
    }
    
    /** {@link #move move()}가 완료됐을 때 {@link MoveResult}를 받는 리스너를 구독한다. */
    public void addMoveResultListener(MoveResultListener listener) {
        moveResultListener.add(listener);
    }
    
    
    /** 어느 쪽 팀의 차례인지 반환한다. */
    public Team getTurn() { return turn; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(int x, int y) { return board.getPiece(x, y); }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(Position pos) { return board.getPiece(pos); }
    /** 현재 게임이 진행 중이면 {@code true} */
    public boolean isPlaying() { return playing; }
    
    
    /** from 위치에 있는 말을 to 위치로 옮긴다. 이동 성공 여부 및 결과, 실패했다면 실패 원인이 담긴 {@link MoveResult}를 생성한다. 그리고 {@link MoveResultListener}에게 이벤트를 실행시키고 결과를 전달한다.
     * @return 이동 성공 여부 및 결과, 실패했다면 실패 원인. 리턴값을 사용하지 않고 {@link MoveResultListener}를 사용하는 것을 권장한다.
     * @see MoveResult
     * @see #addMoveResultListener(MoveResultListener) */
    public MoveResult move(Position from, Position to) {
        if (!playing)
            return invokeMovedEvent(MoveResult.NOT_PLAYING);
        if (!inBoard(from) || !inBoard(to))
            return invokeMovedEvent(MoveResult.OUT_OF_BOARD);
        if (to.equals(board.getKingPosition(turn.opponent())))
            return invokeMovedEvent(MoveResult.ATTACKED_KING);
        
        Piece piece = getPiece(from);
        if (piece == null)
            return invokeMovedEvent(MoveResult.NO_PIECE);
        if (piece.team != turn)
            return invokeMovedEvent(MoveResult.INVALID_TURN);
        if (!piece.hasMove(to))
            return invokeMovedEvent(MoveResult.INVALID_MOVE);
        
        // 말 옮기기
        movePiece(board, from, to);
        
        // 이동 완료 후 처리
        if (piece instanceof OnMovedListener p) {
            p.onMoved(this);
        }
        if (lastMovedPiece != null && lastMovedPiece instanceof Pawn pawn) {
            pawn.isEnPassantTarget = false;
        }
        lastMovedPiece = piece;
        
        // 다음 이동 계산 + 체크 확인
        boolean check = calculateMoves(board, null);
        
        // 스테일메이트 확인
        if (!check) {
            boolean stalemate = true;
            for (Piece p : board.getPieceIterator()) {
                if (p.getMoveCount() > 0) {
                    stalemate = false;
                    break;
                }
            }
            if (stalemate) {
                endGame();
                return invokeMovedEvent(MoveResult.STALEMATE);
            }
        }
        
        // 턴 교체
        turn = piece.team.opponent();
        
        // 잘못된 이동 제거 + 최종 판정
        MoveResult result = removeIllegalMoves(check);
        if (result == MoveResult.CHECKMATE || result == MoveResult.STALEMATE) {
            endGame();
        }
        
        // 이동 완료 이벤트 실행
        invokeMovedEvent(result);
        
        // AI 이동 처리
        if (isAITeam[turn.ordinal()]) {
            aiMove();
        }
        
        return result;
    }
    
    
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(int x, int y) {
        return 1 <= x && x <= 8 && 1 <= y && y <= 8;
    }
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(Position position) {
        return 1 <= position.x && position.x <= 8 && 1 <= position.y && position.y <= 8;
    }
    
    
    /** 보드의 from 위치에 있는 말을 to 위치로 옮긴다. 캐슬링, 앙파상, 프로모션도 적용한다. */
    private void movePiece(Board board, Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece == null || !piece.hasMove(to)) return;
        
        // 앙파상 (폰이 대각선 이동했는데 공격할 말이 없는 경우)
        if (piece instanceof Pawn pawn) {
            if (getPiece(to) == null && from.y + pawn.forward() == to.y && Math.abs(to.x - from.x) == 1) {
                board.setPiece(to.x, to.y - pawn.forward(), null);
            }
        }
        
        // 말 옮기기
        board.setPiece(from, null);
        board.setPiece(to, piece);
        
        // 캐슬링 (이동이 된다는 것 자체가 캐슬링 가능이므로 따로 검사는 안 함)
        if (piece instanceof King) {
            if (from.equals(5, to.y)) {
                // 킹 사이드
                if (to.equals(7, to.y)) {
                    Piece rook = getPiece(8, to.y);
                    board.setPiece(8, to.y, null);
                    board.setPiece(6, to.y, rook);
                    ((OnMovedListener) rook).onMoved(this);
                }
                // 퀸 사이드
                else if (to.equals(3, to.y)) {
                    Piece rook = getPiece(1, to.y);
                    board.setPiece(1, to.y, null);
                    board.setPiece(4, to.y, rook);
                    ((OnMovedListener) rook).onMoved(this);
                }
            }
        }
        
        // 프로모션
        if (piece instanceof Pawn && piece.position.y == (piece.team == Team.BLACK ? 1 : 8)) {
            board.setPiece(piece.position, new Queen(piece.team, piece.position));
        }
    }
    
    /** 주어진 판 위에 있는 모든 말들의 현재 이동 가능 위치를 계산해서, 그 말에 저장해 놓는다. 특정한 팀만 계산할 수도 있다.
     * <p> 하는 김에 체크 여부도 계산한다. </p>
     * @param board 계산할 보드
     * @param team 계산할 팀. null이면 모든 팀 계산
     * @return 체크 여부 */
    private boolean calculateMoves(Board board, Team team) {
        boolean check = false;
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null) continue;
            if (team != null && piece.team != team) continue;
            
            if (piece.calculateMoves(board) == true) {
                check = true;
            }
        }
        
        return check;
    }
    
    /** 말들의 이동 가능 위치 중 이동했을 때 우리 편이 체크당하는 경우를 제거한다. {@link #tempBoard}에서 이동 위치를 전부 시뮬레이션하여, 체크가 된다면 제거한다.
     * <p> 체크, 체크메이트, 스테일메이트 또는 셋 다 아닌 상태를 최종적으로 결정한다. </p>
     * @param isCheckNow 현재 체크 상태. 체크메이트/스테일메이트 구분에 쓰인다.
     * @return 체크/체크메이트/스테일메이트라면 그에 맞는 {@link MoveResult}, 아니면 {@link MoveResult#SUCCESS} */
    private MoveResult removeIllegalMoves(boolean isCheckNow) {
        int moveCount = 0; // 말 하나를 계산한 후 최종 이동 경로의 개수 저장 (to check checkmate)
        
        // 다음에 이동할 팀의 모든 말에 대해
        for (Piece piece : board.getPieceIterator()) {
            if (piece == null || piece.team != turn) continue;
            
            // 말을 모든 이동 가능 위치로 이동시키고 체크 여부 확인
            tempPos.set(piece.position);
            var iter = piece.moves.iterator();
            while (iter.hasNext()) {
                Position to = iter.next();
                
                // board를 tempBoard에 복사
                board.copyTo(tempBoard);
                
                // 이동
                movePiece(tempBoard, piece.position, to);
                
                // 체크라면 이동 가능 위치 제거
                boolean check = calculateMoves(tempBoard, turn.opponent());
                if (check) {
                    iter.remove();
                }
            }
            
            // 롤백
            piece.position.set(tempPos);
            
            // 최종 이동 위치 개수 저장
            if (moveCount == 0) {
                moveCount += piece.getMoveCount();
            }
        }
        
        // 현재 체크 상태, 이동 가능 위치 개수에 따라 최종 결과 리턴
        if (moveCount == 0) return isCheckNow ? MoveResult.CHECKMATE : MoveResult.STALEMATE;
        else return isCheckNow ? MoveResult.CHECK : MoveResult.SUCCESS;
    }
    
    /** {@link #move move()} 완료 이벤트를 발생시키고 {@code result}를 보낸다.
     * @return 인자로 들어온 {@code result}
     */
    private MoveResult invokeMovedEvent(MoveResult result) {
        moveResultListener.forEach(listener -> listener.onMoved(result));
        return result;
    }
    
    /** AI가 이동 위치를 정해서 {@link #move move()}를 실행시킨다. */
    private void aiMove() {
        if (!isAITeam[turn.ordinal()]) return;
        
        aiPieces.clear();
        for (Piece p : board.getPieceIterator()) {
            if (p.team != turn || p.getMoveCount() == 0)
                continue;
            aiPieces.add(p);
        }
        
        // 랜덤 이동
        if (aiPieces.size() > 0) {
            Piece p = aiPieces.get(rand.nextInt(aiPieces.size()));
            Position pTo = p.moves.get(rand.nextInt(p.getMoveCount()));
            move(p.position.copy(), pTo);
        }
    }
    
    
    /** @see Board#toString() */
    @Override
    public String toString() { return board.toString(); }
}
