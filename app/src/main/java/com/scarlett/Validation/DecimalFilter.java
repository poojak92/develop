package com.scarlett.Validation;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;

public class DecimalFilter extends DigitsKeyListener {

    public DecimalFilter() {
        super(true, true);
        getInstance("1234567890,.");
    }

    private int digits = 2;

    public void setDigits(int d) {
        digits = d;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        CharSequence out = super.filter(source, start, end, dest, dstart, dend);

        // if changed, replace the source
        if (out != null) {
            source = out;
            start = 0;
            end = out.length();
        }

        int len = end - start;

        // if deleting, source is empty
        // and deleting can't break anything
        if (len == 0) {
            return source;
        }

        int dlen = dest.length();

        // Find the position of the decimal .
        for (int i = 0; i < dstart; i++) {
            if (dest.charAt(i) == '.') {
                // being here means, that a number has
                // been inserted after the dot
                // check if the amount of digits is right
                return (dlen-(i+1) + len > digits) ?
                        "" :
                        new SpannableStringBuilder(source, start, end);
            }
        }

        for (int i = start; i < end; ++i) {
            if (source.charAt(i) == '.') {
                // being here means, dot has been inserted
                // check if the amount of digits is right
                if ((dlen-dend) + (end-(i + 1)) > digits)
                    return "";
                else
                    break;  // return new SpannableStringBuilder(source, start, end);
            }
        }

        String oldString = dest.toString();
        if(!oldString.contains(".") && !source.equals(".") && oldString.length() == 7){
            return "";
        }else if(oldString.contains(".")){
            String split[] = oldString.split("\\.");

            if(split[0].length() == 7){
                return "";
            }
        }

        if(oldString.isEmpty() && source.equals(".")){
            return "";
        }

        // if the dot is after the inserted part,
        // nothing can break
        return new SpannableStringBuilder(source, start, end);
    }
}
