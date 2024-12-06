package org.utilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {


    /**
     * Create Copy of Provided File
     * @param sourcePath Source Path
     * @param copyPath Duplicate Path
     */
    public static void createFileCopy(String sourcePath, String copyPath) {
        File sourceFile = new File(sourcePath);
        File copyFile = new File(copyPath);

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            fileInputStream = new FileInputStream(sourceFile);
            fileOutputStream = new FileOutputStream(copyFile);

            if(fileInputStream.available() > 0){
                int i;
                while((i = fileInputStream.read()) != -1) {
                    fileOutputStream.write(i);
                }
            }else{
                throw new Exception();
            }
        }catch (FileNotFoundException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(Objects.nonNull(fileInputStream)){
                    fileInputStream.close();
                }
                if(Objects.nonNull(fileOutputStream)){
                    fileOutputStream.close();
                }
            }catch (IOException x){
                System.out.println(Arrays.toString(x.getStackTrace()));
            }
        }
    }


    /**
     * Return Map<String,String> of the provided Directory
     * @param filePath Directory
     * @return data map
     */
    public static Map<String,String> getFileDirectory(String filePath){
        Map<String,String> data = new HashMap<>();
        File file = new File(filePath);
        File[] dir = file.listFiles();
        if(Objects.nonNull(dir)){
            Arrays.sort(dir);
            Arrays.stream(dir).filter(File::isFile).forEach(c -> data.computeIfAbsent("File: "+c.getName(), s -> "Path: "+c.getAbsolutePath()));
            Arrays.stream(dir).filter(File::isDirectory).forEach(c -> data.computeIfAbsent("Directory: "+c.getName(), s -> "Directory: "+c.getAbsolutePath()));
        }
        return data;
    }

    /**
     * Create New File
     * @param path location
     * @param name name
     * @param extension extension
     */
    public static void createFile(String path, String name, String extension){
        String dir = path + File.separator + name + extension;
        try{
            File file = new File(Objects.requireNonNull(dir));
            if(Objects.requireNonNull(file).createNewFile()){
                System.out.println("New File Created at: " + dir);
            }else{
                System.out.println("File Already Exists");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read Given File and return StringBuilder
     * @param filePath file location
     * @return StringBuilder
     */
    public static StringBuilder readFile(String filePath) {
        BufferedReader br;
        StringBuilder sb;
        try{
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));

            int i;
            sb = new StringBuilder();
            while((i = br.read()) != -1) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }


    /**
     * Return All Lines in a form of List<String> from Provided File
     * @param filePath provided file path
     * @return List<String>
     */
    public static List<String> readFileLines(String filePath) {
        List<String> li;
        try{
            li = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return li;
    }


    /**
     * Collect data from CSV File
     * @param filePath file location
     * @param delimiter delimiter
     * @return CSV Data Map
     */
    public static Map<Integer,List<String>> getDataFromCsv(String filePath, String delimiter){
        Map<Integer,List<String>> fileData = new HashMap<>();
        try{
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int row =1;
            while(Objects.nonNull((line = bufferedReader.readLine()))){
                String[] data = line.split(delimiter);
                fileData.put(row,Arrays.stream(data).collect(Collectors.toList()));
                row++;
            }
            return fileData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
