package refapp;

import applet.ClearWin;
import processing.core.PApplet;
import processing.core.PImage;

public class ImageBoard {

    PImage webImg;
    ClearWin p;

    public ImageBoard(ClearWin p) {
        this.p = p;
    }

    public void construct() {
                    PApplet.println("setup()");
        p.background(0);
                    PApplet.println("background");
    }

    public void load(){

        if (p.key == 32){
            String url = "https://i.imgur.com/91TZLL0.png";
                    PApplet.println("string");

            this.webImg = p.loadImage(url, "png");
                    PApplet.println("image loaded");
            }
        if (webImg.width == -1){
            p.exit();
        }
        this.drawImage();
    }


    public void drawImage(){

        if (webImg.width > 0){
            if (webImg.width > 300){
                webImg.resize(300, 300);
                    PApplet.println("image resized");
            }
            if (webImg.width <= 300){
                p.getSurface().setSize(webImg.width, webImg.height);
                    PApplet.println("window resized");
            }
        }
        if(webImg != null){
            p.image(webImg, 0, 0);
                    PApplet.println("image should be drawn");
        }
        p.noLoop();
    }
}