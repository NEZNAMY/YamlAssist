package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * String starting with "%", "&" or other character that is not allowed without quotes
 */
public class QuoteWrapRequired extends SyntaxError {

	//list of characters that cannot start a value (quotes are required)
	private char[] invalidStartCharacters = {'\0', '%', '-', '.', '[', '{', ']', '}', ',', '?', ':', '*', '&', '!', '|', '>'};
			
	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<String>();
		for (int lineNumber = 1; lineNumber <= fileLines.size(); lineNumber++) {
			String line = removeIndent(fileLines.get(lineNumber-1));
			
			//skipping empty lines
			if (line.startsWith("#") || line.isEmpty()) continue;
			
			String value = getValue(line);
			for (char invalid : invalidStartCharacters) {
				if (value.startsWith(invalid+"")) {
					suggestions.add("Wrap value in line " + lineNumber + " into quotes.");
				}
			}
		}
		return suggestions;
	}
	
	/**
	 * Returns map value in the specified line of text
	 * @param line - line of configuration file
	 * @return map value of the line
	 */
	private String getValue(String line) {
		if (line.startsWith("- ")) {
			return line.substring(line.split("- ")[0].length()+2);
		} else if (line.contains(": ")) {
			return line.substring(line.split(": ")[0].length()+2);
		}
		//should not happen
		return line;
	}
}