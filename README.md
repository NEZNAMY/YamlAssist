# YamlAssist
 
 A library that converts snakeyaml's error messages into much more user-friendly suggestions how to fix yaml syntax.
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
    <version>1.0.0</version>
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
		} catch (InvalidConfigurationException e) {
			if (e.getCause() instanceof YAMLException) {
				YAMLException yaml = (YAMLException) e.getCause();
				List<String> suggestions = YamlAssist.getSuggestions(f, yaml);
				//print suggestions into console
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 ```
