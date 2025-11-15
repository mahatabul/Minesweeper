import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    private class MineTile extends JButton{
        int r;
        int c;
        public MineTile(int row, int col){
            this.r=row;
            this.c=col;
        }
    }

    int tilesize=70;
    int numrows=8;
    int numcols=8;
    int boardsize=tilesize*numrows;
    JFrame frame=new JFrame("Minesweeper");
    JLabel textLabel=new JLabel();
    JPanel textPanel=new JPanel();
    JPanel boardPanel=new JPanel();

    MineTile[][] mineTiles=new MineTile[numrows][numcols];

    Minesweeper(){
        frame.setVisible(true);
        frame.setSize(boardsize,boardsize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial",Font.BOLD,30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numrows,numcols));
        boardPanel.setBackground(Color.GRAY);
        frame.add(boardPanel);
        for(int r=0;r<numrows;r++){
            for(int c=0;c<numcols;c++)
            {
                MineTile tile = new MineTile(r, c);
                mineTiles [r][c]=tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS",Font.BOLD,45));
                boardPanel.add(tile);
            }
        }

    }
    public static void main(String[] args) {
    new Minesweeper();
}
    
}
