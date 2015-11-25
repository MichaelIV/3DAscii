package ascii3d;

import asciiPanel.AsciiCharacterData;
import asciiPanel.Drawable;
import asciiPanel.Line;
import asciiPanel.Render;
import asciiPanel.TileTransformer;
import java.awt.Color;
import java.awt.Point;

public class Controller {

    private static Controller controller = new Controller();
    private static Screen screen;
    private static Perspective pers;
    private int xInst;
    private final int xDiff;

    public Controller() {
        screen = Screen.getInstance();
        pers = Perspective.getInstance();
        //number of pixels per registered turn for mouse reg
        xDiff = 16;
    }

    public void render() {
        screen.render();
    }

    public void addAnimation(TileTransformer t) {
        screen.addAnimation(t);
    }

    public void addAnimation(TileTransformer[] t) {
        screen.addAnimation(t);
    }

    public void addRender(Render[] r) {
        screen.addRender(r);
    }

    public void addRender(Render r) {
        screen.addRender(r);
    }

    private void execute(Command c) {
        if (c != null) {
            c.exe(getInstance());
        }
    }

    public void clearRenders() {
        screen.clearRenders();
    }

    public void setMouseValue(int x) {
        xInst = x;
    }

    public void takeMouseMotion(int x) {
        if (x - xDiff > xInst) {
            //move right
            execute(pers.lookRight());
            xInst = x;
        } else if (x + xDiff < xInst) {
            //move left
            execute(pers.lookLeft());
            xInst = x;
        }
        //nothing happening!
    }

    int temporary = 0;
    public void takeMouseClick(int x, int y) {
        screen.clearRenders();
        screen.addRender(new Line(new Point(10, 14), new Point(50, temporary), new AsciiCharacterData(ImageLib.B_R_CORNER, Color.GREEN, Color.BLACK)).getRender());
        screen.addRender(new Line(new Point(71, 14), new Point(75, temporary), new AsciiCharacterData(ImageLib.DARK_SHADE, Color.CYAN, Color.BLACK)).getRender());
        temporary++;
        if (temporary == 40) {
            temporary = 0;
        }
    }

    public void takeInput(int keyCode) {
        switch (keyCode) {
            case 65://a
            case 37://left
                execute(pers.step(Step.LEFT));
                break;
            case 87://w
            case 38://up
                execute(pers.step(Step.FORWARD));
                break;
            case 68://d
            case 39://right
                execute(pers.step(Step.RIGHT));
                break;
            case 83://s
            case 40://down
                execute(pers.step(Step.BACK));
                break;
        }
    }

    public int getScreenWidth() {
        return screen.getAsciiPanelWidth();
    }

    public int getScreenHeight() {
        return screen.getAsciiPanelHeight();
    }

    public static Controller getInstance() {
        if (controller == null) {
            Controller controller = new Controller();
        }
        return controller;
    }
}
