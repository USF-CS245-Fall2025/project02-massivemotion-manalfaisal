package massive;

import java.awt.Color;

/* Represents one celestial body (star or comet) in the simulation.
 * Each body has a position (x,y), velocity (vx,vy), a display size (diameter),
 * and a color used when drawing on the canvas.
 */
public class CelestialBody {
    public double x, y;   //current position in pixels
    public double vx, vy; //velocity in pixels per timer tick
    public int size;      //diameter in pixels
    public Color color;   //drawing color

    /** Construct a new celestial body with given parameters. */
    public CelestialBody(double x, double y,
                         double vx, double vy,
                         int size, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
        this.color = color;
    }

    //Advance the body one epoch by adding its velocity to position.
    public void move() {
        x += vx;
        y += vy;
    }
}
