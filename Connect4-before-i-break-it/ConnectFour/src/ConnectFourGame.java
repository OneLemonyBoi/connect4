import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Scanner;


public class ConnectFourGame {
    
    private ConnectFourGrid grid = new ConnectFourGrid();
    private JFrame frame = new JFrame("Connect Four");
    private JPanel panel;
    private int player;
    private boolean computerplay;
    private int redwins;
    private int yellowwins;

    
    public static void main(String[] args){
        new ConnectFourGame().start();
    }
    private void start() {
      System.out.println("Enter the following number to choose play style:");
      System.out.println("computer = 0             solo = 1               instructions = 2  ");
      Scanner intcheck = new Scanner(System.in);
      int intchoice = intcheck.nextInt();
      if(intchoice == 0){
        computerplay = true;
      }
      else if (intchoice == 1){
        computerplay = false;
      }
      else if (intchoice == 2){
        System.out.println();
        System.out.println("general: the user can pick any coulmn to place their piece, put four of your peices in a row, column, or diagonal configuration to win the round. The user will always play as yellow. Place another checker to restart the game.");
        System.out.println();
        System.out.println("computer: the computer will automaticly place pieces after you.");
        System.out.println();
        System.out.println("solo: piece color switches every turn.");

      }
      setUpPanel();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

        

    }
    private void setUpPanel() {
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                grid.draw(g);
                drawGameInfo(g);
            }
        };
        panel.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                clickedAt(me);

                
                frame.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(1000, 1200));
        panel.setBackground(Color.black);
        frame.add(panel);
        frame.pack();


    }
    protected void clickedAt(MouseEvent me) {
        if (grid.colFromX(me.getX()) < 0 || grid.colFromX(me.getX()) > 6){
          System.out.println("X out of bounds");
          return;
        } 
        if (grid.rowFromY(me.getY()) < 0 || grid.rowFromY(me.getY()) > 5) {
          System.out.println("Y out of bounds");
          return;
        }
        
        int col = grid.colFromX(me.getX());
        if(grid.colFull(col)) {
          System.out.println("Column "+col+ " full");
          return;
        }
        player++;
        if(player % 2 == 0){
            grid.colClicked(col, true);
        } else {
          grid.colClicked(col, false);
        }
        if (computerplay == true)
        {
          player++;
          int computercol = grid.pickColumn();
          if (computercol != -1)
          {
            if(player % 2 == 0){
              grid.colClicked(computercol, true);
            } else {
              grid.colClicked(computercol, false);
            }
          }
        }
        
    }
    /**
     * Who's turn is it?  How many Checkers played?  Who has won the most games?
     * @param g
     */
    protected void drawGameInfo(Graphics g) {
      g.setColor(Color.LIGHT_GRAY); 
      g.setFont(new Font("Monospaced", Font.BOLD, 20));
	    g.drawString("Turns: " + player, 285, 20);
      if (grid.i != 0){
      if(grid.i == 1){ 
        g.setColor(Color.RED); 
        g.drawString("Red won ", 90, 270);
        redwins = redwins + 1;
        
      } else if (grid.i == 2)
      {
        g.setColor(Color.YELLOW); 
        g.drawString("Yellow won ", 75, 270);
        yellowwins = yellowwins + 1;
        
      }
      g.setColor(Color.LIGHT_GRAY); 
      g.drawString("Yellow wins: " + yellowwins , 285, 120);
      g.drawString("Red wins: " + redwins , 285, 140);
        resetGame();
      }
    
    }
    public void resetGame(){
      grid.clearBoard();
      player = 0;

    }

}
