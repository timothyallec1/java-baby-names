/*
 * Copyright (c) 2021. California Baptist University
 * Department of Computer Science and Data Sciences
 * TestUtils.java
 *
 * Last Modified Date: 5/27/21, 1:29 PM
 * Last Modified User: chasecrossley
 */

import org.junit.Assert;

import java.lang.reflect.Field;

public class TestUtils {


    // class constant for name file (Change the value only. Do NOT change the names of the constant)
    // Test with both "names.txt" and "names2.txt" (Before submission, change back to "names.txt")
    static final String NAME_FILENAME_1 = "names.txt";
    static final Integer STARTING_YEAR_1 = 1890;
    static final Integer DECADE_WIDTH_1 = 60;
    static final Integer LEGEND_HEIGHT_1 = 30;

    static final String NAME_FILENAME_2 = "names2.txt";
    static final Integer STARTING_YEAR_2 = 1863;
    static final Integer DECADE_WIDTH_2 = 50;
    static final Integer LEGEND_HEIGHT_2= 20;



    public static void setStaticField(Class<?> clazz, String fieldName, Object value) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Assert.fail(String.format("There is not a field named: %s", fieldName));
        }
        field.setAccessible(true);

        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            Assert.fail(String.format("Could not set value of field: %s %s Make sure the field does not have " +
                    "the FINAL modifier", fieldName, System.lineSeparator()));
        }
    }

    public static void setNames1Variables(){
        setStaticField(BabyNames.class,"nameFilename", NAME_FILENAME_1);
        setStaticField(BabyNames.class,"startingYear", STARTING_YEAR_1);
        setStaticField(BabyNames.class,"decadeWidth", DECADE_WIDTH_1);
        setStaticField(BabyNames.class,"legendHeight", LEGEND_HEIGHT_1);
    }

    public static void setNames2Variables(){
        setStaticField(BabyNames.class,"nameFilename", NAME_FILENAME_2);
        setStaticField(BabyNames.class,"startingYear", STARTING_YEAR_2);
        setStaticField(BabyNames.class,"decadeWidth", DECADE_WIDTH_2);
        setStaticField(BabyNames.class,"legendHeight", LEGEND_HEIGHT_2);
    }
}
