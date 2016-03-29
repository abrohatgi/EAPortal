package com.nuance.expertassistant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class LoadConfig {

    public static void main(String args[]) {

        String url = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
        System.out.println(" The URL is :" + url);
    }

    public static Properties loadExternalConfiguration() throws IOException {

        Properties properties = new Properties();

        properties.load(new FileInputStream("/Users/abhishek_rohatgi/git_views/ExpertAssistant/src/main/java/Walmart.properties"));

        //get the value of the property
        return properties;

    }

    public static Properties loadConfiguration() throws IOException {
        //Get the inputStream-->This time we have specified the folder name too.

        InputStream inputStream = LoadConfig.class.getClassLoader()
                .getResourceAsStream("Walmart.properties");

        Properties properties = new Properties();

        System.out.println("Loading Config from Internal Source");

        //load the inputStream using the Properties
        properties.load(inputStream);

        return properties;
    }

    public static String getValue4Key(String Key) {
        String keyValue = null;

        try {
            keyValue = loadExternalConfiguration().getProperty(Key);
            // System.out.println("Loaded from External Source : " + keyValue);
            return keyValue;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getStackTrace());
            System.out.println("[Error : Unable to load config from external source]");
        }

        try {
            keyValue = loadConfiguration().getProperty(Key);
            //  System.out.println("KeyValue = " + keyValue);
            return keyValue;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getStackTrace());
            System.out.println("[Error : Unable to load config from internal source]");
            return "error_path";
        }

    }

    public static String getValueFromFile(String Key, String URLName) {

        String keyValue = null;

        try {
            URL url = new URL(URLName);
            InputStream in = url.openStream();

            Properties properties = new Properties();

            properties.load(in);
            keyValue = properties.getProperty(Key);
            return keyValue;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
