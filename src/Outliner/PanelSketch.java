package Outliner;

import java.awt.*;

public class PanelSketch extends ClearWin {
    TPanel panel1;
    TPanel panel2;

    @Override
    public void settings(){
        super.settings();
        size(600, 400);
    }

    @Override
    public void construct() {
        super.construct();
        Color bgp1 = new Color(150,0,255, 100);
        Color bgp2 = new Color(150,130,255, 200);

        panel1 = new TPanel();
        panel2 = new TPanel();

        // Set size and position of panels
        panel1.setBounds(0, 0, 400, 40);
        panel2.setBounds(300, 0, 250, height);
        panel1.setBackground(bgp1);
        panel2.setBackground(bgp2);
        frame.setLayout(null);
        addPanel(panel1);
        addPanel(panel2);

    }

    @Override
    public void setup(){
        super.setup();
        println("blue bar green square");
    }

    @Override
    public void draw(){
        super.draw();
//        background(255);
    }

    @Override
    public void display(){
        super.display();
        pg.beginDraw();
        pg.stroke(255, 255, 255, 10);
        pg.noFill();
        pg.strokeWeight(2);
        pg.rect(0, 0, width - 1, height - 1);
        pg.endDraw();

        pg.beginDraw();
        pg.strokeWeight(1);
        pg.fill(0,0,0,1.f);
        pg.stroke(255, 100);
        pg.rect(60, 50, width/2, height/2);
        pg.endDraw();
    }
}
