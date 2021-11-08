package draw;

import applet.ClearWin;
import processing.core.*;
import processing.core.PGraphics;
import java.awt.*;
import java.util.ArrayList;

public class SmoothLine {

    ClearWin p;

    public SmoothLine(ClearWin p) {
        this.p = p;
    }

    PImage screenshot;
    private ArrayList <PVector> points;
    private int maxPoints = 60;
    private int markDistance = 1;
    /*TODO
     the point dencity should be mouse direction dependant, increse the dencity on every curve of the line, and
     decreese if the line is thraight. Also decreese if the mouse movement speeds up, and increase again, if it
     slows down*/
    private PGraphics pg;

    PVector ta = new PVector(0.0f, 0.0f);
    PVector mta = new PVector(0.0f, 0.0f);
    PVector m = new PVector(0.0f, 0.0f);

    public void settings() {
        p.fullScreen();
    }

    public void setup() {
        points = new ArrayList <>();
        pg = p.createGraphics(p.width, p.height);
        screenshot();
        p.background(0);
    }

    public void draw() {

        pg.beginDraw();
        pg.noStroke();
        pg.stroke(255);
        pg.strokeWeight(1);
        pg.noFill();
        // draw curve
        pg.beginShape();
        if (screenshot != null) {
            pg.image(screenshot, 0, 0, p.width, p.height);
        }
        if (points.size() > 0) {

            // begin anchor
            pg.curveVertex(points.get(0).x, points.get(0).y);

            // curve points
            for (PVector p : points) {
                pg.curveVertex(p.x, p.y);
//              ellipse(p.x, p.y, 3, 3);
            }
            // end anchor
            pg.curveVertex(points.get(points.size() - 1).x, points.get(points.size() - 1).y);
        }
        pg.endShape();
        pg.endDraw();
        p.image(pg, 0, 0);
    }

    public void mouseMoved() {

        //float bend = ta.dot(60, 20, 0);
        // add point when new is distant from old
        if (points.size() < 2 || points.get(points.size() - 2).dist(new PVector(p.mouseX, p.mouseY)) > markDistance) {

            points.add(new PVector(p.mouseX, p.mouseY));

            // erase from back of line
            if (points.size() > maxPoints) {
                points.remove(0);
            }
        } else {
            // nudge -- update last point if too close to previous
            points.set(points.size() - 1, new PVector(p.mouseX, p.mouseY));
        }
    }

    public void screenshot() {
        try {
            screenshot = new PImage(new Robot().createScreenCapture(new Rectangle(0, 0, p.displayWidth, p.displayHeight)));
        } catch (AWTException e) {
        }
    }

    public void keyPressed() {
        if (p.key == p.ESC) {
            p.exit();
        }
    }

    PVector m() {
        return new PVector(p.mouseX, p.mouseY);
    }

    PVector mta() {
        return new PVector(PApplet.tan(p.mouseX), PApplet.tan(p.mouseY));
    }

    PVector dta() {
        return new PVector(PApplet.tan(p.mouseX), PApplet.tan(p.mouseY));
    }
}