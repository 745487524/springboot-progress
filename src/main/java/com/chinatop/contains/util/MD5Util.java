package com.chinatop.contains.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String str2MD5(String md5Str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return "";
        }
        char[] charArray = md5Str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                stringBuffer.append(0);
            }
            stringBuffer.append(Integer.toHexString(val));
        }
        return stringBuffer.toString();

    }

    public static String converMD5(String instr) {
        char[] a = instr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    public static void main(String[] args) {
        String s = "000015";
        System.out.println("MD5后：" + str2MD5(s));
        System.out.println("加密的：" + converMD5(s));
        System.out.println("解密的：" + converMD5(converMD5(s)));
    }
}
