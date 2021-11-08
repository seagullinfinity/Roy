package draw;

import java.awt.AWTException;
import applet.ClearWin;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.awt.*;
import java.util.ArrayList;

public class OverlayDraw{

    PImage screenshot;
    ClearWin p;

    public OverlayDraw(ClearWin p) {
        this.p = p;
    }

    private ArrayList <PVector> points;
    ArrayList <Movement> movements = new ArrayList <>();
    float deleteRadius = 40;

    public void construct() {
        points = new ArrayList <>();
/*        colorMode(HSB);*/
        screenshot();
    }

    public void display() {

        if (screenshot != null) {
            p.image(screenshot, 0, 0, p.width, p.height);
        }

        for (Movement m : movements) {
            p.beginShape();
            for (Line line : m.lines) {
                p.noFill();
                p.stroke(255);
                if (line.skip) {
                    p.noStroke();
                }
                p.strokeWeight(1);
                p.vertex(line.x0, line.y0);
                p.vertex(line.x1, line.y1);
            }
            p.endShape();
        }
    }

    public void screenshot() {
        try {
            screenshot = new PImage(new Robot().createScreenCapture(new Rectangle(0, 0, p.displayWidth,
            p.displayHeight)));
        } catch (AWTException e) {
        }
        p.getSurface().setVisible(true);
    }

    public void keyPressed() {
        p.keyPressed();
        if (p.keyCode == 90) {
            deleteLastMovement();
        }
    }

    public void mousePressed() {
        p.mousePressed();
        if (p.mouseButton == p.LEFT) {
            movements.add(new Movement());
        } else if (p.mouseButton == p.RIGHT) {
            p.circle(p.mouseX, p.mouseY, 40);
        }
        mouseInteract();
    }

    public void mouseReleased() {
        p.mouseReleased();
    }

    public void mouseDragged() {
        p.mouseDragged();
        mouseInteract();
    }

    public void mouseInteract() {
        if (p.mouseButton == p.RIGHT) {
            drawDeleteCircle();
            removeLinesCloseTo(p.mouseX, p.mouseY);
        } else if (p.mouseButton == p.LEFT) {
            makeLine();
        }
    }

    public void drawDeleteCircle() {
        float x = p.mouseX;
        float y = p.mouseY;
        p.stroke(255);
        p.strokeWeight(2);
        p.noFill();
        p.ellipse(x, y, deleteRadius, deleteRadius);
    }

    public void makeLine() {
        if (! p.mousePressed) {
            return;
        }
        getCurrentMovement().lines.add(new Line(p.pmouseX, p.pmouseY, p.mouseX, p.mouseY));
    }

    Movement getCurrentMovement() {
        if (movements.isEmpty()) {
            movements.add(new Movement());
        }
        return movements.get(movements.size() - 1);
    }

    public void deleteLastMovement() {
        if (movements.isEmpty()) {
            return;
        }
        movements.remove(movements.size() - 1);
    }

    public void removeLinesCloseTo(float x, float y) {
        for (Movement m : movements) {
            for (Line line : m.lines) {
                float dist = PApplet.min(PApplet.dist(line.x0, line.y0, x, y), PApplet.dist(line.x1, line.y1, x, y));
                if (dist < 40) {
                    line.skip = true;
                }
            }
        }
    }

    static class Movement {
        ArrayList <Line> lines = new ArrayList <>();
    }

    static class Line {
        public boolean skip;
        float x0, y0, x1, y1;

        Line(float x0, float y0, float x1, float y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }
    }
}
