
package breakout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Runner extends JPanel{
    
    private JFrame frame;
    private Ellipse2D ball;
    private int ballX;
    private int ballY;
    private boolean right;
    private boolean up;
    private Rectangle2D bar;
    private boolean goingRight;
    private boolean goingLeft;
    private long last;
    private ArrayList<Shape> shapes;
    private ArrayList<int[]> colorCodes;
    private int numBlocks = 54;
    private int hit = 0;
    private boolean running;
    
    public Runner(){
        running = false;
        setUp();
        run();
    }
    
    private void setUp(){
        last = System.currentTimeMillis();
        goingRight = false;
        goingLeft = false;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Break Out");
        this.setBackground(Color.black);
        Color color = new Color(20, 20, 20);
        frame.setBackground(color);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ball = new Ellipse2D.Double(255, 430, 20, 20);
        bar = new Rectangle(225,450,80,10);
        int x = 5;
        int y = 5;
        shapes = new ArrayList();
        colorCodes = new ArrayList();
        Random r = new Random();
        for(int i = 0; i < numBlocks; i ++){
            Rectangle2D rect = new Rectangle2D.Double(x,y,50,30);
            shapes.add(rect);
            int[] c = new int[3];
            c[0] = r.nextInt()%200;
            c[1] = r.nextInt()%200;
            c[2] = r.nextInt()%200;
            if(c[0] < 100) c[0] = 78;
            if(c[1] < 100) c[1] = 178;
            if(c[2] < 100) c[2] = 7;
            colorCodes.add(c);
            x+=52;
            if(x+50 > 500){
                y+=32;
                x = 5;
            }
        }
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    goingRight = false;
                    goingLeft = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    goingRight = true;
                    goingLeft = false;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    goingRight = false;
                    goingLeft = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    goingRight = true;
                    goingLeft = false;
                }
               
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    goingLeft = false;
                }
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    goingRight = false;
                }
            }
        
        });
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.GREEN);
        g2.fill(ball);
        g2.setPaint(Color.BLACK);
        g2.fill(bar);
        for(int i = 0; i < shapes.size(); i++){
            if(shapes.get(i) == null) continue;
            Shape s = shapes.get(i);
            int[] c = colorCodes.get(i);
            Color color = new Color(c[0], c[1], c[2]);
            g2.setColor(color);
            g2.fill(s);
        }
    }
    
    public void run(){
        repaint();
        while(true){
            this.repaint();
            frame.repaint();
            double x = ball.getX();
            double y = ball.getY();
            if(checkRight() ||(x < 0 && !right)){
                right = true;
            }else if(checkLeft() || (x+20 > 490 && right)){
                right = false;
            }
            if(checkDown() || (y < 0 && up)){
                up = false;
            }else if( checkUp() || (y+20 > 450 && !up)){
                if(y > 430 && !hitBar()) {
                    JOptionPane.showMessageDialog(null, "You loose");
                    break;
                }
                up = true;
            }
            double xbar = bar.getX();
            long current = System.currentTimeMillis();
            long diff = current - last;
            if(diff > 1){
                if(goingLeft){
                    if(xbar > 0){
                        xbar-=5;
                    }else{
                        xbar = 0;
                    }
                    
                }
                if(goingRight){
                    if(xbar+100 < 500){
                        xbar+=5;
                    }else{
                        xbar = 400;
                    }
                }
                last = current;
            }
            bar.setRect(xbar, 450, 80, 10);
            if(right) x+=0.0005; else x-=0.0005;
            if(up) y-=0.0005; else y+=0.0005;
            ball = new Ellipse2D.Double(x, y, 20, 20);
            
        }
    }
    
    public boolean contact(Shape s){
        return ball.getBounds2D().intersectsLine((Line2D)s);
    }
    
    public boolean hitBar(){
        return ball.getBounds2D().intersects(bar.getBounds2D());
    }
    
    public boolean checkDown(){
        for(int i =0; i < shapes.size(); i++){
            Rectangle2D s = (Rectangle2D)shapes.get(i);
            if(s == null)continue;
            Line2D l = new Line2D.Double(s.getX(),s.getY()+s.getHeight(), s.getX()+s.getWidth(), s.getY()+s.getHeight());
            if(contact(l)){
                shapes.set(i, null);
                return true;
            }
        }
        return false;
    }
    
    public boolean checkLeft(){
        for(int i =0; i < shapes.size(); i++){
            Rectangle2D s = (Rectangle2D)shapes.get(i);
            if(s == null)continue;
            Line2D l = new Line2D.Double(s.getX(),s.getY(), s.getX(), s.getY()+s.getHeight() );
            if(contact(l)){
                shapes.set(i, null);
                return true;
            }
        }
        return false;
    }
    
    public boolean checkRight(){
        for(int i =0; i < shapes.size(); i++){
            Rectangle2D s = (Rectangle2D)shapes.get(i);
            if(s == null)continue;
            Line2D l = new Line2D.Double(s.getX()+s.getWidth(),s.getY(), s.getX()+s.getWidth(), s.getY()+s.getHeight() );
            if(contact(l)){
                shapes.set(i, null);
                return true;
            }
        }
        return false;
    }
    
    public boolean checkUp(){
        for(int i =0; i < shapes.size(); i++){
            Rectangle2D s = (Rectangle2D)shapes.get(i);
            if(s == null)continue;
            Line2D l = new Line2D.Double(s.getX(),s.getY(), s.getX()+s.getWidth(), s.getY());
            if(contact(l)){
                shapes.set(i, null);
                return true;
            }
        }
        return false;
    }
}
