package com.scarlett.Validation;

import android.text.InputFilter;
import android.text.Spanned;

//it will chek input according to input and returns desired output in uppercase letter
public class AccountInfoInputFilter implements InputFilter {

    String regExp = "[A-Za-z0-9]+";
    String textEntered;
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.length()>0){
            textEntered = source.toString();
            if (textEntered.length()>0){
                textEntered = textEntered.toUpperCase();
            }
            if (textEntered.matches(regExp)){
                return textEntered;
            }
        }
        return "";
    }
}
