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
}