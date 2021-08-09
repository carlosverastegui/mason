import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.Color;

public class StudentsWithUI extends GUIState {
    
    public Display2D display;
    public JFrame displayFrame;
    
    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
    NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();

    public static void main(String[] args) {
        StudentsWithUI vid = new StudentsWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
    }

    public void start() {
        super.start();
        setupPortrayals();
    }

    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }

    public void setupPortrayals() {
        Students students = (Students) state;

        // Tell the portrayals what to portray and how to portray them
        yardPortrayal.setField(students.yard);
        yardPortrayal.setPortrayalForAll(new OvalPortrayal2D());

        buddiesPortrayal.setField(new SpatialNetwork2D(students.yard, students.buddies));
        buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
        // Reschedule the displayer
        display.reset();
        display.setBackdrop(Color.white);

        // Redraw the display
        display.repaint();
    }

    public void init(Controller c) {
        super.init(c);
        display = new Display2D(600, 600, this);
        display.setClipping(false);

        displayFrame = display.createFrame();
        displayFrame.setTitle("Schoolyard Display");
        c.registerFrame(displayFrame);
        displayFrame.setVisible(true);
        display.attach(buddiesPortrayal, "Buddies");
        display.attach(yardPortrayal, "Yard");
    }

    public void quit() {
        super.quit();
        
        if (displayFrame != null) {
            displayFrame.dispose();
        }

        displayFrame = null;
        display = null;
    }

    public StudentsWithUI() {
        super(new Students(System.currentTimeMillis()));
    }

    public StudentsWithUI(SimState state) {
        super(state);
    }

    public static String getName() {
        return "Student Schoolyard Clique";
    }
}