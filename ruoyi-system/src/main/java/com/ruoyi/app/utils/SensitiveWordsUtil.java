package com.ruoyi.app.utils;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;

public class SensitiveWordsUtil {

    public Boolean sensitiveWord(String txt) {
        return SensitiveWordHelper.contains(txt);
    }
}