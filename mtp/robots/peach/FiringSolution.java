package mtp.robots.peach;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class FiringSolution {
    public final Triangle[] triangles = new Triangle[10];
    int i = 0;

    public void addTriangle(Triangle t) {
        if (i >= triangles.length) { i = 0; }
        triangles[i] = t;
        i++;
    }

    public void drawSolutions(Graphics2D gr) {
        int r = 5;

        for (Triangle t : triangles) {
            if (t == null) continue;
            Point[] p = t.getPoints();
            gr.setColor(new Color (r += 25, 0, 0, 0x80));
            gr.drawLine(p[0].getX(), p[0].getY(), p[1].getX(), p[1].getY());
            gr.drawLine(p[0].getX(), p[0].getY(), p[2].getX(), p[2].getY());
            gr.drawLine(p[1].getX(), p[1].getY(), p[2].getX(), p[2].getY());

            for (int i = 0; i < p.length; i++)
                if (p[i].getName() == 'b')
                    gr.fillRect(p[i].getX() - 10, p[i].getY() - 10, 20, 20);
        }
    }
}
