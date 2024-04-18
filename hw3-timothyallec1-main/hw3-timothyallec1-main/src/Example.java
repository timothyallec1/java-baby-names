import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Example {
    //class constants for open area in graphics (Do NOT change)
    private static final Integer OPEN_AREA_WIDTH = 780;
    private static final Integer OPEN_AREA_HEIGHT = 500;

    //prompt msg class constants (Do NOT change)
    private static final String MESSAGE_PREFIX = "This program allows you to search through the\n" +
            "data from the Social Security Administration\n" +
            "to see how popular a particular name has been\n" +
            "since ";

    // class constant for meaning file (Do NOT change)
    private static final String MEANING_FILENAME = "meanings.txt";

    // class constant for name file (Change the value only. Do NOT change the names of the constant)
    // Test with both "names.txt" and "names2.txt" (Before submission, change back to "names.txt")
    private static String nameFilename = "names.txt";

    // Other class constants (Change the value only. Do NOT change the names of the constants)
    private static Integer startingYear = 1890; // change the value according to spec
    private static Integer decadeWidth = 60; // change the value according to spec
    private static Integer noOfDecades = 30; // change the value according to spec //changing this to make more sense for debugging, will change the variable name back before final submission

    // YOU ARE NOT ALLOWED TO ADD ANY OTHER CONSTANTS THAN ABOVE

    public static void main(String[] args) throws FileNotFoundException {
// scanner to go through names file
        Scanner namesDB = new Scanner(new File(nameFilename));
        // prints introduction to the application
        System.out.println(MESSAGE_PREFIX);
        // variable to store correct line from namesDB
        String data = searchDB(namesDB);
        // if loop to catch combinations with no entry in the database
        if (data == "") {
            System.out.println("name/gender combination not found");
        } else {
            DrawingPanel p = new DrawingPanel(noOfDecades * decadeWidth, 550);
            Graphics g = p.getGraphics();
            drawPlot(g);
            plotGraph(data, g);
        }
    }
    // method to prompt the user to search for a name, and return the line matching
    // the requested name in the names database
    public static String searchDB(Scanner namesDB) {
        // declare scanner to read user input
        Scanner console = new Scanner(System.in);

        // prompt the user to enter the name to be searched for and the gender
        System.out.print("name? ");
        String name = console.next();
        System.out.print("gender (M or F)? ");
        String gender = console.next();

        // concatenate name and gender strings to form a search string
        String searchString = name.toUpperCase() + " " + gender.toUpperCase();

        // loop to search through file
        while(namesDB.hasNextLine()) {
            String nameAge = namesDB.next() + " " + namesDB.next();
            String line = namesDB.nextLine();
            if (nameAge.toUpperCase().equals(searchString)) {
                return nameAge + line;
            }
        }
        return "";
    }

    // method to draw the initial plot for the points (without data)
    public static void drawPlot(Graphics g) {
        g.drawLine(0, 25, noOfDecades * decadeWidth, 25);
        g.drawLine(0, 525, noOfDecades * decadeWidth, 525);

        // loop to draw individual lines separating decades
        for (int i = 0; i < noOfDecades; i++) {
            g.drawLine((i + 1) * decadeWidth, 0, (i + 1) * decadeWidth, 550);
            g.drawString(String.valueOf(startingYear + (10 * i)), decadeWidth * i, 550);
        }
    }



    // method to plot the data points from the line returned by the searchDB method
    public static void plotGraph(String line, Graphics g) {
        // set graphics color to red
        g.setColor(Color.RED);

        // scanner to go through the line of data
        Scanner lineP = new Scanner(line);

        // variables to act as coordinates for the plot and the text to be plotted next to it
        int xCor = 0;
        int yCor = 0;
        int rank = 0;
        int yPrev = 0;
        String description;

        // adding the name and the gender to the description variable
        description = lineP.next();
        description = description + " " + lineP.next().toUpperCase();

        // loop to go through the entire line
        while (lineP.hasNextInt()) {
            rank = lineP.nextInt();
            yPrev = yCor;
            // if statement to correct coordinates for rank 0 (i.e.
            // not on the list for a particular year)
            if (rank > 0) {
                yCor = 25 + (rank / 2);
            } else {
                yCor = 525;
            }
            g.drawString(description + " " + String.valueOf(rank), xCor, yCor);
            g.drawLine(xCor - decadeWidth, yPrev, xCor, yCor);
            xCor += decadeWidth;
        }
    }
}
