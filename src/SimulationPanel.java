package massive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class SimulationPanel extends JPanel implements ActionListener {
    private final Timer timer;
    private final List<CelestialBody> bodies;
    private final int width, height;
    //Probability per tick to spawn from top/bottom and left/right
    private final double genX, genY;
    //Radius (px) of comets
    private final int bodySize;
    //Random velocity component in [-R..R] \ {0}
    private final int bodyVelRange;

    public SimulationPanel(Properties p, String listKind, int width, int height, int delay) {
        this.width = width;
        this.height = height;

        if ("arraylist".equalsIgnoreCase(listKind)) {
            bodies = new massive.ArrayList<>();
        } else if ("single".equalsIgnoreCase(listKind)) {
            bodies = new massive.LinkedList<>();
        } else if ("double".equalsIgnoreCase(listKind)) {
            bodies = new massive.DoublyLinkedList<>();
        } else if ("dummyhead".equalsIgnoreCase(listKind)) {
            bodies = new massive.DummyHeadLinkedList<>();
        } else {
            bodies = new massive.ArrayList<>();
        }

        //Read star params
        int sx = parseInt(p, "star_position_x", 512);
        int sy = parseInt(p, "star_position_y", 384);
        int sSize = parseInt(p, "star_size", 30);
        double svx = parseDouble(p, "star_velocity_x", 0.0);
        double svy = parseDouble(p, "star_velocity_y", 0.0);

        //Spawn params
        genX = clamp01(parseDouble(p, "gen_x", 0.06));
        genY = clamp01(parseDouble(p, "gen_y", 0.06));
        bodySize = parseInt(p, "body_size", 10);
        bodyVelRange = Math.max(1, parseInt(p, "body_velocity", 3));

        //Add the red star to the same list as comets
        bodies.add(new CelestialBody(sx, sy, svx, svy, sSize, Color.RED));

        //Start timer
        timer = new Timer(delay, this);
        timer.start();

        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1) Advance positions & remove off-screen bodies
        int i = 0;
        while (i < bodies.size()) {
            CelestialBody b = bodies.get(i);
            b.move();

            if (isOffScreen(b)) {
                bodies.remove(i);
            } else {
                i = i + 1;
            }
        }

        //2) Probabilistically spawn new comets along edges
        if (Math.random() < genX) {
            spawnFromTopOrBottom();
        }
        if (Math.random() < genY) {
            spawnFromLeftOrRight();
        }

        //3) Redraw
        repaint();
    }

    private void spawnFromTopOrBottom() {
        boolean fromTop = Math.random() < 0.5;          //true = top, false = bottom
        double x = Math.random() * width;               //random horizontal position
        double y;
        double vx = nonZeroRand(bodyVelRange);          //random horizontal velocity
        double vyCandidate = nonZeroRand(bodyVelRange); //random vertical speed
        double vy;

        if (fromTop) {
            //Spawn just above the top edge, moving downward
            y = -bodySize;
            vy = Math.abs(vyCandidate);
        } else {
            //Spawn just below the bottom edge, moving upward
            y = height;
            vy = -Math.abs(vyCandidate);
        }

        CelestialBody comet = new CelestialBody(x, y, vx, vy, bodySize, Color.BLACK);
        bodies.add(comet);
    }

    private void spawnFromLeftOrRight() {
        boolean fromLeft = Math.random() < 0.5;          //true = left, false = right
        double y = Math.random() * height;               //random vertical position
        double x;
        double vy = nonZeroRand(bodyVelRange);           //random vertical velocity
        double vxCandidate = nonZeroRand(bodyVelRange);  //random horizontal speed
        double vx;

        if (fromLeft) {
            //Spawn just outside the left edge, moving right
            x = -bodySize;
            vx = Math.abs(vxCandidate);
        } else {
            //Spawn just outside the right edge, moving left
            x = width;
            vx = -Math.abs(vxCandidate);
        }

        CelestialBody comet = new CelestialBody(x, y, vx, vy, bodySize, Color.BLACK);
        bodies.add(comet);
    }

    //Random integer in [-R..R] excluding 0 (returned as double)
    private static double nonZeroRand(int R) {
        int v = 0;
        while (v == 0) {
            v = (int) Math.round((Math.random() * (2 * R)) - R);
        }
        return (double) v;
    }

    private boolean isOffScreen(CelestialBody b) {
        //Treat size as diameter -> off once entire circle left the view
        return (b.x + b.size < 0) || (b.y + b.size < 0) ||
               (b.x > width) || (b.y > height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = 0;
        while (i < bodies.size()) {
            CelestialBody b = bodies.get(i);
            g.setColor(b.color);
            g.fillOval((int) b.x, (int) b.y, b.size, b.size);
            i = i + 1;
        }
    }

    //Parsing helpers with defaults
    private static int parseInt(Properties p, String key, int defVal) {
        try {
            String s = p.getProperty(key);
            if (s != null) return Integer.parseInt(s.trim());
        } catch (Exception ignored) { }
        return defVal;
    }

    private static double parseDouble(Properties p, String key, double defVal) {
        try {
            String s = p.getProperty(key);
            if (s != null) return Double.parseDouble(s.trim());
        } catch (Exception ignored) { }
        return defVal;
    }

    private static double clamp01(double x) {
        if (x < 0.0) return 0.0;
        if (x > 1.0) return 1.0;
        return x;
    }
}
