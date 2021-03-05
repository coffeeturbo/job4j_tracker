package ru.job4j.tracker.input;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidateInputTest {

    @Test
    public void whenInvalidInput() {
        ByteArrayOutputStream mem = new ByteArrayOutputStream();
        PrintStream out = System.out;
        System.setOut(new PrintStream(mem));
        String[] data = {"one", "1"};

        Input input = new StubInput(data);
        ValidateInput validate = new ValidateInput(input);
        validate.askInt("Enter");
        assertThat(
                mem.toString(),
                is(String.format("Please enter validate data again.%n"))
        );
        System.setOut(out);
    }

    @Test
    public void whenKeyMenuBiggerThenMax() {
        ByteArrayOutputStream mem = new ByteArrayOutputStream();
        PrintStream out = System.out;
        System.setOut(new PrintStream(mem));
        String[] data = {"22", "1"};

        Input input = new StubInput(data);
        ValidateInput validate = new ValidateInput(input);
        validate.askInt("Enter", 10);
        assertThat(
            mem.toString(),
            is(String.format("Please select key from menu.%s", System.lineSeparator()))
        );
        System.setOut(out);
    }
}