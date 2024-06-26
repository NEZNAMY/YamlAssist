package me.neznamy.yamlassist.types;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.SyntaxError;

/**
 * Incorrect amount of leading spaces
 */
public class BadIndentation extends SyntaxError {
	
	@Override
	public List<String> getSuggestions(YAMLException exception, List<String> fileLines) {
		return checkForIndent(fileLines);
	}

	/**
	 * The core method, really messy right now, will be reworked
	 * @param lines - lines of file
	 * @return - list of fix suggestions
	 */
	private List<String> checkForIndent(List<String> lines) {
		List<String> suggestions = new ArrayList<>();
		for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
			String line = lines.get(lineNumber);
			if (isComment(line)) continue;
			int currentLineIndent = getIndentCount(line);
			String prevLine = lineNumber == 0 ? "" : lines.get(lineNumber-1);
			int remove = 1;
			while (prevLine.isEmpty() || isComment(prevLine)) {
				int id = lineNumber-(remove++);
				if (id == -1) {
					prevLine = "";
					break;
				}
				prevLine = lines.get(id);
			}
			prevLine = removeEndLineComments(prevLine);
			int prevLineIndent = getIndentCount(prevLine);
			if (removeSpaces(prevLine).endsWith(":")) {
				//expecting 2 more spaces or same or 2k less (k = 1,2,..)
				if (currentLineIndent - prevLineIndent > 2) {
					suggestions.add("Remove " + (currentLineIndent-prevLineIndent-2) + " space(s) from line " + (lineNumber+1));
					lineNumber++;
					continue;
				}
				if (currentLineIndent - prevLineIndent == 1) {
					suggestions.add("Add 1 space to line " + (lineNumber+1));
					lineNumber++;
					continue;
				}
				if (prevLineIndent - currentLineIndent == 1) {
					if (removeSpaces(line).startsWith("-")) {
						suggestions.add("Add 1 or 3 spaces to line " +  (lineNumber+1));
                    } else {
						suggestions.add("Remove 1 space from line " + (lineNumber+1));
                    }
                    lineNumber++;
                    continue;
                }
			} else {
				//expecting same indent count or 2k less (k = 1,2,..)
				if (currentLineIndent > prevLineIndent) {
					suggestions.add("Remove " + (currentLineIndent-prevLineIndent) + " space(s) from line " + (lineNumber+1));
					lineNumber++;
					continue;
					//member of list with fewer spaces
				} else if (currentLineIndent != prevLineIndent && removeIndent(prevLine).startsWith("-") && removeIndent(line).startsWith("-")) {
					suggestions.add("Add " + (prevLineIndent-currentLineIndent) + " space(s) to line " + (lineNumber+1));
					lineNumber++;
					continue;
				}
			}
			if (currentLineIndent%2 == 1) {
				suggestions.add("Add or remove one space at line " + (lineNumber+1));
				lineNumber++;
            }
		}
		return suggestions;
	}
	
	private String removeSpaces(String line) {
		String fixed = line;
		while (fixed.startsWith(" ") || fixed.startsWith("\t")) fixed = fixed.substring(1);
		while (fixed.endsWith(" ") || fixed.endsWith("\t")) fixed = fixed.substring(0, fixed.length()-1);
		return fixed;
	}
	
	/**
	 * Return true if this line appears to be a comment only, false if not
	 * @param line - line of file
	 * @return true if line is only a comment, false otherwise
	 */
	private boolean isComment(String line) {
		String[] array = line.split("#");
		if (array.length == 0) return true;
		return array[0].replace(" ", "").isEmpty();
	}
}