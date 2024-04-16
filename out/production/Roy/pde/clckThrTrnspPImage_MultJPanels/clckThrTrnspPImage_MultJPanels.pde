
// Import Libraries
import java.awt.*;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import javax.swing.Jpanel;
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
PGraphics p0, p1, p2;
JFrame frame;
Jpanel pnl0;
Jpanel pnl1;
Jpanel pnl2;
PApplet applet = this;

float scaleFactor = 1.64;
float offsetX = 0;
float offsetY = 0;

void setup(){
  surface.setSize(400, 400);
  surface.setLocation(surfacePosX, surfacePosY);

  frame = (JFrame)((PSurfaceAWT.SmoothCanvas) getSurface().getNative()).getFrame();
  frame.removeNotify();
  frame.setUndecorated(true);
  frame.setLayout(null);
  frame.addNotify();

  p0 = createGraphics(width, height);
  p1 = createGraphics(width, height);
  p2 = createGraphics(width, height);

  Jpanel pnl0 = new Jpanel() {
    @Override
      protected void paintComponent(Graphics graphics) {
      if (graphics instanceof Graphics2D) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(p0.image, 0, 0, null);
      }
    }
  };

  Jpanel pnl1 = new Jpanel() {
    @Override
      protected void paintComponent(Graphics graphics) {
      if (graphics instanceof Graphics2D) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(p1.image, 0, 0, null);
      }
    }
  };

  Jpanel pnl2 = new Jpanel() {
    @Override
      protected void paintComponent(Graphics graphics) {
      if (graphics instanceof Graphics2D) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawImage(p2.image, 0, 0, null);
      }
    }
  };

  frame.setContentPane(pnl0);
  pnl0.setFocusable(true);
  pnl0.setFocusTraversalKeysEnabled(true);
  pnl0.requestFocus();
  pnl0.requestFocusInWindow();
  
  frame.setContentPane(pnl1);
  pnl1.setFocusable(true);
  pnl1.setFocusTraversalKeysEnabled(true);
  pnl1.requestFocus();
  pnl1.requestFocusInWindow();

  frame.setContentPane(pnl2);
  pnl2.setFocusable(true);
  pnl2.setFocusTraversalKeysEnabled(true);
  pnl2.requestFocus();
  pnl2.requestFocusInWindow();

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

  pnl0.addMouseListener(mA);
  pnl1.addMouseListener(mA);
  pnl2.addMouseListener(mA);

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

  pnl0.addMouseMotionListener(mA);
  pnl1.addMouseMotionListener(mA);
  pnl2.addMouseMotionListener(mA);


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

  pnl1.addKeyListener(kL);
  pnl2.addKeyListener(kL); 
}

void draw(){
  frame.setBackground(new Color(0, 0, 0, 0.000));

  p0.beginDraw();
  p0.background(0, 0, 0, 0);
  p0.endDraw();

  p1.beginDraw();
  p1.background(0, 0, 0, 0);
  p1.endDraw();

  p2.beginDraw();
  p2.background(0, 0, 0, 0);
  p2.endDraw();

  p1.beginDraw();
  p1.loadPixels();
  for (int x = 0; x < width; ++x) {
    for (int y = 0; y < height; ++y) {
      float scaledX = (x - offsetX) / scaleFactor;
      float scaledY = (y - offsetY) / scaleFactor;
      float d = dist(scaledX, scaledY, mouseX, mouseY);
      float maxDist = dist(0, 0, width / (2 * scaleFactor), height / (2 * scaleFactor));
      float mid = map(d, maxDist / 0.8, 0, 0, 400);
      int loc = int(scaledX) + int(scaledY) * width;
      p1.pixels[loc] = color(d, scaledY / 2, scaledX / 2, mid);
    }
  }
  updateSurface();
  p1.updatePixels();
  p1.filter(INVERT);
  p1.endDraw();
  

  for (int y = 0; y <= height+40; y = y + 40) {
    for (int x = 0; x <= width+40; x = x + 40) {
      int o = 30;
      float d = dist(x,y, mouseX, mouseY);
      float maxDist = dist(0, 0, width/2, height/2);
      float mid = map(d, 0, maxDist/3, 0, 400);
      p2.beginDraw();
      p2.noStroke();
      p2.fill(color(255, 255, 255, map(d, 0, maxDist/4, 400, 0)));
      p2.ellipse(x, y, o, o);
      if(mousePressed){
        p2.noFill();
        p2.stroke(color(255, 255, 255, map(d, 0, maxDist/6, 400, 0)));
        p2.strokeWeight(15);
        p2.ellipse(x, y, o, o);
      }
      p2.endDraw();
    }
    updateSurface();  
  }

  int thk = 2;
  p0.beginDraw();
  p0.strokeWeight(thk);
  p0.noFill();
  p0.stroke(255);
  p0.rect(thk-(thk/1.5), thk-(thk/1.5), width-(thk/1.5), height-(thk/1.5));
  p0.noStroke();
  p0.endDraw();
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