import chess.Chess;
import chess.Pawn;
import chess.Team;

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

    MyFrame() {

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
                    blankCol = new Color(0, 0, 0);

                njp.setBackground(blankCol);
                JLabel jl = new JLabel(j +"," + i);
                if(myChess.getPiece(j,i) instanceof Pawn)
                {
                    int plusIndex = 0;
                    if(myChess.getPiece(j,i).getTeam() == Team.WHITE)
                    {
                        plusIndex = 5;
                    }
                    jl.setIcon(pieces[plusIndex + 0]);
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
        b.setBackground(Color.gray);
        System.out.println("Entered");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        JPanel b = (JPanel)e.getSource();
        b.setBackground(backCol);
    }
}


