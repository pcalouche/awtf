package com.pcalouche.awtf_core.util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlHelper {
    public static void printToScreen(Object data) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        Yaml yaml = new Yaml(dumperOptions);
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
    }

    public static Object loadFromInputStream(String inputStreamPath) {
        try {
            InputStream inputStream = YamlHelper.class.getResourceAsStream(inputStreamPath);
            Object data = new Yaml().load(inputStream);
            inputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(Object data, String filePath) {
        try {
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setPrettyFlow(true);
            Yaml yaml = new Yaml(dumperOptions);
            Writer fileWriter = new FileWriter(new File(filePath));
            yaml.dump(data, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readFromFile(String filePath) {
        try {
            Reader reader = new FileReader(new File(filePath));
            Object data = new Yaml().load(reader);
            reader.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
