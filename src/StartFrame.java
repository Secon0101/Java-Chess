import chess.Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartFrame extends JFrame implements MouseListener {

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
        JButton btn = new JButton("Player VS Player");
        btn.setBackground(Color.lightGray);
        JButton btn2 = new JButton("Player VS Computer");
        btn2.setBackground(Color.lightGray);
        sub2.add(btn);
        sub2.add(btn2);
        main.add(sub1);
        main.add(sub2);
        add(main);
        setVisible(true);

        btn.addActionListener(e ->{
            setVisible(false);
            new MyFrame(chess);
            chess.startGame();
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
