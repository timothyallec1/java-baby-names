import org.junit.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2021. California Baptist University
 * Department of Computer Science and Data Sciences
 * TestBabyNames.java
 *
 * Last Modified Date: 5/27/21, 1:41 PM
 * Last Modified User: chasecrossley
 */

/*
4)      Build tests for HW3 which should cover below cases for each names.txt and names2.txt 2 hours (The actual grading you can do after 10/1)
-          Prompt message correctness test (1 test is enough) **********DONE**********
-          Case insensitivity test (have 2 test) **********DONE**********
-          Console output correctness test if found (have 2 tests) (the two output after taking in the user input) **********DONE**********
-          Console output correctness test if not found (have 2 tests) **********DONE**********
-          Graphics correctness test for all 5 pngs. (have 5 tests)
           (Since you are running gradle locally, you can just use PC folder assuming your laptop if PC. No need to test it on Mac.)
            **********DONE**********
         So total 24 tests. (12 x 2).
 */

public class TestBabyNames {
    private final static int MAX_PIXEL_DIFF_ALLOWED = 0;
    private static final InputStream SYSTEM_IN = System.in;
    private static final String MESSAGE_PREFIX = "This program allows you to search through the\n" +
            "data from the Social Security Administration\n" +
            "to see how popular a particular name has been\n" +
            "since ";
    private static final int SECOND = 1000;
    private static final String OPERATING_SYSTEM = getOperatingSystem();


    @BeforeClass
    public static void disableSystemExit() {
        forbidSystemExitCall();
    }

    @AfterClass
    public static void enableSystemExit() {
        enableSystemExitCall();
    }

    @AfterClass
    public static void runBeforeTestMethod() throws IOException {
        Files.deleteIfExists((new File("names-abbie-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names-don-m-actual.png")).toPath());
        Files.deleteIfExists((new File("names-ethel-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names-michelle-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names-stuart-m-actual.png")).toPath());
        Files.deleteIfExists((new File("names2-abbie-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names2-don-m-actual.png")).toPath());
        Files.deleteIfExists((new File("names2-ethel-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names2-michelle-f-actual.png")).toPath());
        Files.deleteIfExists((new File("names2-stuart-m-actual.png")).toPath());
    }

    private static String captureMainOutput() throws FileNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        BabyNames.main(null);

        System.out.flush();
        System.setOut(oldOut);
        return outputStream.toString();
    }

    private static void forbidSystemExitCall() {
        final SecurityManager securityManager = new SecurityManager() {
            public void checkPermission(Permission permission) {
                if (permission.getName().contains("exitVM")) {
                    throw new ExitTrappedException();
                }
            }
        };
        System.setSecurityManager(securityManager);
    }

    private static void enableSystemExitCall() {
        System.setSecurityManager(null);
    }

    private static String getOperatingSystem() {
        String fullName = System.getProperty("os.name");
        if (fullName.contains("Mac")) {
            return "Mac";
        } else if (fullName.contains("Windows")) {
            return "Windows";
        } else if (fullName.contains("Ubuntu")) {
            return "Ubuntu";
        }
        return fullName;
    }

    @Test(timeout = SECOND * 10)
    public void testPromptMessageNames() {
        TestUtils.setNames1Variables();
        testPromptMessage(TestUtils.STARTING_YEAR_1);
    }

    @Test(timeout = SECOND * 10)
    public void testPromptMessageNames2() {
        TestUtils.setNames2Variables();
        testPromptMessage(TestUtils.STARTING_YEAR_2);
    }

    @Test(timeout = SECOND * 10)
    public void testCaseInsensitivityNames() {
        TestUtils.setNames1Variables();
        String expectedOutput = "Chase    M  0 0  0  0  0 0 0 0  0  659  110  77 66";
        testConsoleOutput("ChAsE", "m", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CaseInsensitivityNames() {
        TestUtils.setNames1Variables();
        String expectedOutput = "Michelle   F  0  0  0  0  0 728  173 39 4  10 22  52 125";
        testConsoleOutput("michelle", "f", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testCaseInsensitivityNames2() {
        TestUtils.setNames2Variables();
        String expectedOutput = "Michelle   f 0 0 0  0  0 728 174  39";
        testConsoleOutput("michelle", "F", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CaseInsensitivityNames2() {
        TestUtils.setNames2Variables();
        String expectedOutput = "\"zOIdBErg\" not found.";
        testConsoleOutput("zOIdBErg", "M", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testCorrectConsoleFoundOutputNames() {
        TestUtils.setNames1Variables();
        String expectedOutput = String.format("Floyd M  60  48  55 61 61  103 142  198  282 384  638  0  0%s",
                System.lineSeparator()) +
                String.format("FLOYD m English Variant of LLOYD%s", System.lineSeparator());
        testConsoleOutput("Floyd", "M", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CorrectConsoleFoundOutputNames() {
        TestUtils.setNames1Variables();
        String expectedOutput = String.format("Nita   F  594 669 600  657  508  520 523  557 0  0 0 0  0%s",
                System.lineSeparator()) +
                String.format("NITA f English Short form of ending in nita; f Native American Means \"bear\"" +
                        " in Choctaw.%s", System.lineSeparator());
        testConsoleOutput("Nita", "F", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testCorrectConsoleFoundOutputNames2() {
        TestUtils.setNames2Variables();
        String expectedOutput = String.format("Darleen f  0  0 0  0 626 599 654 839%s", System.lineSeparator()) +
                String.format("DARLEEN f English Variant of DARLENE%s", System.lineSeparator());
        testConsoleOutput("Darleen", "f", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CorrectConsoleFoundOutputNames2() {
        TestUtils.setNames2Variables();
        String expectedOutput = String.format("Jessie   m 73  69  72  111 133  162 203  260%s", System.lineSeparator())
                + String.format("JESSIE M (no meaning found)%s", System.lineSeparator());
        testConsoleOutput("Jessie", "m", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testCorrectConsoleNotFoundOutputNames() {
        TestUtils.setNames1Variables();
        String name = "Dario";
        String expectedOutput = String.format("\"%s\" not found.", name);
        testConsoleOutput(name, "f", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CorrectConsoleNotFoundOutputNames() {
        TestUtils.setNames1Variables();
        String name = "abbie";
        String expectedOutput = String.format("\"%s\" not found.", name);
        testConsoleOutput(name, "m", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testCorrectConsoleNotFoundOutputNames2() {
        TestUtils.setNames2Variables();
        String name = "Abraham";
        String expectedOutput = String.format("\"%s\" not found.", name);
        testConsoleOutput(name, "f", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void test2CorrectConsoleNotFoundOutputNames2() {
        TestUtils.setNames2Variables();
        String name = "tod";
        String expectedOutput = String.format("\"%s\" not found.", name);
        testConsoleOutput(name, "f", expectedOutput);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalAbbieFNames() throws IOException {
        TestUtils.setNames1Variables();
        String name = "abbie";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_1.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalDonMNames() throws IOException {
        TestUtils.setNames1Variables();
        String name = "don";
        String gender = "m";
        String fileName = TestUtils.NAME_FILENAME_1.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalEthelFNames() throws IOException {
        TestUtils.setNames1Variables();
        String name = "ethel";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_1.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalMichelleFNames() throws IOException {
        TestUtils.setNames1Variables();
        String name = "michelle";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_1.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalStuartMNames() throws IOException {
        TestUtils.setNames1Variables();
        String name = "stuart";
        String gender = "m";
        String fileName = TestUtils.NAME_FILENAME_1.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalAbbieFNames2() throws IOException {
        TestUtils.setNames2Variables();
        String name = "abbie";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_2.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalDonMNames2() throws IOException {
        TestUtils.setNames2Variables();
        String name = "don";
        String gender = "m";
        String fileName = TestUtils.NAME_FILENAME_2.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalEthelFNames2() throws IOException {
        TestUtils.setNames2Variables();
        String name = "ethel";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_2.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalMichelleFNames2() throws IOException {
        TestUtils.setNames2Variables();
        String name = "michelle";
        String gender = "f";
        String fileName = TestUtils.NAME_FILENAME_2.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    @Test(timeout = SECOND * 10)
    public void testGraphicalStuartMNames2() throws IOException {
        TestUtils.setNames2Variables();
        String name = "stuart";
        String gender = "m";
        String fileName = TestUtils.NAME_FILENAME_2.split("\\.")[0];
        testPixelDifference(fileName, name, gender);
    }

    private void testPixelDifference(String fileName, String name, String gender) throws IOException {
        long pixelDiff = getBabyNamesPixelDifference(fileName, name, gender);
        String message = String.format("MAX_PIXEL_DIFF_ALLOWED: %s \n pixelDiff: %s \n (%2$s <= %1$s) == %s",
                MAX_PIXEL_DIFF_ALLOWED, pixelDiff, pixelDiff <= MAX_PIXEL_DIFF_ALLOWED);
        Assert.assertTrue(message, pixelDiff <= MAX_PIXEL_DIFF_ALLOWED);
    }

    private long getBabyNamesPixelDifference(String fileName, String name, String gender) throws IOException {
        String actualFilePath = String.format("%s-%s-%s-actual.png", fileName, name, gender);
        if (!new File(actualFilePath).exists()) {
            Assert.fail(actualFilePath + " was not made."); //output.png was not created
        }

        //Check to see if there is graphical output sample for the Operating System
//  The next few lines of code are only good for JDK 13 and later - updated code to work with earlier versions of the JDK (L.Clement)
//        Assume.assumeTrue("The user's os (%s) is not within the sample output.".formatted(OPERATING_SYSTEM),
//                getContentsOfDir("sample_output/graphical output for %s.txt/"
//                        .formatted(fileName)).contains("%s Users Only".formatted(OPERATING_SYSTEM)));
        Assume.assumeTrue("The user's os (" + OPERATING_SYSTEM + ") is not within the sample output.",
                getContentsOfDir("sample_output/graphical output for " + fileName + ".txt/"
                ).contains(OPERATING_SYSTEM + " Users Only"));


        String expectedFilePath = String.format("sample_output/graphical output for %3$s.txt/" +
                "%s Users Only/%s-%s-%s-%s.png", OPERATING_SYSTEM, OPERATING_SYSTEM.toLowerCase(), fileName, name, gender);

        //compare pixel differences
        return getPixelDifference(actualFilePath, expectedFilePath);
    }

    private void testConsoleOutput(String name, String gender, String expectedOutput) {
        try {
            passInputToSystemIn(name, gender);
            String mainOutput = captureMainOutput();
            String message = String.format("\"%s\" should contain \"%s\"", mainOutput, expectedOutput);
            Assert.assertTrue(message, mainOutput.contains(expectedOutput));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            System.setIn(SYSTEM_IN);
        }
    }

    private long getPixelDifference(String actualFilePath, String expectedFilePath) throws IOException {
        BufferedImage actualImage = ImageIO.read(new File(actualFilePath));
        BufferedImage expectedImage = ImageIO.read(new File(expectedFilePath));
        int actualImageWidth = actualImage.getWidth();
        int expectedImageWidth = expectedImage.getWidth();
        int actualImageHeight = actualImage.getHeight();
        int expectedImageHeight = expectedImage.getHeight();
        if ((actualImageWidth != expectedImageWidth) || (actualImageHeight != expectedImageHeight)) {
            String message = String.format("Dimensions do not match. %1$s" +
                            "Expected Height: %2$s, Actual Height: %3$s %1$s" +
                            "Expected Width: %4$s, Actual Width: %5$s",
                    System.lineSeparator(),
                    expectedImageHeight,
                    actualImageHeight,
                    expectedImageWidth,
                    actualImageWidth);
            Assert.fail(message); //dimensions must match
            return Long.MAX_VALUE;
        } else {
            long diff = 0;
            for (int j = 0; j < actualImageHeight; j++) {
                for (int i = 0; i < actualImageWidth; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = actualImage.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = expectedImage.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    if (r1 - r2 != 0 || g1 - g2 != 0 || b1 - b2 != 0) {
                        diff += 1;
                    }
                }
            }
            return diff;
        }
    }

    private void testPromptMessage(int startingYear) {
        try {
            String correctPrompt = MESSAGE_PREFIX + startingYear;
            passInputToSystemIn("TEST", "TEST");

            String mainOutput = captureMainOutput();
            String message = String.format("\"%s\" should contain \"%s\"", mainOutput, correctPrompt);
            Assert.assertTrue(message, mainOutput.contains(correctPrompt));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            System.setIn(SYSTEM_IN);
        }
    }

    private List<String> getContentsOfDir(String stringPath) {
        List<String> contents = new ArrayList<>();
        try {
            Files.list(new File(stringPath).toPath())
                    .forEach((path -> {
                        contents.add(path.getFileName().toString());
                    }));
        } catch (IOException e) {
            System.out.println("An Error occurred:");
            e.printStackTrace();
        }
        return contents;
    }

    private void passInputToSystemIn(String name, String gender) {
        String data = String.format("%2$s%1$s%3$s%1$s", System.lineSeparator(), name, gender);
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    public static class ExitTrappedException extends SecurityException {

    }

}