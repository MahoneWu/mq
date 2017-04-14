package com.mouse.moon.str;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahone Wu on 2017/4/7.
 */
public class MessageFormateDemo {


    public static void main(String[] args) {
        List<String>  list = new ArrayList<String>();

        list.add("123");
        list.add("234");

        String str ="hi,{0},啊哈哈哈哈哈哈哈哈{1}";
        System.out.println(MessageFormat.format(str,list.toArray()));

    }

}
