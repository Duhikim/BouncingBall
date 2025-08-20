import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 1000;
    Image ball;
    Image backgroundImage;
    Timer timer;
    final double gravity = 0.3;
    double xVelocity = 3;
    double yVelocity = 0;
    double x = 0;
    double y = 20;
    double prevX = 0;
    double prevY = 0;
    int ballWidth = 10;
    int ballHeight = 10;
    double yMin = y;
    double rectX = 50;
    double rectY = 100;
    double rectW = 400;
    double rectH = 800;

    public MyPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);

        timer = new Timer(1, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 기존 그림 지움 (배경 다시 칠하기)

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.fillOval((int)x, (int)y, ballWidth, ballHeight);

        g2d.fillRect((int)rectX, (int)rectY, (int)rectW, (int)rectH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        prevX = x;
        x += xVelocity;
        prevY = y;
        y += yVelocity;

        if(y < yMin){
            yMin = Math.min(yMin, y);
        }

        if(x >= PANEL_WIDTH - ballWidth || x < 0) xVelocity *= -1;
        if(y < 0) yVelocity *= -1;
        if(y > PANEL_HEIGHT-ballHeight) {
            yVelocity = (yVelocity>0)?-Math.sqrt(2*gravity*(y-yMin)) : Math.sqrt(2*gravity*(y-yMin));
        }

        if(y + ballHeight >= rectY && y <= rectY+rectH && x + ballWidth >= rectX && x <= rectX+rectW){
            if((prevY + ballHeight <= rectY) || prevY >= rectY + rectH) {
                yVelocity = (yVelocity>0)?-Math.sqrt(2*gravity*(y-yMin)) : Math.sqrt(2*gravity*(y-yMin));
            }
            if(prevX + ballWidth <= rectX || prevX >= rectX + rectW) xVelocity *= -1;

            if(rectW > 0) {
                double dX = 30;
                double dY = 60;
                rectX += dX/2;
                rectY += dY/2;
                rectW -= dX;
                rectH -= dY;
            }
        }
        rectX -=0.1;
        rectY -=0.2;
        rectW +=0.2;
        rectH +=0.4;

        yVelocity += gravity;
        repaint();
    }
}
