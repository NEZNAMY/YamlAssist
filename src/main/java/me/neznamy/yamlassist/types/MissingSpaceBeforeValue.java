package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * Using "xx:yy" instead of "xx: yy" or "-something" instead of "- something"
 */
public class MissingSpaceBeforeValue extends SyntaxError {

	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<>();
		for (int lineNumber=1; lineNumber <= fileLines.size(); lineNumber++) {
			String line = removeIndent(fileLines.get(lineNumber-1));
			
			//skipping empty lines
			if (line.startsWith("#") || line.isEmpty()) continue;
			
			//member of list
			if (line.startsWith("-") && !line.startsWith("- ")) {
				suggestions.add("Add a space after the \"-\" at line " + lineNumber + ".");
				continue;
			}
			
			//simple values
			if (!line.startsWith("- ") && line.contains(":") && !line.contains(": ") && !line.endsWith(":")) {
				suggestions.add("Add a space after the \":\" at line " + lineNumber + ".");
				continue;
			}
			
			//completely invalid line
			if (!line.contains(":") && !line.contains("-")) {
				suggestions.add("Remove line " + lineNumber + " or add ':' at the end");
            }
		}
		return suggestions;
	}
}