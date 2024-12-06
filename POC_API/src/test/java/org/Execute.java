package org;

import org.utilities.FileManager;

import java.util.List;
import java.util.Map;

public class Execute {
    public static void main(String[] args) {
        FileManager.createFileCopy("C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api\\confirmClone.txt",
                "C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api\\confirmCloneDup.txt");

        Map<String,String> data =  FileManager.getFileDirectory("C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api");

        FileManager.createFile("C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api","testFile",".pdf");

        StringBuilder sb = FileManager.readFile("C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api\\confirmClone.txt");

        List<String> list = FileManager.readFileLines("C:\\Users\\aturuk\\IdeaProjects\\mtf-automation-testing-api\\confirmClone.txt");

        Map<Integer,List<String>> dataCsv = FileManager.getDataFromCsv("list.csv",",");
    }
}
