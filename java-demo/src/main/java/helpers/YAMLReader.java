package helpers;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class YAMLReader {
    public String getURL() throws Exception  {
        Yaml yaml = new Yaml();
        String s = File.separator;
        String configFilePath = "." + s + "src" + s + "main" + s + "java" + s + "helpers" + s + "config.yml";
        InputStream inputStream = new FileInputStream(new File(configFilePath));
        Map<String, String> map = yaml.load(inputStream);
        System.out.println(map);

        return map.get("URL");
    }
}
