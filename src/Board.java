
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int Height = 400;
    int Width = 400;
    int Max_Dots = 1600;
    int Dot_size = 10;
    int Dots;
    int x[] = new int[Max_Dots];
    int y[] = new int[Max_Dots];
    int apple_x;
    int apple_y;
    Image body;
    Image head;
    Image apple;
    Timer timer;
    int DELAY = 150;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    Board() {
        TAdaptar tAdaptar = new TAdaptar();
        addKeyListener(tAdaptar);
        setFocusable(true);
        setPreferredSize(new Dimension(Width, Height));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }

    public void initGame() {
        Dots = 3;
        x[0] = 50;
        y[0] = 50;
        for (int i = 1; i < Dots; i++) {
            x[i] = x[i - 1] + Dot_size;
            y[i] = y[i - 1];
        }
        locateapple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void loadImages() {
        ImageIcon bodyIcon = new ImageIcon("src/Resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/Resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/Resources/apple.png");
        apple = appleIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < Dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }else{
            gameOver(g);
            timer.stop();
        }
    }
    public void locateapple(){
        apple_x = ((int)(Math.random()*39))*Dot_size;
        apple_y = ((int)(Math.random()*39))*Dot_size;

    }
    // checks snakes touches borders
    public void checkCollision(){
        for(int i = 1; i<Dots;i++){
            if(i>4&& x[0] == x[1] && y[0] == y[1]){
                inGame = false;
            }
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(x[0]>= Width){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
        if(y[0]>= Height){
            inGame = false;
        }


    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (Dots -3) * 100;
        String scoremsg = "Score: "+ Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Width-fontMetrics.stringWidth(msg))/2, Height/4);
        g.drawString(scoremsg, (Width-fontMetrics.stringWidth(msg))/2, 3*(Height/4));

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            move();
            checkCollision();
        }

        repaint();
    }
    public void move(){
        for(int i = Dots-1; i> 0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -= Dot_size;
        }
        if(rightDirection){
            x[0] += Dot_size;
        }
        if(upDirection){
            y[0] -= Dot_size;
        }
        if(downDirection){
            y[0] += Dot_size;
        }

    }
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            Dots++;
            locateapple();
        }
    }
    private class TAdaptar extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && !downDirection){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }

    }
}
