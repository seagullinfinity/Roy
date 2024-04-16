import processing.core.PGraphics;

import java.awt.*;

public class Bar extends ClearWin{
    TPanel barPanel;

/*    public static void main(String[] args){
        PApplet.main(java.lang.invoke.MethodHandles.lookup().lookupClass());
    }*/

    int bgf = color(0,0,0,0.002f);
    private PGraphics tg;
//    int bgp = new Color(255, 0, 0, 0.5f).getRGB();

    @Override
    public void settings(){
        super.settings();
    }

    @Override
    public void construct() {
        super.construct();
        Color bgp = new Color(255,0,100, 100);
        barPanel = new TPanel();
        barPanel.setBounds(0, 0, 400, 40);
        barPanel.setBackground(bgp);
        frame.setLayout(null);
        addPanel(barPanel);
    }

    @Override
    public void setup(){
        super.setup();
        println("red bar");
    }

    @Override
    public void draw(){
        super.draw();
        pg.beginDraw();
        pg.strokeWeight(1);
        pg.fill(0,0,0,1.f);
        pg.stroke(255, 100);
        pg.rect(60, 50, width/2, height/2);
        pg.endDraw();
    }
}