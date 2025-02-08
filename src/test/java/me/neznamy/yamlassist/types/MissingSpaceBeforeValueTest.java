package me.neznamy.yamlassist.types;

import me.neznamy.yamlassist.YamlAssist;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissingSpaceBeforeValueTest {

    @Test
    void test() {
        List<String> expectedSuggestions = Arrays.asList(
                "Add a space after the \"-\" at line 3.",
                "Remove line 5 or add ':' at the end",
                "Add a space after the \":\" at line 7."
        );
        List<String> actualSuggestions = YamlAssist.getSuggestions(new File("src/test/resources/MissingSpaceBeforeValue.yml"));
        assertEquals(expectedSuggestions, actualSuggestions);
    }
}