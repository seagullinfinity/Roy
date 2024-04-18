import applet.ClearWin;
import refapp.Clipix;
import refapp.Screenshot;
import refapp.RefApp;
import draw.OverlayDraw;
import refapp.ImageBoard;
import java.awt.*;

public class Roy extends ClearWin{
    public static void main(String[] args) {
        ClearWin.main(java.lang.invoke.MethodHandles.lookup().lookupClass());
    }

    RefApp refapp;
    ClearWin clearwin;
    OverlayDraw overlaydraw;
    Screenshot screenshot;
    Clipix clipix;
    ImageBoard imageboard;

    @Override
    public void settings() {
        clearwin = new ClearWin();
        refapp = new RefApp(this);
        imageboard = new ImageBoard(this);
        screenshot = new Screenshot(this);
        overlaydraw = new OverlayDraw(this);
        clipix = new Clipix(this);
        clipix.window();
    }

    @Override
    public void setup(){
        super.undecoratedFrame();
        clipix.clipBoard();
    }

    @Override
    public void draw() {
        bg = new Color(0x54FF0000, true);
        border = 1f;
        lth = 0.01f;
        clipix.display();
    }

    @Override
    public void keyPressed() {
        if (key == ESC) {
            exit();
        }
    }
}