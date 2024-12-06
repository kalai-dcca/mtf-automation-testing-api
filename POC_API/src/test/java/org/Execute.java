package org;

import org.utilities.FileManager;

import java.util.*;
import java.util.stream.Collectors;

public class Execute {
    public static void main(String[] args) {
        FileManager.createFileCopy("confirmClone.txt",
                "confirmCloneDup.txt");

        //Map<String,String> data =  FileManager.getFileDirectory("mtf-automation-testing-api");

        //FileManager.createFile("mtf-automation-testing-api","testFile",".pdf");

        //StringBuilder sb = FileManager.readFile("confirmClone.txt");

        //Map<Integer,List<String>> dataCsv = FileManager.getDataFromCsv("list.csv",",");

        List<String> list = FileManager.readFileLines("PDEData_20240729132923-fake.txt");

        Map<String,List<String>> fakeFileMap = new HashMap<>();

        for(String each : list){
            String[] s = each.trim().split(" ");
            List<String> dataList = Arrays.stream(s).collect(Collectors.toList()).stream().filter(p -> Objects.nonNull(p) && !p.trim().isEmpty()).collect(Collectors.toList());
            dataList.remove(s[0]);
            fakeFileMap.put(s[0],dataList);
        }
    }
}
