package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;
import me.neznamy.yamlassist.YamlAssist;

/**
 * String starting with "%", "&" or other character that is not allowed without quotes
 */
public class QuoteWrapRequired extends SyntaxError {

	/** List of characters that cannot start a value (quotes are required) */
	private final char[] invalidStartCharacters = {'\0', '%', '-', '.', '[', '{', ']', '}', ',', '?', ':', '*', '&', '!', '|', '>'};

	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<>();
		for (int lineNumber = 1; lineNumber <= fileLines.size(); lineNumber++) {
			String line = removeIndent(fileLines.get(lineNumber-1));
			if (!YamlAssist.getError(InvalidLine.class).isLineValid(line)) continue;
			//skipping empty lines
			if (line.startsWith("#") || line.isEmpty()) continue;

			String value = getValue(line);
			if (value.isEmpty() || value.equals("{}") || value.equals("[]")) continue;
			for (char invalid : invalidStartCharacters) {
				if (value.charAt(0) == invalid) {
					suggestions.add("Wrap value in line " + lineNumber + " into quotes.");
				}
			}
		}
		return suggestions;
	}
}