package com.ganymede.rtservice.mockreported;

/**
 * created by wuke on 2019/3/12
 */
public class Test3 {
    public static void main(String[] args) {
        String str = "你好，谢谢。abc123";
        byte[] bytes = str.getBytes();
        String res = new String(bytes);
        System.out.println(res);
    }
}
