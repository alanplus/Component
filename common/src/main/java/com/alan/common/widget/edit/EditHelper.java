package com.alan.common.widget.edit;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Alan
 * 时 间：2019-12-05
 * 简 述：<功能简述>
 */
public class EditHelper {

    public static void setCursorEnd(EditText editText) {
        if (null != editText && !TextUtils.isEmpty(editText.getText().toString())) {
            editText.setSelection(editText.getText().toString().length());
        }
    }

    public static void setText(EditText editText, String text, TextWatcher textWatcher) {
        if (null == textWatcher) {
            editText.setText(text);
        } else {
            editText.removeTextChangedListener(textWatcher);
            editText.setText(text);
            editText.addTextChangedListener(textWatcher);
        }
    }

}
