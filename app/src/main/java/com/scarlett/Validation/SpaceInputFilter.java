package com.scarlett.Validation;

import android.text.InputFilter;
import android.text.Spanned;

public class SpaceInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {


        String enteredText = source.toString();
        String destString = dest.toString();

        int prevIndex = enteredText.length() - 2;
        int currentIndex = enteredText.length() - 1;

        if(destString.length() == 0 && enteredText.equalsIgnoreCase(" ")){
            return "";
        }

       /*if(destString.contains(" ")){
           return "";
       }*/


        if(prevIndex > 0){
            String prevChar = enteredText.charAt(prevIndex)+"";
            String currentChar = enteredText.charAt(currentIndex)+"";

            if(prevChar == " " && currentChar == " "){
                return  "";
            }else {
                return enteredText;
            }

        }

        return enteredText;
    }
}
