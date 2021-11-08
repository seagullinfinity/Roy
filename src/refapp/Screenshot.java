package refapp;

import applet.ClearWin;
import processing.core.PImage;
import java.awt.*;

public class Screenshot {

    PImage screenshot;
    ClearWin p;

    public Screenshot(ClearWin p) {
        this.p = p;
    }

    public void construct() {
        p.size(p.displayWidth, p.displayHeight, p.JAVA2D);
    }

    public void display() {
        screenshot();
        if (screenshot != null) p.image(screenshot, 0, 0, p.width, p.height);
    }

    public void screenshot() {
        try {
    screenshot = new PImage(new Robot().createScreenCapture(new Rectangle(0, 0, p.displayWidth, p.displayHeight)));
  } catch (AWTException e) { }
    }
}