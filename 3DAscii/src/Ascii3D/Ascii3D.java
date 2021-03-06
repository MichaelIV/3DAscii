package ascii3d;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ascii3D extends Canvas implements Runnable {

    private boolean running;
    private static final int HEIGHT = 22, WIDTH = 35, SCALE = 32;
    private static final String NAME = "Ghostme";

    public int tickCount = 0;

    private JFrame frame;
    private Screen screen;
    private Listener listener;
    private Mouse mouse;
    private final Dimension DIMENSION = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);

    public Ascii3D() {

        frame = new JFrame(NAME);
        screen = Screen.getInstance();
        listener = new Listener();
        mouse = new Mouse();

        //sets personal bug
        frame.setIconImage(new ImageIcon("res/bug.png").getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(screen, null);
        frame.addKeyListener(listener);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.pack();
        frame.setPreferredSize(DIMENSION);
        frame.setResizable(true);
        //A nice visible top left corner point (5,5)
        frame.setLocation(0, 0);
        frame.setVisible(true);
        frame.setSize(DIMENSION);

    }

    public void init() {
        //TEMP
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public static void main(String[] args) {
        new Ascii3D().start();
    }

    public void run() {

        init();

        long lastTime = System.nanoTime();
        final double nsPerTick = 1000000000 / (double) 60;

        int ticks = 0;
        int frames = 0;
        int c = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            screen = Screen.getInstance();

            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                this.render();
                frames = 0;
                ticks = 0;
                screen.render();
            }

        }

    }

    public void tick() {
        tickCount++;
        screen.render();
    }

    public void render() {
        //1 sec
    }

}
