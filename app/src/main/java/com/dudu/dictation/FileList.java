package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import android.content.Context;
import android.os.FileUtils;
import java.io.File;
public class FileList {
    /**
     * 遍历文件夹下的文件
     * @param file 地址
     */
    public static ArrayList<File> getFile(File file) {
        ArrayList<File> list = new ArrayList<>();
        
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
                if (f.isFile()) {
                    list.add(0, f);
                } else {
                    getFile(f);
                }
            }
        }
        return list;
    }
    
    
}
