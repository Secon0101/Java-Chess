import chess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements MouseListener, MoveResultListener{

    ImageIcon[] pieces = new ImageIcon[12];
    Color selectPieceCol = new Color(153,255,102);
    Color movePosCol = new Color(255,200,100);
    Color canKillCol = new Color(255, 100,100);
    Color kingCheckCol = new Color(220,120,220);
    Color kingCheckmateCol = new Color(150,50,150);
    Color stalemateCol = new Color(100,150,200);
    Chess myChess;
    Position startPos;
    Position endPos;
    JPanel mainPanel;
    Piece selectedPiece = null;
    boolean inGame;
    boolean[] kingChecked = { false, false }; //black, white
    List<PieceBlank> blankList = new ArrayList<>();

    public void SetIcons() {
        pieces[0] = new ImageIcon("res/images/PB.png");
        pieces[1] = new ImageIcon("res/images/RB.png");
        pieces[2] = new ImageIcon("res/images/NB.png");
        pieces[3] = new ImageIcon("res/images/BB.png");
        pieces[4] = new ImageIcon("res/images/QB.png");
        pieces[5] = new ImageIcon("res/images/KB.png");
        pieces[6] = new ImageIcon("res/images/PW.png");
        pieces[7] = new ImageIcon("res/images/RW.png");
        pieces[8] = new ImageIcon("res/images/NW.png");
        pieces[9] = new ImageIcon("res/images/BW.png");
        pieces[10] = new ImageIcon("res/images/QW.png");
        pieces[11] = new ImageIcon("res/images/KW.png");
    }

    MyFrame(Chess chess) {
        inGame = true;
        myChess = chess;
        SetIcons();
        for (int i = 0; i < 12; i++) {
            Image img = pieces[i].getImage();
            Image changed = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            pieces[i] = new ImageIcon(changed);
        }

        setTitle("♟ Chess Game ♟ 【 Turn: " + myChess.getTurn().toString() + " 】");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 8));
        mainPanel.setBackground(Color.darkGray);

        for (int i = 8; i > 0; i--) {
            for (int j = 1; j <= 8; j++) {
                PieceBlank blank = new PieceBlank();
                blankList.add(blank);

                JLabel jl = new JLabel();
                blank.label = jl;
                blank.posX = j;
                blank.posY = i;
                jl.setSize(60, 60);

                blank.add(jl);
                mainPanel.add(blank);
                blank.addMouseListener(this);
            }
        }
        add(mainPanel);
        setVisible(true);

        UpdateFrame();
    }

    public void UpdateFrame()
    {
        int index = 0;
        for (int i = 8; i > 0; i--)
        {
            for (int j = 1; j <= 8; j++)
            {
                Color blankCol;
                if ((j + (i % 2)) % 2 == 0) blankCol = new Color(255, 255, 255);
                else blankCol = new Color(100, 100, 100);

                blankList.get(index).setBackground(blankCol);

                if (myChess.getPiece(j, i) != null)
                {
                    Piece curPiece = myChess.getPiece(j, i);
                    int plusIndex = 0;
                    if (curPiece.getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    if (curPiece instanceof Rook)
                    {
                        plusIndex += 1;
                    }
                    else if (curPiece instanceof Knight)
                    {
                        plusIndex += 2;
                    }
                    else if (curPiece instanceof Bishop)
                    {
                        plusIndex += 3;
                    }
                    else if (curPiece instanceof Queen)
                    {
                        plusIndex += 4;
                    }
                    else if (curPiece instanceof King)
                    {
                        plusIndex += 5;
                        if(kingChecked[curPiece.getTeam().ordinal()])
                        {
                            blankList.get(index).setBackground(kingCheckCol);
                        }
                    }
                    blankList.get(index).label.setIcon(pieces[plusIndex]);
                }
                else
                {
                    blankList.get(index).label.setIcon(null);
                }
                index++;
            }
        }
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (inGame)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                PieceBlank pieceBlank = (PieceBlank) e.getSource();
                PieceMove(pieceBlank);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                startPos = null;
                selectedPiece = null;
                UpdateFrame();
            }
        }
    }

    private void PieceMove(PieceBlank selectedPieceBlank)
    {
        if (selectedPiece != null)
        {
            endPos = new Position(selectedPieceBlank.posX, selectedPieceBlank.posY);
            myChess.move(startPos, endPos);
        }
        else
        {
            if (myChess.getPiece(selectedPieceBlank.posX, selectedPieceBlank.posY) != null)
            {
                selectedPiece = myChess.getPiece(selectedPieceBlank.posX, selectedPieceBlank.posY);
                if (selectedPiece.getTeam() == myChess.getTurn())
                {
                    startPos = new Position(selectedPieceBlank.posX, selectedPieceBlank.posY);
                    List<Position> moves = myChess.getPiece(selectedPieceBlank.posX, selectedPieceBlank.posY).getMoves();
                    List<PieceBlank> blanks = new ArrayList<>();
                    for (int i = 0; i < 64; i++)
                    {
                        blanks.add((PieceBlank) mainPanel.getComponent(i));
                    }
                    for (int i = 0; i < moves.size(); i++)
                    {
                        for (int j = 0; j < blanks.size(); j++)
                        {
                            if (blanks.get(j).posX == moves.get(i).x && blanks.get(j).posY == moves.get(i).y)
                            {
                                if (myChess.getPiece(blanks.get(j).posX, blanks.get(j).posY) == null)
                                {
                                    blanks.get(j).setBackground(movePosCol);
                                }
                                else
                                {
                                    blanks.get(j).setBackground(canKillCol);
                                }
                            }
                        }
                    }
                    selectedPieceBlank.setBackground(selectPieceCol);
                }
                else
                {
                    selectedPiece = null;
                }
            }
        }
    }

    @Override
    public void onMoved(MoveResult result)
    {
        if (result == MoveResult.SUCCESS || result == MoveResult.CHECK || result == MoveResult.CHECKMATE || result == MoveResult.STALEMATE)
        {
            startPos = null;
            setTitle("♟ Chess Game ♟ 【 Turn: " + myChess.getTurn().toString() + " 】");
            if (result != MoveResult.SUCCESS)
            {
                List<PieceBlank> blanks = new ArrayList<>();
                for (int i = 0; i < 64; i++)
                {
                    blanks.add((PieceBlank) mainPanel.getComponent(i));
                }
                for (int i = 0; i < blanks.size(); i++)
                {
                    Piece tempPiece = myChess.getPiece(blanks.get(i).posX, blanks.get(i).posY);
                    if (tempPiece instanceof King && tempPiece.getTeam() == myChess.getTurn())
                    {
                        if (result == MoveResult.CHECK)
                        {
                            setTitle("♟ Chess Game ♟ 【 Turn: " + myChess.getTurn().toString() + " : CHECK 】");
                            kingChecked[myChess.getTurn().ordinal()] = true;
                        }
                        else if (result == MoveResult.CHECKMATE)
                        {
                            blanks.get(i).setBackground(kingCheckmateCol);
                            inGame = false;
                            Win();
                        }
                        else
                        {
                            blanks.get(i).setBackground(stalemateCol);
                            inGame = false;
                            Draw();
                        }
                    }
                }
            }
            kingChecked[myChess.getTurn().opponent().ordinal()] = false;
        }
        endPos = null;
        selectedPiece = null;
        System.out.println(myChess);
        UpdateFrame();
    }

    private void Win()
    {
        setTitle("♟ Chess Game ♟ 【 " + myChess.getTurn().opponent() + " WON : CHECKMATE 】");
    }

    private void Draw()
    {
        setTitle("♟ Chess Game ♟ 【 DRAW : STALEMATE 】");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}


