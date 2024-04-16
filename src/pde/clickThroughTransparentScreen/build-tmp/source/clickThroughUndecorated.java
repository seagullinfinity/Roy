import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.awt.PSurfaceAWT; 
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

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class clickThroughUndecorated extends PApplet {

//Import Libraries














//Declare Gobal Variables
PGraphics pg;
JFrame frame;
JPanel panel;
PApplet applet = this;

public void setup()
{

  // size(200, 200); 
  

  frame = (JFrame)((PSurfaceAWT.SmoothCanvas) getSurface().getNative()).getFrame();
  frame.removeNotify();
  frame.setUndecorated(true);
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
  panel.setFocusTraversalKeysEnabled(false);
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
      mouseX = MouseInfo.getPointerInfo().getLocation().x-frame.getLocation().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y-frame.getLocation().y;
      applet.mouseDragged();
    }
    public void mouseMoved(MouseEvent me) {
      mouseX = MouseInfo.getPointerInfo().getLocation().x-frame.getLocation().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y-frame.getLocation().y;
      applet.mouseMoved();
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
}

public void draw()
{
  pg.beginDraw();
  pg.background(0, 0);
  if (mousePressed) {
    pg.fill(0, 153, 204, 126);
  } else {
    pg.fill(0, 204, 153, 126);
  }
  pg.ellipse(mouseX, mouseY, 60, 60);
  pg.fill(0, 255);
  pg.stroke(0, 255);
  pg.text(key, mouseX-3, mouseY-10);
  pg.endDraw();
  frame.setBackground(new Color(0, 0, 0, 0));
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "clickThroughUndecorated" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
