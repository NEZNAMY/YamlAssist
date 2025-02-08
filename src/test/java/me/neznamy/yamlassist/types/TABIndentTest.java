package me.neznamy.yamlassist.types;

import me.neznamy.yamlassist.YamlAssist;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TABIndentTest {

    @Test
    void test() {
        List<String> expectedSuggestions = Collections.singletonList("Replace \\t (TAB) with 4 spaces on line 3.");
        List<String> actualSuggestions = YamlAssist.getSuggestions(new File("src/test/resources/TABIndent.yml"));
        assertEquals(expectedSuggestions, actualSuggestions);
    }
}