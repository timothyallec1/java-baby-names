import java.awt.*;
import java.util.*;
import java.io.*;
/* finalized on 10/13/2022 by timothy allec.
collaborated with vincent, phineas, and kai .
this program will read user input and compare it to data found in 2 separate files and output the data for that name*/

public class BabyNames {

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
    private static Integer legendHeight = 30; // change the value according to spec


//     YOU ARE NOT ALLOWED TO ADD ANY OTHER CONSTANTS THAN ABOVE
public static String name;
    public static String gender;

    // main will collect input from the user, output it to console, and will print the data on a drawing panel
    public static void main(String[] args) throws FileNotFoundException {
        getInput();
        String files = outputToConsole();
        OutputGraphics(files);
    }
    // this method receives user input and saves it as strings: "name" and "gender
    public static void getInput() {
        System.out.println(MESSAGE_PREFIX + startingYear);
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        name = input.next();
        System.out.print("Gender (M or F): ");
        gender = input.next();
    }
    // this method outputs the data collected from reading the files and prints it to the console
    public static String outputToConsole() throws FileNotFoundException {
        String meaningFile = checkLine(MEANING_FILENAME);
        String popularityFile = checkLine(nameFilename);

        String files = null;
        if (meaningFile != null) {
            files = meaningFile + "\n" + popularityFile;
            System.out.println(popularityFile);
            System.out.println(meaningFile);
        } else {
            System.out.println("\n" + name + "not found");
        }
        return files;
    }
    //this method checks and collects data from each file for the specified name and gender from the user input
    public static String checkLine(String file) throws FileNotFoundException {
        Scanner input = new Scanner(new File(file));
        while (input.hasNextLine() ) {
            String currentLine = input.nextLine();
            Scanner lineScan = new Scanner(currentLine);
            String fileName = lineScan.next();
            String fileGender = lineScan.next();

            if (fileName.equalsIgnoreCase(name) && fileGender.equalsIgnoreCase(gender)) {
                return currentLine;
            }
        }
        return "\"" + name + "\"" + " " + "not found.";
    }
    private static void OutputGraphics(String files) {
        DrawingPanel p = new DrawingPanel(OPEN_AREA_WIDTH, OPEN_AREA_HEIGHT+60);
        Graphics g = p.getGraphics();
        Scanner outputSplit = new Scanner(files);
        String line1 = outputSplit.nextLine();  //meaning file
        String line2 = outputSplit.nextLine(); //popularity file
        StaticGraphic(g, line1, line2);
        DynamicGraphic(g, line2);
    }
    // this draws the grey boxes at the top/bottom, and the decades in proper increment form
    public static void StaticGraphic(Graphics g, String line1, String line2) {  //OPEN_AREA_WIDTH = 780 OPEN_AREA_=500
        Scanner scan1 = new Scanner(line1);
        Scanner scan2 = new Scanner(line2);
        //draw boxes and black lines
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, OPEN_AREA_WIDTH, legendHeight); //top rectangle
        g.fillRect(0, OPEN_AREA_HEIGHT + legendHeight, OPEN_AREA_WIDTH, legendHeight);//bottom rectangle
        g.setColor(Color.black);
        g.drawLine(0, legendHeight, OPEN_AREA_WIDTH, legendHeight);
        g.drawLine(0, OPEN_AREA_HEIGHT + legendHeight, OPEN_AREA_WIDTH, OPEN_AREA_HEIGHT + legendHeight);
        // draw meaning on panel
        line1 = scan1.nextLine();

        g.drawString(line1.trim(), 0, 16);
        //draw decades
        Scanner scan = new Scanner(line2);
        int i = 0;
        scan.next();
        scan.next();
        while (scan.hasNext()) {

            g.setColor(Color.black);
            g.drawString("" + (startingYear + (i * 10)), i * decadeWidth, 552);
            i++;
            scan.next();
        }
    }
    // draws the green bars on the panel
    public static void DynamicGraphic(Graphics g, String line2) {
        Scanner scan = new Scanner(line2);
        //this skips the name and gender found on the file
        scan.next();
        scan.next();
        int i = 0;
        // scans for data from the file
        while (scan.hasNextInt()) {
            // implement number on top of bar within the while loop
            int num = scan.nextInt();
            // if data = 0; it will set num to 1000, this printing "0" at a y-coordinate of 530
            if (num == 0) {
                num = OPEN_AREA_HEIGHT * 2;
            }
            // prints the green bars for data that is not equal to 0
            g.setColor(Color.green);
            g.fillRect(decadeWidth * i, num / 2 + legendHeight, decadeWidth / 2, OPEN_AREA_HEIGHT - num / 2);
            // prints the data collected from the file on top of each bar
            g.setColor(Color.black);
            if ( num!= 1000) {
                g.drawString(""+num, decadeWidth*i, num/2 + legendHeight);
            } else{
                num = 0;
                g.setColor(Color.black);
                g.drawString(""+0, decadeWidth*i, OPEN_AREA_HEIGHT + legendHeight);
            }
            i++;
        }
    }
}





