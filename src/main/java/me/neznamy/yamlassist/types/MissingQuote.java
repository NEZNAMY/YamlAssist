package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;
import me.neznamy.yamlassist.YamlAssist;

/**
 * A missing ' or " at the beginning or ending of a value
 */
public class MissingQuote extends SyntaxError {

	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		List<String> suggestions = new ArrayList<>();
		for (int lineNumber = 1; lineNumber <= fileLines.size(); lineNumber++) {
			String text = fileLines.get(lineNumber-1);
			if (!YamlAssist.getError(InvalidLine.class).isLineValid(text)) continue;
			if (removeIndent(text).startsWith("#")) continue;
			String suggestion = checkElement(getValue(text), lineNumber);
			if (suggestion != null) suggestions.add(suggestion);
		}
		return suggestions;
	}

	/**
	 * Calls checkElement(String, int, String) with both ' and " and returns error message if any, null otherwise
	 * @param value - map value to be checked
	 * @param lineNumber - line number to be used in error message
	 * @return error message or null if nothing was found
	 */
	private String checkElement(String value, int lineNumber) {
		String result;
		if ((result = checkElement(value, lineNumber, "'")) != null) {
			return result;
		}
		if ((result = checkElement(value, lineNumber, "\"")) != null) {
			return result;
		}
		return null;
	}
	
	/**
	 * Checks value for all possible problems
	 * @param value - mapping value
	 * @param lineNumber - line number to be used in error message
	 * @param c - quote character, ' or " to prevent duplicated code
	 * @return error message or null if nothing was found
	 */
	private String checkElement(String value, int lineNumber, String c) {
		if (value.equals(c)) {
			return "Add " + c + " at the end of line " + lineNumber + " to finish empty value";
		}
		if (value.startsWith(c) && !value.endsWith(c)) {
			if (getCharCount(value, c.charAt(0)) == 2) {
				int i;
				for (i = value.length()-2; i>0; i--) {
					if (value.charAt(i) == c.charAt(0)) break;
				}
				if (i>0) {
					return "Remove extra text \"" + value.substring(i+1) + "\" after ending " + c + " in line " + lineNumber;
				}
			}
			return "Add " + c + " at the end of line " + lineNumber;
		}
		if (value.endsWith(c) && !value.startsWith(c)) {
			return "Add " + c + " at the beginning of value at line " + lineNumber;
		}
		if (value.endsWith(c + c) && !value.equals(c + c)) {
			return "Remove one " + c + " from the end of line " + lineNumber;
		}
		return null;
	}
	
	private int getCharCount(String string, char c) {
		int count = 0;
		for (int i=0; i<string.length(); i++) {
			if (string.charAt(i) == c) count++;
		}
		return count;
	}
}