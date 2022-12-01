import chess.Chess;
import chess.MoveResultListener;
import chess.Team;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class StartFrame extends JFrame implements ChangeListener {

    int[] colors = { 255, 255, 255, 100, 100, 100 };
    boolean choseCom = false;

    MyFrame chessFrame;
    Team AITeam;
    Chess newChess;
    StartFrame(Chess chess)
    {
        newChess = chess;
        chessFrame = new MyFrame(newChess);
        newChess.addMoveResultListener((MoveResultListener) chessFrame);

        setTitle("♟ Chess Game ♟");
        setSize(504, 504);
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
        JLabel jl = new JLabel("Chessing in Java");
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
                AITeam = Team.BLACK;
                ChooseColor(main);
            }
            else
            {
                ChooseColor(main);
            }
        });

        btn2.addActionListener(e ->{
            if(choseCom)
            {
                AITeam = Team.WHITE;
                ChooseColor(main);
            }
            else
            {
                choseCom = true;
                InVSCom(btn1, btn2, jl);
            }
        });
    }

    JPanel WPanel = new JPanel();
    JPanel BPanel = new JPanel();
    private void ChooseColor(JPanel panel)
    {
        panel.removeAll();
        panel.setLayout(new GridLayout(2,2));
        WPanel = new JPanel();
        WPanel.setBackground(new Color(colors[0], colors[1], colors[2]));
        BPanel = new JPanel();
        BPanel.setBackground(new Color(colors[3], colors[4], colors[5]));
        JPanel pl3 = new JPanel();
        pl3.setBackground(Color.darkGray);
        JPanel pl4 = new JPanel();
        pl4.setBackground(Color.darkGray);
        JLabel colorW = new JLabel("Color A");
        JLabel colorB = new JLabel("Color B");
        colorW.setSize(100,100);
        colorB.setSize(100,100);
        WPanel.add(colorW);
        BPanel.add(colorB);
        panel.add(WPanel);
        panel.add(BPanel);
        panel.add(pl3);
        panel.add(pl4);
        JSlider sliderWR = new JSlider(0,255,255);
        JSlider sliderWG = new JSlider(0,255,255);
        JSlider sliderWB = new JSlider(0,255,255);
        sliderWR.setName("WR");
        sliderWG.setName("WG");
        sliderWB.setName("WB");
        sliderWR.addChangeListener(this);
        sliderWG.addChangeListener(this);
        sliderWB.addChangeListener(this);
        JLabel WRValue = new JLabel("Red Value :");
        JLabel WGValue = new JLabel("Green Value :");
        JLabel WBValue = new JLabel("Blue Value :");
        WRValue.setForeground(Color.WHITE);
        WGValue.setForeground(Color.WHITE);
        WBValue.setForeground(Color.WHITE);
        pl3.add(WRValue);
        pl3.add(sliderWR);
        pl3.add(WGValue);
        pl3.add(sliderWG);
        pl3.add(WBValue);
        pl3.add(sliderWB);
        JSlider sliderBR = new JSlider(0,255,100);
        JSlider sliderBG = new JSlider(0,255,100);
        JSlider sliderBB = new JSlider(0,255,100);
        sliderBR.setName("BR");
        sliderBG.setName("BG");
        sliderBB.setName("BB");
        sliderBR.addChangeListener(this);
        sliderBG.addChangeListener(this);
        sliderBB.addChangeListener(this);
        JLabel BRValue = new JLabel("Red Value :");
        JLabel BGValue = new JLabel("Green Value :");
        JLabel BBValue = new JLabel("Blue Value :");
        BRValue.setForeground(Color.WHITE);
        BGValue.setForeground(Color.WHITE);
        BBValue.setForeground(Color.WHITE);
        pl4.add(BRValue);
        pl4.add(sliderBR);
        pl4.add(BGValue);
        pl4.add(sliderBG);
        pl4.add(BBValue);
        pl4.add(sliderBB);
        JButton reset = new JButton("RESET");
        reset.addActionListener(e -> {
            sliderWR.setValue(255);
            sliderWG.setValue(255);
            sliderWB.setValue(255);
            sliderBR.setValue(100);
            sliderBG.setValue(100);
            sliderBB.setValue(100);
        });
        pl3.add(reset);
        JButton select = new JButton("SELECT");
        select.addActionListener(e -> {
            SetGameColor(new Color(colors[0], colors[1], colors[2]), new Color(colors[3], colors[4], colors[5]));
        });
        pl4.add(select);
        setVisible(true);
    }

    private void SetGameColor(Color W, Color B)
    {
        chessFrame.SetColor(W,B);
        setVisible(false);
        if(choseCom)
        {
            newChess.startAIGame(AITeam);
        }
        else
        {
            newChess.startGame();
        }
        chessFrame.OpenTab();
        chessFrame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider js = (JSlider)e.getSource();
        int index = 0;
        switch (js.getName())
        {
            case "WG" :
                index = 1;
                break;

            case "WB" :
                index = 2;
                break;

            case "BR" :
                index = 3;
                break;

            case "BG" :
                index = 4;
                break;

            case "BB" :
                index = 5;
                break;

        }
        colors[index] = js.getValue();
        WPanel.setBackground(new Color(colors[0], colors[1], colors[2]));
        BPanel.setBackground(new Color(colors[3], colors[4], colors[5]));
    }

    private void InVSCom(JButton btn1, JButton btn2, JLabel jl)
    {
        btn1.setText("WHITE");
        btn1.setForeground(new Color(255,255,255));
        btn2.setText("BLACK");
        btn2.setForeground(new Color(0));
        jl.setText("Choose Color");
        setVisible(true);
    }
}
