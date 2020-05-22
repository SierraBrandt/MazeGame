
package maze;


import java.io.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Maze extends JFrame implements Runnable {

    static final int numRows = 24;
    static final int numColumns = 30;
    static final int XBORDER = 20;
    static final int YBORDER = 80;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + numColumns*30;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + numRows*30;
    
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;

    final int PATH = 0;
    final int WALL = 1;
    int board[][] = {
        {WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,PATH,PATH,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,WALL,WALL,PATH,PATH,PATH,WALL,WALL},
        {WALL,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,PATH,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,PATH,WALL},
        {WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,PATH,PATH,WALL,WALL,PATH,PATH,WALL,PATH,WALL,WALL,PATH,WALL},
        {WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,PATH,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,PATH,WALL,PATH,WALL},
        {WALL,WALL,WALL,PATH,PATH,PATH,WALL,WALL,PATH,PATH,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,WALL,PATH,PATH,WALL,WALL},
        {WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,PATH,WALL,PATH,PATH,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL},
        {WALL,PATH,WALL,PATH,WALL,WALL,PATH,PATH,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL},
        {WALL,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,PATH,WALL,PATH,WALL},
        {WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,PATH,PATH,PATH,PATH,WALL,PATH,PATH,WALL,PATH,WALL,PATH,WALL,PATH,PATH,WALL,PATH,WALL,WALL,PATH,WALL},
        {WALL,PATH,PATH,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,PATH,WALL,WALL,WALL,WALL,PATH,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL},
        {WALL,WALL,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,WALL,PATH,WALL,WALL,PATH,PATH,WALL,PATH,PATH,PATH,WALL,WALL,PATH,PATH,PATH,PATH,WALL},
        {WALL,PATH,PATH,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,PATH,WALL,WALL,WALL,PATH,PATH,PATH,PATH,WALL,WALL,WALL,PATH,WALL,WALL,PATH,WALL},
        {WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,PATH,WALL,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,WALL,WALL,WALL,PATH,WALL,PATH,PATH,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,WALL,PATH,WALL,WALL},
        {WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,PATH,PATH,PATH,PATH,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,PATH,WALL,WALL},
        {WALL,PATH,WALL,PATH,PATH,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL,PATH,WALL},
        {WALL,PATH,WALL,PATH,PATH,WALL,WALL,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,PATH,PATH,PATH,WALL,WALL,PATH,PATH,PATH,WALL,WALL,PATH,WALL,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,PATH,PATH,WALL,PATH,WALL,WALL,WALL,PATH,WALL},
        {WALL,PATH,WALL,WALL,WALL,PATH,PATH,WALL,WALL,WALL,WALL,PATH,WALL,WALL,PATH,PATH,PATH,WALL,WALL,WALL,WALL,WALL,PATH,WALL,PATH,WALL,PATH,PATH,PATH,WALL},
        {WALL,PATH,PATH,PATH,PATH,PATH,WALL,WALL,WALL,PATH,PATH,PATH,WALL,WALL,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,WALL,PATH,PATH,PATH,PATH,WALL,WALL,WALL},
        {WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL,WALL}};
    

    int currentRow;
    int currentColumn;
    int columnDir;
    int rowDir;
    
    int numCoins = 10;
    int coinRow[] = new int[numCoins];
    int coinColumn[] = new int[numCoins];
    boolean coinActive[] = new boolean[numCoins];
    int coinsCollected;
    
    static Maze frame;
    public static void main(String[] args) {
        frame = new Maze();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Maze() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button

// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                    columnDir = 0;
                    rowDir = -1;
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    columnDir = 0;
                    rowDir = 1;
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    columnDir = -1;
                    rowDir = 0;
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    columnDir = 1;
                    rowDir = 0;
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        
        g.setColor(Color.red);
//horizontal lines
        for (int zi=1;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        {
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
        }
        
//Display the objects of the board
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
//If the location on the board is snake, then draw the box gray.                
                if (board[zrow][zcolumn] == WALL)
                {
                    g.setColor(Color.gray);
                    g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows + 1);
                }  

            }
        }
        {
        g.setColor(Color.green);
        g.fillRect(getX(0)+currentColumn*getWidth2()/numColumns,
        getY(0)+currentRow*getHeight2()/numRows,
        getWidth2()/numColumns,
        getHeight2()/numRows);
        }
  
        for (int i=0;i<numCoins;i++)
        {
            if(coinActive[i]){
            g.setColor(Color.YELLOW);
            g.fillOval(getX(0)+coinColumn[i]*getWidth2()/numColumns,
            getY(0)+coinRow[i]*getHeight2()/numRows,
            getWidth2()/numColumns,
            getHeight2()/numRows);
            g.setColor(Color.BLACK);
            g.drawOval(getX(0)+coinColumn[i]*getWidth2()/numColumns,
            getY(0)+coinRow[i]*getHeight2()/numRows,
            getWidth2()/numColumns,
            getHeight2()/numRows);
            }
            
        }  
        g.setColor(Color.BLACK);
        g.drawString("score = " + coinsCollected,320,48);
        
        gOld.drawImage(image, 0, 0, null);
    }
////////////////////////////////////////////////////////////////////////////
    public void drawCoin(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        g.setColor(Color.YELLOW);
        g.fillOval(10,10,10,10);
        g.setColor(Color.BLACK);
        g.drawOval(10,10,10,10);
        g.setColor(Color.BLACK);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            
            
            double seconds = .04;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        boolean tryAgain = true;
        while (tryAgain)
        {
        currentRow = (int)(Math.random()*numRows);
        currentColumn = (int)(Math.random()*numColumns);
        if (board[currentRow][currentColumn] == PATH)
        tryAgain = false;
        }
        
        for (int i=0;i<numCoins;i++)
        {
        coinActive[i]= true;
        tryAgain = true;
        while (tryAgain)
            {
            coinRow[i] = (int)(Math.random()*numRows);
            coinColumn[i] = (int)(Math.random()*numColumns);
            if (board[coinRow[i]][coinColumn[i]] == PATH)
            tryAgain = false;
            }
        }
        coinsCollected = 0;
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            reset();
        }
        
        if (board[currentRow+rowDir][currentColumn+columnDir] == PATH)
        {
        currentRow += rowDir;
        currentColumn+=columnDir;
        }
        rowDir = 0;
        columnDir = 0;
        
        for (int i=0;i<numCoins;i++) //loop through all the coins.
        {
            if (coinActive[i]) //only check coins that have not been collected.
            {
                if (coinRow[i] == currentRow && coinColumn[i] == currentColumn) //if the player has just collected a coin.
                {
                //add 2 lines of code that show that you have collected the coin.
                coinActive[i]=false;
                coinsCollected++;
                }
            }
        }
        }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }


/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}


