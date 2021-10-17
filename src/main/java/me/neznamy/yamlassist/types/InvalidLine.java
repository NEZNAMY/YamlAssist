package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * Line that does not start with "-" and is supposed to; lines not containing ":"
 */
public class InvalidLine extends SyntaxError {

	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<String>();
		for (int lineNumber = 1; lineNumber <= fileLines.size(); lineNumber++) {
			String line = removeIndent(fileLines.get(lineNumber-1));
			String previousLine = lineNumber == 1 ? "" : removeIndent(fileLines.get(lineNumber-2));
			if (previousLine.startsWith("-") && !line.startsWith("-") && (line.startsWith("'") || line.startsWith("\""))) {
				//member of list no longer starting with "-"
				String value = "- ";
				for (int i=0; i<getIndentCount(line); i++) {
					value = " " + value;
				}
				suggestions.add("Add \"" + value + "\" at the beginning of line " + lineNumber);
			}
		}
		return suggestions;
	}
	
	public boolean isLineValid(String line) {
		return !line.startsWith("'") && !line.startsWith("\"");
	}
}