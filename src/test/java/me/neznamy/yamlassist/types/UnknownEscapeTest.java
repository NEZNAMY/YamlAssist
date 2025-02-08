package me.neznamy.yamlassist.types;

import me.neznamy.yamlassist.YamlAssist;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnknownEscapeTest {

    @Test
    void test() {
        List<String> expectedSuggestions = Collections.singletonList("Remove the \\ from line 2 or add another one after it to make the character display properly.");
        List<String> actualSuggestions = YamlAssist.getSuggestions(new File("src/test/resources/UnknownEscape.yml"));
        assertEquals(expectedSuggestions, actualSuggestions);
    }
}