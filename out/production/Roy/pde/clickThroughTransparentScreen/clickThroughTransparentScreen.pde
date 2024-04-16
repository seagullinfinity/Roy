// Import Libraries
import java.awt.*;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

private int surfacePosX = 800;
private int surfacePosY = 200;
private Point mouse = new Point(0, 0);

//Declare Gobal Variables
PGraphics pg;
JFrame frame;
JPanel panel;
PApplet applet = this;

float scaleFactor = 1;
float offsetX = 0;
float offsetY = 0;

void setup(){
  surface.setSize(400, 400);
  surface.setLocation(surfacePosX, surfacePosY);

  frame = (JFrame)((PSurfaceAWT.SmoothCanvas) getSurface().getNative()).getFrame();
  frame.removeNotify();
  frame.setUndecorated(true);
  // frame.setResizable(true);
  frame.setLayout(null);
  frame.addNotify();

  pg = createGraphics(width, height);

  JPanel panel = new JPanel() {
    @Override
      protected void paintComponent(Graphics graphics) {
      if (graphics instanceof Graphics2D) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(pg.image, 0, 0, null);
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
      if (me.getButton() == MouseEvent.BUTTON3) {
            offsetX += mouseX - me.getX();
            offsetY += mouseY - me.getY();
        }
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
      mouseX = MouseInfo.getPointerInfo().getLocation().x-frame.getLocation().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y-frame.getLocation().y;
      applet.mouseDragged();
    }
  };

  mA = new MouseAdapter() {

    public void mouseMoved(MouseEvent me) {
      mouseX = MouseInfo.getPointerInfo().getLocation().x-frame.getLocation().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y-frame.getLocation().y;
      applet.mouseMoved();
    }
  };

  panel.addMouseMotionListener(mA);


  KeyListener kL = new KeyListener() {

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
}

void draw(){
  frame.setBackground(new Color(0, 0, 0, 0.000));

  pg.beginDraw();
  pg.background(0, 0, 0, 0);
  pg.endDraw();

//________________________________________Gradient
  pg.beginDraw();
  pg.loadPixels();
  for (int x = 0; x < width; ++x) {
    for (int y = 0; y < height; ++y) {
      float scaledX = (x - offsetX) / scaleFactor;
      float scaledY = (y - offsetY) / scaleFactor;
      float d = dist(scaledX, scaledY, mouseX, mouseY);
      float maxDist = dist(0, 0, width / (2 * scaleFactor), height / (2 * scaleFactor));
      float mid = map(d, 0, maxDist / 0.8, 0, 400);
      int loc = int(scaledX) + int(scaledY) * width;
      pg.pixels[loc] = color(d, scaledY / 2, scaledX / 2, mid);
    }
  }
  updateSurface();
  pg.updatePixels();
  pg.filter(INVERT);
  pg.endDraw();
  

//________________________________________Dots
  for (int y = 0; y <= height+40; y = y + 40) {
    for (int x = 0; x <= width+40; x = x + 40) {
      int o = 30;
      float d = dist(x,y, mouseX, mouseY);
      float maxDist = dist(0, 0, width/2, height/2);
      float mid = map(d, 0, maxDist/3, 0, 400);
      pg.beginDraw();
      pg.noStroke();
      pg.fill(color(255, 255, 255, map(d, 0, maxDist/4, 400, 0)));
      pg.ellipse(x, y, o, o);
      if(mousePressed){
        pg.noFill();
        pg.stroke(color(255, 255, 255, map(d, 0, maxDist/6, 400, 0)));
        pg.strokeWeight(15);
        pg.ellipse(x, y, o, o);
      }
      pg.endDraw();
    }
    updateSurface();
    
  }

//________________________________________Frame rect
  int thk = 2;
  pg.beginDraw();
  pg.strokeWeight(thk);
  pg.noFill();
  pg.stroke(255);
  pg.rect(thk-(thk/1.5), thk-(thk/1.5), width-(thk/1.5), height-(thk/1.5));
  pg.noStroke();
  pg.endDraw();
  updateSurface();
}

private void updateSurface() {

  // println("update surface");
  Point pm = mouse;
  mouse = MouseInfo.getPointerInfo().getLocation();
  if (mousePressed) {
    surfacePosX -= (pm.x - mouse.x);
    surfacePosY -= (pm.y - mouse.y);
    surface.setLocation(surfacePosX, surfacePosY);
  }
}