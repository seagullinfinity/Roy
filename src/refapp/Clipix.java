package refapp;

import applet.ClearWin;
import java.awt.*;
import processing.core.PImage;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;

public class Clipix {

    ClearWin p;
    PImage img;
    BufferedImage image = null;

    public Clipix(ClearWin p) {
        this.p = p;
    }

    public void window() {
        p.size(600, 400);
    }

    public void clipBoard() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = null;
        for (final DataFlavor df : c.getAvailableDataFlavors())
            if (df.getRepresentationClass().equals(Image.class))
                flavor = df;
        if (flavor != null)
            try {
                image = (BufferedImage) c.getData(flavor);
                img = new PImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        p.getSurface().setSize(img.width, img.height);
        display();
    }

    public void display(){
        p.image(img, 0, 0);
    }

}