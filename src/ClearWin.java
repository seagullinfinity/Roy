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

// LEFT

public class ClearWin extends PApplet {

    JFrame frame;
    PApplet applet = this;
    private int surfacePosX = displayWidth / 2;
    private int surfacePosY = displayHeight / 2;
    private Point mouse = new Point(0, 0);
    public PGraphics pg;
    private boolean moving = false;
    public Color bg = new Color(0, 0, 0, 0.f);
    private Color bgp = new Color(150,130,255, 200);
    protected String hello = "hello world";

    public void addPanel(JPanel panel) {
        // add(panel);
        frame.add(panel);
    }

    public void removePanel(JPanel panel) {
        // remove(panel);
        frame.remove(panel);
    }

    public void settings(){
        smooth(1);
    }

    public void setup() {
        // do stuff here that always happens before setup() in the extending sketch
        construct();
    }

    public void draw() {
        // do stuff here that always happens before draw() in the extending sketch
        display();
    }

    public void construct() {

        imageMode(CENTER);
        rectMode(CENTER);

        surface.setVisible(false);
        surface.setSize(400, 400);
        surface.setLocation(surfacePosX, surfacePosY);

        frame = (JFrame) ((PSurfaceAWT.SmoothCanvas) getSurface().getNative()).getFrame();
        frame.removeNotify();
        frame.setUndecorated(true);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.setBackground(bg);
        frame.addNotify();

        surface.setVisible(true);

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
        panel.setBounds(300, 0, 250, height);
        panel.setBackground(bgp);

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
        // spawnSurface();
    }

    public void display() {

        updateSurface();
        frame.setBackground(bg);

        pg.beginDraw();
        pg.stroke(255);
        pg.noFill();
        pg.strokeWeight(2);
        pg.rect(0, 0, width - 1, height - 1);
        pg.endDraw();
        // Move();
    }

    private void spawnSurface() {

        Point pm = mouse;
        mouse = MouseInfo.getPointerInfo().getLocation();
        surfacePosX -= (pm.x - mouse.x);
        surfacePosY -= (pm.y - mouse.y);
        surface.setLocation(displayWidth/2 - (width / 2), displayHeight/2 - (height / 2));
        frame.setVisible(true);
        Move();
    }

    private void Move() {
        if (key == ESC) {
            exit();
        }
        Point pm = mouse;
        mouse = MouseInfo.getPointerInfo().getLocation();
        if (mousePressed) {
            surfacePosX -= (pm.x - mouse.x);
            surfacePosY -= (pm.y - mouse.y);
            surface.setLocation(surfacePosX, surfacePosY);
        }
    }

    public void keyReleased() {
        moving = false;
    }

    private void updateSurface() {
        Point pm = mouse;
        mouse = MouseInfo.getPointerInfo().getLocation();
        if (mousePressed) {
            surfacePosX -= (pm.x - mouse.x);
            surfacePosY -= (pm.y - mouse.y);
            surface.setLocation(surfacePosX, surfacePosY);
        }
        if (key == ESC) {
            exit();
        }
    }
}