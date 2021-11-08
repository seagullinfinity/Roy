package refapp;

import applet.ClearWin;
import refapp.ImageBoard;

public class RefApp extends ClearWin {

    ImageBoard imageboard;
    ClearWin p;

    public RefApp(ClearWin p) {
        this.p = p;
    }

    public void settings() {
        imageboard = new ImageBoard(this);
//        imageboard.settings();
    }

    public void setup() {
        imageboard.construct();
    }

    public void draw() {
        imageboard.load();
    }
}