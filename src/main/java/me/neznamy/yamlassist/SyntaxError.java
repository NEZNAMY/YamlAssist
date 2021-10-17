package me.neznamy.yamlassist;

import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

/**
 * An abstract class representing error finder
 */
public abstract class SyntaxError {
	
	/**
	 * Returns list of syntax errors found based on yaml exception and file content
	 * @param exception - the yaml exception
	 * @param fileLines - lines of file
	 * @return List of fix suggestions of this error type
	 */
	public abstract List<String> getSuggestions(YAMLException exception, List<String> fileLines);
	
	/**
	 * Removes tabs/spaces at the beginning and end of a line and returns it
	 * @param line - line to remove indent from
	 * @return line without indentation
	 */
	protected String removeIndent(String line) {
		String replaced = line;
		while (replaced.startsWith(" ") || replaced.startsWith("\t")) {
			replaced = replaced.substring(1);
		}
		while (replaced.endsWith(" ") || replaced.endsWith("\t")) {
			replaced = replaced.substring(0, replaced.length()-1);
		}
		return replaced;
	}
	
	/**
	 * Returns amount of leading spaces in line of text
	 * @param line - line to check
	 * @return amount of leading spaces
	 */
	protected int getIndentCount(String line) {
		if (line.split("#")[0].replace(" ", "").length() == 0) return 0;
		int index = 0;
		int spaces = 0;
		//not letting tab indent give invalid suggestions
		while (line.length() > index && (line.charAt(index) == ' ' || line.charAt(index) == '\t')) {
			if (line.charAt(index) == ' ') {
				index++;
				spaces++;
			} else {
				index++;
				spaces += 4;
			}
			
		}
		return spaces;
	}
	
	 /**
	 * Returns map value in the specified line of text
	 * @param line - line of configuration file
	 * @return map value of the line
	 */
	protected String getValue(String line) {
		String value = removeIndent(line);
		if (value.startsWith("- ")) {
			value = value.substring(2);
		} else if (value.contains(": ")) {
			value = value.substring(value.split(": ")[0].length()+2);
		}
		return removeEndLineComments(value);
	}
	
	/**
	 * Removes comments from the line if present
	 * @param line - line to remove comment from
	 * @return line without end line comment
	 */
	protected String removeEndLineComments(String line) {
		StringBuilder sb = new StringBuilder();
		boolean insideQuotes = false;
		char quoteChar = 0;
		for (int i=0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '"' || c == '\'') {
				if (i == 0) {
					insideQuotes = true;
					quoteChar = c;
				} else {
					if (quoteChar == c) insideQuotes = false;
				}
			}
			if (c == '#' && !insideQuotes && (quoteChar != 0 || (sb.length() > 0 && sb.charAt(sb.length()-1) == ' '))) {
				while (sb.length() > 0 && sb.charAt(sb.length()-1) == ' ') sb.setLength(sb.length()-1);
				return sb.toString();
			}
			sb.append(c);
		}
		while (sb.length() > 0 && sb.charAt(sb.length()-1) == ' ') sb.setLength(sb.length()-1);
		return sb.toString();
	}
}