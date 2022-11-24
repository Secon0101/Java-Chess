import chess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyFrame extends JFrame implements MouseListener {

    ImageIcon[] pieces = new ImageIcon[12];
    Color backCol;
    Chess myChess;
    Position startPos;
    Position endPos;
    JPanel mainPanel;
    JPanel subPanel;

    public void SetIcons()
    {
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

        myChess = chess;
        SetIcons();
        for(int i = 0; i < 12; i++)
        {
            Image img = pieces[i].getImage();
            Image changed = img.getScaledInstance(45,45, Image.SCALE_SMOOTH);
            pieces[i] = new ImageIcon(changed);
        }

        setTitle("첫번째 프레임");
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UpdateFrame();
    }

    public void UpdateFrame()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        mainPanel = new JPanel();
        subPanel = new JPanel();
        mainPanel.setSize(500,500);
        subPanel.setSize(100,500);
        mainPanel.setLayout(new GridLayout(8, 8));
        mainPanel.setBackground(Color.darkGray);

        for (int i = 8; i > 0; i--) {
            for (int j = 1; j <= 8; j++) {
                Color blankCol = new Color(0, 0, 0);
                PieceBlank blank = new PieceBlank();

                if ((j + (i%2)) % 2 == 0)
                    blankCol = new Color(255, 255, 255);
                else
                    blankCol = new Color(100, 100, 100);

                blank.setBackground(blankCol);
                JLabel jl = new JLabel();
                blank.label = jl;
                blank.posX = j;
                blank.posY = i;
                jl.setSize(60,60);

                if(myChess.getPiece(j,i) != null)
                {
                    Piece curPiece = myChess.getPiece(j,i);
                    int plusIndex = 0;
                    if(curPiece.getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }

                    if(curPiece instanceof Pawn)
                    {
                        jl.setIcon(pieces[plusIndex + 0]);
                    }
                    if(curPiece instanceof Rook)
                    {
                        jl.setIcon(pieces[plusIndex + 1]);
                    }
                    if(curPiece instanceof Knight)
                    {
                        jl.setIcon(pieces[plusIndex + 2]);
                    }
                    if(curPiece instanceof Bishop)
                    {
                        jl.setIcon(pieces[plusIndex + 3]);
                    }
                    if(curPiece instanceof Queen)
                    {
                        jl.setIcon(pieces[plusIndex + 4]);
                    }
                    if(curPiece instanceof King)
                    {
                        jl.setIcon(pieces[plusIndex + 5]);
                    }
                }
                blank.add(jl);
                mainPanel.add(blank);
                blank.addMouseListener(this);
            }
        }
        panel.add(mainPanel);
        panel.add(subPanel);
        add(panel);
        setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        PieceBlank pieceBlank = (PieceBlank)e.getSource();
        startPos = new Position(pieceBlank.posX, pieceBlank.posY);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPos != null && endPos != null)
        {
            PieceBlank pieceFull = (PieceBlank)e.getSource();
            myChess.move(startPos, endPos);
            startPos = null;
            endPos = null;
            UpdateFrame();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        PieceBlank b = (PieceBlank)e.getSource();
        backCol = b.getBackground();
        //b.setBackground(new Color(153,255,102));
        if(startPos != null)
        {
            endPos = new Position(b.posX, b.posY);
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        PieceBlank b = (PieceBlank)e.getSource();
        //b.setBackground(backCol);
    }
}


