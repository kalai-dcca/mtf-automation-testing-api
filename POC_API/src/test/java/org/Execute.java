package org;

import org.utilities.FileManager;

import java.util.List;
import java.util.Map;

public class Execute {
    public static void main(String[] args) {
        FileManager.createFileCopy("confirmClone.txt",
                "confirmCloneDup.txt");

        Map<String,String> data =  FileManager.getFileDirectory("mtf-automation-testing-api");

        FileManager.createFile("mtf-automation-testing-api","testFile",".pdf");

        StringBuilder sb = FileManager.readFile("confirmClone.txt");

        List<String> list = FileManager.readFileLines("confirmClone.txt");

        Map<Integer,List<String>> dataCsv = FileManager.getDataFromCsv("list.csv",",");
    }
}
