package chess;

import java.util.List;
import java.util.ArrayList;

public final class Chess {
    private final Piece[][] board = new Piece[8][8];
    private final List<Piece> pieces = new ArrayList<>(32);
    private final King[] kings = new King[2];
    
    private boolean playing;
    private Team turn = Team.WHITE;
    
    
    public Chess() {
        // 말 놓기
        for (int y = 8; y >= 1; y -= 7) {
            final Team team = y == 8 ? Team.BLACK : Team.WHITE;
            placePiece(new Rook(this, team, new Position(1, y)));
            placePiece(new Knight(this, team, new Position(2, y)));
            placePiece(new Bishop(this, team, new Position(3, y)));
            placePiece(new Queen(this, team, new Position(4, y)));
            placePiece(new King(this, team, new Position(5, y)));
            placePiece(new Bishop(this, team, new Position(6, y)));
            placePiece(new Knight(this, team, new Position(7, y)));
            placePiece(new Rook(this, team, new Position(8, y)));
            for (int x = 1; x <= 8; x++) {
                placePiece(new Pawn(this, team, new Position(x, y == 8 ? 7 : 2)));
            }
        }
        
        calculateMoves();
    }
    
    
    /** 어느 쪽 팀의 차례인지 반환한다. */
    public Team getTurn() { return turn; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(int x, int y) { return board[y-1][x-1]; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */
    public Piece getPiece(Position pos) { return board[pos.y-1][pos.x-1]; }
    /** 왼쪽 아래가 (1, 1), 오른쪽 위가 (8, 8) */ 
    private void setPiece(Position pos, Piece piece) { board[pos.y-1][pos.x-1] = piece; }
    
    
    /** 게임을 시작한다. 이후로 {@code move} 메서드를 사용할 수 있다. */
    public void startGame() {
        playing = true;
    }
    
    /** 게임을 끝낸다. */
    public void endGame() {
        playing = false;
    }
    
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(int x, int y) {
        return 1 <= x && x <= 8 && 1 <= y && y <= 8;
    }
    /** 위치(좌표)가 8x8 체스판 안에 있는지 판단한다. */
    public static boolean inBoard(Position position) {
        return 1 <= position.x && position.x <= 8 && 1 <= position.y && position.y <= 8;
    }
    
    /** from 위치에 있는 말을 to 위치로 옮긴다.
     * @return 이동 성공 여부 + 실패했다면 실패 원인
     * @see MoveResult */
    public MoveResult move(Position from, Position to) {
        if (!playing)
            return MoveResult.NOT_PLAYING;
        if (!inBoard(from) || !inBoard(to))
            return MoveResult.OUT_OF_BOARD;
        
        Piece piece = getPiece(from);
        if (piece == null)
            return MoveResult.NO_PIECE;
        if (piece.team != turn)
            return MoveResult.INVALID_TURN;
        if (!piece.hasMove(to))
            return MoveResult.INVALID_MOVE;
        
        // 말 옮기기
        if (getPiece(to) != null) {
            pieces.remove(getPiece(to));
        }
        setPiece(from, null);
        setPiece(to, piece);
        piece.position.set(to);
        
        if (piece instanceof OnMovedListener p) {
            p.onMoved();
        }
        
        // 다음 이동 계산
        calculateMoves();
        
        // 턴 교체
        turn = turn == Team.BLACK ? Team.WHITE : Team.BLACK;
        
        return MoveResult.SUCCESS;
    }
    
    /** 폰을 퀸으로 승격시킨다. */
    void promote(Pawn pawn) {
        pieces.remove(pawn);
        placePiece(new Queen(this, pawn.team, pawn.position));
    }
    
    
    /** 말을 이차원 배열과 말 리스트에 추가한다. */
    private void placePiece(Piece piece) {
        setPiece(piece.position, piece);
        pieces.add(piece);

        // 킹이면 따로 저장
        if (piece instanceof King king) {
            kings[king.getTeam().value()] = king;
        }
    }
    
    /** 판 위에 있는 모든 말들의 이동 가능 위치를 계산해서, 그 말에 저장해 놓는다. */
    private void calculateMoves() {
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).calculateMoves();
        }
    }
}
