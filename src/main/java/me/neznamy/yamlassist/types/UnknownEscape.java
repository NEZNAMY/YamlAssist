package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * Using "\" without a proper followup character to escape.
 */
public class UnknownEscape extends SyntaxError {

	/** List of valid characters to be escaped */
	private final List<Character> validEscapedCharacters = Arrays.asList('\\', 'b', 'f', 'n', 'r', 't', 'u', '"');
	
	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<>();
		for (int i=1; i <= fileLines.size(); i++) {
			String line = fileLines.get(i-1);
			for (int j=0; j<line.length(); j++) {
				if (line.charAt(j) == '\\') {
					if ((j == line.length()-1 || !validEscapedCharacters.contains(line.charAt(j+1)))) {
						suggestions.add("Remove the \\ from line " + i + " or add another one after it to make the character display properly.");
					}
					j++;
				}
			}
		}
		return suggestions;
	}
}