package com.dudu.dictation;


import java.util.ArrayList;

import java.io.File;
public class DirList {


    /**
     * 遍历文件夹下的文件夹
     * @param file 地址
     */
    
    public static ArrayList<Dictation> getName(File file) {
        ArrayList<Dictation> nameList = new ArrayList<Dictation>();
        File[] fileArray = file.listFiles();
        System.out.println(fileArray);
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
                if (f.isFile()) {
                    String name=f.getName();
                    Dictation dictation= new Dictation(name,R.drawable.text);
                    nameList.add(0,dictation);
                }
                if (f.isDirectory()) {
                    String name=f.getName();
                    Dictation dictation= new Dictation(name,R.drawable.recorded);
                    nameList.add(0,dictation);
                }
            }
        }
        return nameList;
    }
    public static ArrayList<String> getRecordedFileNames(File file) {
        ArrayList<String> nameList = new ArrayList<>();
        File[] fileArray = file.listFiles();
        System.out.println(fileArray);
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
                if (f.isFile()) {
                    String name=f.getName();
                    nameList.add(0,name);
                }

            }
        }
        return nameList;
    }
    
}
