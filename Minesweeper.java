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

    
    int minecount = 50;

    JFrame frame=new JFrame("Minesweeper: " + Integer.toString(minecount));
    JLabel textLabel=new JLabel();
    JPanel textPanel=new JPanel();
    JPanel boardPanel=new JPanel();


    MineTile[][] mineTiles=new MineTile[numrows][numcols];
    ArrayList<MineTile>minelist;

    Random random= new Random();

    int tilesClicked=0;
    boolean gameOver=false;

    Minesweeper(){
       
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
                // tile.setText("💣");
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e){

                        if(gameOver)
                        {
                            return;
                        }

                        MineTile tile=(MineTile)e.getSource();

                        //left click
                        if(e.getButton()==MouseEvent.BUTTON1){
                            if(tile.getText().isEmpty()){
                                if(minelist.contains(tile))
                                {
                                    revealMine();
                                    textLabel.setText("Game Over!");
                                }
                                else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        }

                        else if(e.getButton()==MouseEvent.BUTTON3){
                            if(tile.getText()=="" && tile.isEnabled()){
                                tile.setText("🚩");
                            }
                            else if(tile.getText()=="🚩")
                            {
                                tile.setText("");
                            }
                        }
                    }
                    
                });

                boardPanel.add(tile);
            }
        }
         frame.setVisible(true);

         minelist = new ArrayList<>();

         setMines();
         

    }

    void setMines()
    {
        // minelist=new ArrayList<MineTile>();
        // minelist.add(mineTiles[1][1]);
        // minelist.add(mineTiles[2][2]);
        // minelist.add(mineTiles[3][3]);  
        // minelist.add(mineTiles[4][4]);
        int mineleft = minecount;
        while(mineleft>0)
        {
           int r=random.nextInt(numrows);
           int c=random.nextInt(numcols);

           MineTile mine = mineTiles[r][c];
           if(!minelist.contains(mine))
           {
             minelist.add(mine);
             mineleft-=1;
           }
        }
    }
    void revealMine()
    {
        for(int i=0;i<minelist.size();i++)
        {
            MineTile tile=minelist.get(i);
            tile.setText("💣");
        }
        gameOver=true;
        textLabel.setText("Game Over");
    }

    public static void main(String[] args) {
    new Minesweeper();
}

void checkMine(int r, int c)
{
    if(r<0||r>=numrows||c<0||c>=numcols)
    {
        return ;
    }

    MineTile tile=mineTiles[r][c];

    if(!tile.isEnabled())
    {
        return;
    }

    tile.setEnabled(false);
    
    tilesClicked+=1;

    int minesfound=0;
    //top 3
    minesfound+=countMine(r-1,c-1);//top left
    minesfound+=countMine(r-1,c);//top
    minesfound+=countMine(r-1,c+1);//top right

    //left and right
    minesfound+=countMine(r,c-1);// left
    minesfound+=countMine(r,c+1);//right
    
    //bottom three
    minesfound+=countMine(r+1,c-1);//bottom left
    minesfound+=countMine(r+1,c);//bottom
    minesfound+=countMine(r+1,c+1);//bottom right

    if(minesfound>0){
        tile.setText(Integer.toString(minesfound));
    }
    else{
        tile.setText("");

        //top 3 
        checkMine(r-1,c-1);
        checkMine(r-1, c+1);

        //left right

        checkMine(r, c-1);
        checkMine(r, c+1);

        //bottom
        checkMine(r+1, c-1);
        checkMine(r+1, c);
        checkMine(r+1, c+1);

    }
    if(tilesClicked==numrows*numcols-minelist.size())
    {
        gameOver = true;
        textLabel.setText("Mines Cleared");
    }
}

int countMine(int r,int c){
    if(r<0||r>=numrows||c<0||c>=numcols)
    {
        return 0;
    }
    if(minelist.contains(mineTiles[r][c]))
    {
        return 1;
    }
    return 0;
}
    
}
 