import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class Students extends SimState {

    public Continuous2D yard = new Continuous2D(1.0, 100, 100);
    public int numStudents = 50;

    double forceToSchoolMultiplier = 0.01;
    double randomMultiplier = 0.1;

    public Network buddies = new Network(false);

    public Students(long seed) {
        super(seed);
    }

    public void start() {
        super.start();

        // Clear the yard
        yard.clear();

        // Clear the buddies :(
        buddies.clear();
      
        // Add some students to the yard
        for (int i = 0; i < numStudents; i++) {
            Student student = new Student();
            yard.setObjectLocation(student,
                new Double2D(yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
                    yard.getHeight() * 0.5 + random.nextDouble() - 0.5));
    
            buddies.addNode(student);
            schedule.scheduleRepeating(student);
        }

        // Define like/dislike relationships
        Bag students = buddies.getAllNodes();
        for (int i = 0; i < students.size(); i++) {
            Object student = students.get(i);

            // Who does he like?
            Object studentB = null;
            do {
                studentB = students.get(random.nextInt(students.numObjs));
            } while (student == studentB);

            double buddiness = random.nextDouble();
            buddies.addEdge(student, studentB, new Double(buddiness));

            // Who does he dislike?
            do {
                studentB = students.get(random.nextInt(students.numObjs));
            } while (student == studentB);

            buddiness = random.nextDouble();
            buddies.addEdge(student, studentB, new Double(-buddiness));
        }
    }

    public static void main(String[] args) {
        doLoop(Students.class, args);
        System.exit(0);
    }
}
