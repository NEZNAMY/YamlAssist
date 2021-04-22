package me.neznamy.yamlassist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import me.neznamy.yamlassist.types.BadIndentation;
import me.neznamy.yamlassist.types.DoubleMapping;
import me.neznamy.yamlassist.types.InvalidLine;
import me.neznamy.yamlassist.types.InvalidList;
import me.neznamy.yamlassist.types.MissingQuote;
import me.neznamy.yamlassist.types.MissingSpaceBeforeValue;
import me.neznamy.yamlassist.types.QuoteWrapRequired;
import me.neznamy.yamlassist.types.TABIndent;
import me.neznamy.yamlassist.types.UnknownEscape;

/**
 * The core class of yamlassist
 */
public class YamlAssist {

	//registered error checkers
	private static Map<Class<? extends SyntaxError>, SyntaxError> registeredSyntaxErrors = new HashMap<Class<? extends SyntaxError>, SyntaxError>();

	/**
	 * Registers predefined checkers
	 */
	static {
		registerSyntaxError(new DoubleMapping());
		registerSyntaxError(new InvalidList());
		registerSyntaxError(new InvalidLine());
		registerSyntaxError(new MissingQuote());
		registerSyntaxError(new MissingSpaceBeforeValue());
		registerSyntaxError(new QuoteWrapRequired());
		registerSyntaxError(new TABIndent());
		registerSyntaxError(new UnknownEscape());
		registerSyntaxError(new BadIndentation());
	}

	/**
	 * Returns list of all syntax errors found based on file content
	 * @param file - the broken file
	 * @return List of fix suggestions
	 */
	public static List<String> getSuggestions(File file) {
		List<String> suggestions = new ArrayList<String>();
		try {
			if (file == null) throw new IllegalArgumentException("File cannot be null");
			if (!file.exists()) throw new IllegalStateException("File does not exist");
			FileInputStream input = new FileInputStream(file);
			try {
				new Yaml().load(new InputStreamReader(input, Charset.defaultCharset()));
			} catch (YAMLException exception) {
				for (SyntaxError possibleError : registeredSyntaxErrors.values()) {
					suggestions.addAll(possibleError.getSuggestions(exception, readAllLines(file)));
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return suggestions;
	}

	/**
	 * Allows anyone to register their own syntax error finder
	 * @param error - the error finder
	 */
	public static void registerSyntaxError(SyntaxError error) {
		registeredSyntaxErrors.put(error.getClass(), error);
	}

	/**
	 * Reads all lines in file and returns them as List
	 * @return list of lines in file
	 * @throws IOException - if IO operation fails
	 */
	private static List<String> readAllLines(File file) throws IOException {
		List<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()));
		String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		br.close();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getError(Class<T> clazz) {
		return (T) registeredSyntaxErrors.get(clazz);
	}
}