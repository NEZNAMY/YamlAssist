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
    <version>1.0.1</version>
  </dependency>
</dependencies>
 ```
 
 # Usage example
 
 ```
	public void onEnable() {
		getDataFolder().mkdirs();
		File f = new File(getDataFolder(), "config.yml");
		try {
			YamlConfiguration config = new YamlConfiguration();
			config.load(f);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof YAMLException) {
				YAMLException yaml = (YAMLException) e.getCause();
				List<String> suggestions = YamlAssist.getSuggestions(f, yaml);
				//print suggestions into console
			}
		}
	}
 ```
