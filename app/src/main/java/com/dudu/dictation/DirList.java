package com.dudu.dictation;


import java.util.ArrayList;

import java.io.File;
public class DirList {
    /**
     * 遍历文件夹下的文件夹
     * @param file 地址
     */
    public static ArrayList<File> getPath(File file) {
        ArrayList<File> list = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    list.add(0, f);
                } else {
                    getPath(f);
                }
            }
        }
        return list;
    }
    
    public static ArrayList<String> getName(File file) {
        
        ArrayList<String> namelist = new ArrayList<>();
        File[] fileArray = file.listFiles();
        System.out.println(fileArray);
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    String name=f.getName();
                    namelist.add(0,name);
                } else {
                    getName(f);
                }
            }
        }
        return namelist;
    }

    
}
