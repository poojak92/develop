package com.scarlett.Validation;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


// it will check dots '.' in character sequence and return null

public class DecimalDigitsInputFilter implements InputFilter {
    private int DecimalDigits = 2;

    Pattern mPattern;

    public DecimalDigitsInputFilter(int decimalDigits) {
        this.DecimalDigits = decimalDigits;
    }

    public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)");
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }

}
