package com.cloud.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinYinUtil
{

    private PinYinUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String chinesToUppercase(String newString) {

       StringBuilder code = new StringBuilder();

        char[] newChar = newString.toCharArray(); //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    code.append(PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    code.append(newChar[i]);
                }
            }
            else {
                code.append(newChar[i]);
            }
        }
        return code.toString();
    }
}
