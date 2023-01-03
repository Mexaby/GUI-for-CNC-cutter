package Model;

import java.awt.*;
import java.awt.geom.Path2D;

public class DemoShape {
    public DemoShape() {
    }

    public void drawDemoShape(Graphics2D shape){
        Path2D.Double path = new Path2D.Double();
        path.moveTo(0, 0);
        path.lineTo(0, 160);
        path.curveTo(0, 160, 0, 200, 40, 200);
        path.lineTo(200, 200);
        path.curveTo(240, 200, 200, 140, 240, 140);
        path.curveTo(260, 140, 260, 120, 260, 120);
        path.lineTo(260, 40);
        path.curveTo(260, 0, 220, 0, 220, 0);
        path.lineTo(0,0);
        shape.draw(path);
    }
}
