# YamlAssist
 
 A library that detects syntax errors in a yaml file based on snakeyaml's error message and file's content and transforms them into user-friendly messages.
# Maven Repository
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <!-- YamlAssist -->
  <dependency>
    <groupId>com.github.NEZNAMY</groupId>
    <artifactId>YamlAssist</artifactId>
    <version>1.0.2</version>
  </dependency>
</dependencies>
 ```
 
 # Usage example
 
 ```
	public void onEnable() {
		getDataFolder().mkdirs();
		File file = new File(getDataFolder(), "config.yml");
		try {
			YamlConfiguration config = new YamlConfiguration();
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof YAMLException) {
				List<String> suggestions = YamlAssist.getSuggestions(file);
				//print suggestions into console
			}
		}
	}
 ```
