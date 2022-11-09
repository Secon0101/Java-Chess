import chess.*;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyFrame extends JFrame implements MouseListener {

    ImageIcon[] pieces = new ImageIcon[12];
    Color backCol;
    Chess myChess;

    MyFrame(Chess chess) {

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

        for(int i = 0; i < 12; i++)
        {
            Image img = pieces[i].getImage();
            Image changed = img.getScaledInstance(50,50, Image.SCALE_SMOOTH);
            pieces[i] = new ImageIcon(changed);
        }

        setTitle("첫번째 프레임");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(8, 8));
        jp.setBackground(Color.darkGray);

        for (int i = 8; i > 0; i--) {
            for (int j = 1; j <= 8; j++) {
                Color blankCol = new Color(0, 0, 0);
                JPanel njp = new JPanel();

                if ((j + (i%2)) % 2 == 0)
                    blankCol = new Color(255, 255, 255);
                else
                    blankCol = new Color(100, 100, 100);

                njp.setBackground(blankCol);
                JLabel jl = new JLabel();
                jl.setSize(60,60);

                if(chess.getPiece(j,i) instanceof Pawn)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;

                    }
                    jl.setIcon(pieces[plusIndex + 0]);
                }
                if(chess.getPiece(j,i) instanceof Rook)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    jl.setIcon(pieces[plusIndex + 1]);
                }
                if(chess.getPiece(j,i) instanceof Knight)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    jl.setIcon(pieces[plusIndex + 2]);
                }
                if(chess.getPiece(j,i) instanceof Bishop)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    jl.setIcon(pieces[plusIndex + 3]);
                }
                if(chess.getPiece(j,i) instanceof Queen)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    jl.setIcon(pieces[plusIndex + 4]);
                }
                if(chess.getPiece(j,i) instanceof King)
                {
                    int plusIndex = 0;
                    if(chess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 6;
                    }
                    jl.setIcon(pieces[plusIndex + 5]);
                }
                njp.add(jl);
                jp.add(njp);
                njp.addMouseListener(this);
            }
        }
        add(jp);
        setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        JPanel b = (JPanel)e.getSource();
        backCol = b.getBackground();
        b.setBackground(new Color(153,255,102));
    }
    @Override
    public void mouseExited(MouseEvent e) {
        JPanel b = (JPanel)e.getSource();
        b.setBackground(backCol);
    }
}


