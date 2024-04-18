package Outliner;
import javax.swing.*;
import java.awt.*;

public class TPanel extends JPanel {

//    public PGraphics tg;
//    public TPanel(PGraphics graphics) {
//        this.tg = graphics;
//    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
//            g2d.drawImage(tg.image, 0, 0, null);
//            g2d.setColor(new Color(0, 0, 255, 255)); // Semi-transparent red
//            g2d.fillRect(0, 0, getWidth(), getHeight()); // Fill the panel with a rectangle
        }
    }
}
