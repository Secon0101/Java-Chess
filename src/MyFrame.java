import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class MyFrame extends JFrame{
    MyFrame()
    {
        setTitle("첫번째 프레임");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(8,8));
        jp.setBackground(Color.white);
        int row = 72;

        for(int i = 8; i > 0; i --)
        {
            int col = 1;
            for(int j = 0; j < 8; j++)
            {
                jp.add(new JButton((char)row + Integer.toString(col)));
                col++;
            }
            row--;
        }


        add(jp);
        setVisible(true);
    }
}
