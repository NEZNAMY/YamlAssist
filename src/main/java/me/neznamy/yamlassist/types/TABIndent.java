package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * Using the TAB key instead of 4 spaces to indent
 */
public class TABIndent extends SyntaxError {

	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<>();
		for (int i=1; i <= fileLines.size(); i++) {
			String line = fileLines.get(i-1);
			if (line.contains("\t")) {
				if (line.endsWith("\t")) {
					suggestions.add("Remove \\t (TAB) from the end of line " + i + ".");
				} else {
					suggestions.add("Replace \\t (TAB) with 4 spaces on line " + i + ".");
				}
			}
		}
		return suggestions;
	}
}