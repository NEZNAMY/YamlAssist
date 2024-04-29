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
				if (fileLines.size() == lineNumber) continue;
				String nextLine = fileLines.get(lineNumber);
				if (getIndentCount(nextLine) - getIndentCount(fileLines.get(lineNumber-1)) == 2) {
					suggestions.add("Remove the \"" + line.substring(line.indexOf(':')+1) + "\" from the end of line " + lineNumber);
				} else {
					suggestions.add("Add a space after the \":\" at line " + lineNumber + ".");
				}
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