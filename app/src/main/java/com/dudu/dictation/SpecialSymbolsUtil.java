package com.dudu.dictation;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断是否含有特殊字符
 * @return true为包含，false为不包含
 */


public class SpecialSymbolsUtil {
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}