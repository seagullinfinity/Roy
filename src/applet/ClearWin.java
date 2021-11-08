package applet;

import processing.core.PApplet;
import processing.core.PGraphics;
import java.awt.*;
import processing.awt.PSurfaceAWT;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClearWin extends PApplet {

    JFrame frame;
    PApplet applet = this;

    private boolean moveWin = false;
    private boolean space = false;

    private int surfacePosX = 0;
    private int surfacePosY = 0;
    private Point mouse = new Point(0, 0);
    public PGraphics pg;

    public float border = 1f;
    public float lth = 0.01f;
    public Color bg = new Color(0x810095FF, true);

    static final String RENDERER = JAVA2D;
//    static final String RENDERER = FX2D;

    public void settings() {
        size(600, 400, RENDERER);
        smooth(1);
    }
//    ------------------------------------------------------------------------------------------------------------------

    public void undecoratedFrame() {
        frame = (JFrame) ((PSurfaceAWT.SmoothCanvas) getSurface().getNative()).getFrame();
        frame.removeNotify();
        frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.addNotify();
    }

    public void setup() {

        surface.setVisible(false);
        frame.setBackground(bg);
        undecoratedFrame();

        pg = createGraphics(width + 16, height + 40);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                if (graphics instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.drawImage(pg.image, 0, 0, null);
                    //TODO make a modular slot for adding as many image layers as I want, in a separate class
                }
            }
        };

        frame.setContentPane(panel);
        panel.setFocusable(true);
        panel.setFocusTraversalKeysEnabled(true);
        panel.requestFocus();
        panel.requestFocusInWindow();

        MouseAdapter mA =
                new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {
                        mousePressed = true;
                        applet.mousePressed();
                    }

                    public void mouseReleased(MouseEvent me) {
                        mousePressed = false;
                        applet.mouseReleased();
                    }
                };

        panel.addMouseListener(mA);

        mA = new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                mouseX = MouseInfo.getPointerInfo().getLocation().x - frame.getLocation().x;
                mouseY = MouseInfo.getPointerInfo().getLocation().y - frame.getLocation().y;
                applet.mouseDragged();
            }
        };

        panel.addMouseMotionListener(mA);

        KeyListener kL =
                new KeyListener() {

                    public void keyTyped(KeyEvent e) {
                        key = e.getKeyChar();
                        keyCode = e.getKeyCode();
                        applet.keyTyped();
                    }

                    public void keyReleased(KeyEvent e) {
                        key = e.getKeyChar();
                        keyCode = e.getKeyCode();
                        applet.keyReleased();
                        keyPressed = false;
                    }

                    public void keyPressed(KeyEvent e) {
                        key = e.getKeyChar();
                        keyCode = e.getKeyCode();
                        applet.keyPressed();
                        keyPressed = true;
                    }
                };

        panel.addKeyListener(kL);
        spawnSurface();
    }

    public void draw() {

        frame.setBackground(bg);

        pg.beginDraw();
        pg.stroke(255);
        pg.noFill();
        pg.strokeWeight(border);
        pg.rect(0, 0, width + 16 - 1, height + 39 - 1);
        pg.strokeWeight(lth);
        pg.line(0, 0, width + 16 - 1, height + 39 - 1);
        pg.endDraw();
    }

    public void spawnSurface() {

        Point pm = mouse;
        mouse = MouseInfo.getPointerInfo().getLocation();
        surfacePosX -= (pm.x - mouse.x);
        surfacePosY -= (pm.y - mouse.y);
        surface.setLocation(surfacePosX - (width / 2), surfacePosY - (height / 2));
        frame.setVisible(true);
    }

    public void keyPressed() {

        space = key == 32;

        if (space && moveWin) {
            surface.setLocation(surfacePosX - (width / 2), surfacePosY - (height / 2));
        }
        if (key == ESC) {
            exit();
        }
    }

    public void mouseDragged() {
        Point pm = mouse;
        mouse = MouseInfo.getPointerInfo().getLocation();
        surfacePosX -= (pm.x - mouse.x);
        surfacePosY -= (pm.y - mouse.y);

        moveWin = mousePressed && key == 32;
        space = key == 32;
    }

    public void keyReleased() {
        moveWin = key == 32;
        space = key == 32;
    }

    public void mouseReleased() {
        moveWin = false;
    }
}




