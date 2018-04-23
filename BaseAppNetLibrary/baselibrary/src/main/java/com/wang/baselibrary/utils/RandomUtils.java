package com.wang.baselibrary.utils;

import java.util.Random;


public class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new AssertionError();
    }

    /**
     * 得到一个固定长度的随机字符串，它是大写的，小写字母和数字的混合
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * 得到一个固定长度的随机字符串，它是一个数字的混合
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * 得到一个固定长度的随机字符串，它是大写和小写字母的混合
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 得到一个固定长度的随机字符串，它是大写字母的混合
     * 
     * @param length  length
     * @return   CapitalLetters
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 得到一个固定长度的随机字符串，它是小写字母的混合
     * 
     * @param length  length
     * @return  get a fixed-length random string, its a mixture of lowercase letters
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * 得到一个固定长度的随机字符串, 它是所传入的字符的混合
     * 
     * @param source  source
     * @param length  length
     * @return  get a fixed-length random string, its a mixture of chars in source
     */
    public static String getRandom(String source, int length) {
        return UtilStringEmpty.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * 得到一个固定长度的随机字符串, 它是所传入的字符数组内容的混合
     * 
     * @param sourceChar    sourceChar
     * @param length  length
     * @return   get a fixed-length random string, its a mixture of chars in sourceChar
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }


    /**
     *
     * @param max  接受的数值
     * @return  返回一个随机的数值
     */
    public static int getRandom(int max) {

        return getRandom(0, max);
    }


    /**
     *
     * @param min  最小
     * @param max  最大
     * @return  返回一个范围的数值
     */
    public static int getRandom(int min, int max) {

        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * 洗牌算法，随机地使用默认的随机性来源对指定的数组进行随机化
     * 
     * @param objArray  数组
     * @return 从新的数组
     */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }

    /**
     * 洗牌算法，随机地对指定的数组进行分析
     * 
     * @param objArray  数组
     * @param shuffleCount  洗的个数
     * @return  是否成功
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    /**
     * 洗牌算法，随机地使用默认的随机性来源对指定的int数组进行随机操作。
     * 
     * @param intArray  数组
     * @return  洗牌之后
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * 洗牌算法，随机地对指定的int数组进行随机化
     * 
     * @param intArray   数组
     * @param shuffleCount  范围
     * @return  新的数组
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }
}
