import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class MyFrame extends JFrame {

    ImageIcon[] pieces = new ImageIcon[12];

    MyFrame() {
        pieces[0] = new ImageIcon("res/images/PB.png");
        pieces[1] = new ImageIcon("res/images/PW.png");
        pieces[2] = new ImageIcon("res/images/RB.png");
        pieces[3] = new ImageIcon("res/images/RW.png");
        pieces[4] = new ImageIcon("res/images/BB.png");
        pieces[5] = new ImageIcon("res/images/BW.png");
        pieces[6] = new ImageIcon("res/images/NB.png");
        pieces[7] = new ImageIcon("res/images/NW.png");
        pieces[8] = new ImageIcon("res/images/QB.png");
        pieces[9] = new ImageIcon("res/images/QW.png");
        pieces[10] = new ImageIcon("res/images/KB.png");
        pieces[11] = new ImageIcon("res/images/KW.png");

        setTitle("첫번째 프레임");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(8, 8));
        jp.setBackground(Color.darkGray);
        int row = 72;

        for (int i = 8; i > 0; i--) {
            int col = 1;
            for (int j = 0; j < 8; j++) {
                Color blankCol = new Color(0, 0, 0);
                JPanel njp = new JPanel();

                if ((j + (i%2)) % 2 == 0)
                    blankCol = new Color(255, 255, 255);
                else
                    blankCol = new Color(0, 0, 0);

                njp.setBackground(blankCol);
                JLabel blackPos = new JLabel();
                //blackPos.setIcon(pieces[9]);
                blackPos.setForeground(new Color(255 - blankCol.getRed(),255 - blankCol.getGreen(),255 - blankCol.getBlue()));
                njp.add(blackPos);
                jp.add(njp);
                col++;
            }
            row--;
        }
        add(jp);
        setVisible(true);
    }
}
