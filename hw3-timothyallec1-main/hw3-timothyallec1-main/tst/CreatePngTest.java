/*
 * Copyright (c) 2021. California Baptist University
 * Department of Computer Science and Data Sciences
 * CreatePngTest.java
 *
 * Last Modified Date: 5/25/21, 1:22 PM
 * Last Modified User: chasecrossley
 */

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;


/**
 * With the way that DrawingPanel.java is setup, this class is necessary to generate the various different
 * MORTAR size png files if a student removes "panel.save()" from the main method in CafeWall.java
 **/
public class CreatePngTest {
    private final int SECOND = 1;

    @Test
    public void generateAbbieFNames() {
        TestUtils.setNames1Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_1.split("\\.")[0], "abbie", "f"));
    }

    @Test
    public void generateDonMNames() {
        TestUtils.setNames1Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_1.split("\\.")[0], "don", "m"));
    }

    @Test
    public void generateEthelFNames() {
        TestUtils.setNames1Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_1.split("\\.")[0], "ethel", "f"));
    }

    @Test
    public void generateMichelleFNames() {
        TestUtils.setNames1Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_1.split("\\.")[0], "michelle", "f"));
    }

    @Test
    public void generateStuartMNames() {
        TestUtils.setNames1Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_1.split("\\.")[0], "stuart", "m"));
    }

    @Test
    public void generateAbbieFNames2() {
        TestUtils.setNames2Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_2.split("\\.")[0], "abbie", "f"));
    }

    @Test
    public void generateDonMNames2() {
        TestUtils.setNames2Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_2.split("\\.")[0], "don", "m"));
    }

    @Test
    public void generateEthelFNames2() {
        TestUtils.setNames2Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_2.split("\\.")[0], "ethel", "f"));
    }

    @Test
    public void generateMichelleFNames2() {
        TestUtils.setNames2Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_2.split("\\.")[0], "michelle", "f"));
    }

    @Test
    public void generateStuartMNames2() {
        TestUtils.setNames2Variables();
        assertTimeoutPreemptively(ofSeconds(SECOND * 10), () -> generateOutput(TestUtils.NAME_FILENAME_2.split("\\.")[0], "stuart", "m"));
    }

    private void generateOutput(String fileNameUsed, String name, String gender) throws Exception {
        String filePath = String.format("%s-%s-%s-actual.png", fileNameUsed, name, gender);
        System.setProperty(DrawingPanel.SAVE_PROPERTY, filePath);
        passInputToSystemIn(name, gender);
        BabyNames.main(null); //should create an output.png with the correct size
    }


    private void passInputToSystemIn(String name, String gender) {
        String data = String.format("%2$s%1$s%3$s%1$s", System.lineSeparator(), name, gender);
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

}
