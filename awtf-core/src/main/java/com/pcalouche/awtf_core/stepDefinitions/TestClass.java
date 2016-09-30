package com.pcalouche.awtf_core.stepDefinitions;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Properties;

public class TestClass {
    public static void main(String[] args) {
        System.out.println("java.io.tmpDir->" + System.getProperty("java.io.tmpdir"));
        System.out.println("user.dir->" + System.getProperty("user.dir"));
        System.out.println("user.home->" + System.getProperty("user.home"));
        Properties properties = new Properties();
        properties.setProperty("[MRN1]", "1111");
        properties.setProperty("[MRN2]", "22222");

//        List<String> list = new ArrayList<>();
//        list.add("param1=blah");
//        list.add("param2=blah2");

        Path p = Paths.get(System.getProperty("user.home"), "myDir");
//        Path p = Paths.get("C:\\temp\\myFile.properties");
        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream o = new BufferedOutputStream(Files.newOutputStream(p.resolve("myFile.properties"), StandardOpenOption.CREATE))) {
//            o.write(String.join("\n", list).getBytes());
            properties.store(o, "MRN Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties propertiesRead = new Properties();
        try (InputStream i = new BufferedInputStream(Files.newInputStream(p.resolve("myFile.properties")))) {
            propertiesRead.load(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(propertiesRead.getProperty("[MRN1]"));

        for (Map.Entry<Object, Object> e : propertiesRead.entrySet()) {
            System.out.println("key->" + e.getKey() + " value->" + e.getValue());
        }
    }
}
