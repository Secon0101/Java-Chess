import chess.Chess;
import chess.Team;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    boolean choseCom = false;
    StartFrame(Chess chess) {
        setTitle("♟ Chess Game ♟");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel main = new JPanel();
        main.setBackground(Color.darkGray);
        GridLayout gl = new GridLayout(2,1);
        main.setLayout(gl);

        JPanel sub1 = new JPanel();
        sub1.setBackground(Color.darkGray);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,0,100);
        sub1.setLayout(fl);
        JLabel jl = new JLabel("Chess Game");
        jl.setOpaque(true);
        jl.setFont(jl.getFont().deriveFont(50f));
        jl.setForeground(new Color(255,255,255));
        jl.setBackground(Color.darkGray);
        sub1.add(jl);

        JPanel sub2 = new JPanel();
        sub2.setBackground(Color.darkGray);
        FlowLayout fl2 = new FlowLayout(FlowLayout.CENTER,50,100);
        sub2.setLayout(fl2);
        JButton btn1 = new JButton("Player VS Player");
        btn1.setBackground(Color.lightGray);
        JButton btn2 = new JButton("Player VS Computer");
        btn2.setBackground(Color.lightGray);

        sub2.add(btn1);
        sub2.add(btn2);
        main.add(sub1);
        main.add(sub2);
        add(main);
        setVisible(true);

        btn1.addActionListener(e ->{
            if(choseCom)
            {
                setVisible(false);
                new MyFrame(chess);
                chess.startAIGame(Team.BLACK);
            }
            else
            {
                setVisible(false);
                new MyFrame(chess);
                chess.startGame();
            }
        });

        btn2.addActionListener(e ->{
            if(choseCom)
            {
                setVisible(false);
                new MyFrame(chess);
                chess.startAIGame(Team.WHITE);
            }
            else
            {
                choseCom = true;
                InVSCom(btn1, btn2);
            }
        });
    }

    private void InVSCom(JButton btn1, JButton btn2)
    {
        btn1.setText("WHITE");
        btn1.setForeground(new Color(255,255,255));
        btn2.setText("BLACK");
        btn2.setForeground(new Color(0));
        setVisible(true);
    }
}
