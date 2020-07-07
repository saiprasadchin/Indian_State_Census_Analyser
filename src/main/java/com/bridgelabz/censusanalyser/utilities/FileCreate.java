package com.bridgelabz.censusanalyser.utilities;

import java.io.FileWriter;
import java.io.IOException;

public class FileCreate {
    public static void jsonFileWriter(String csvFilePath,String jsonString)  {
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvFilePath);
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
