package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;


//to validate minimum length as well as maximum length
public class MinMaxLengthRule extends QuickRule<EditText> {

    private String Message,Data;
    private int minLength;
    private int maxLength;

    public MinMaxLengthRule(int sequence, String message, int minLength, int maxLength) {
        super(sequence);
        Message = message;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(EditText view) {
        Data = view.getText().toString().trim();
        return Data!=null &&(Data.length() >= minLength && Data.length()<=maxLength);
    }

    @Override
    public String getMessage(Context context) {
        return Message;
    }
}
