package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;


// to validate minimum length
public class MinLength extends QuickRule<EditText> {
    String Message;
    int Length;
    String Data;

    public MinLength(int sequence,int length , String message) {
        super(sequence);
        Length = length;
        Message = message;
    }

    @Override
    public boolean isValid(EditText view) {
        Data = view.getText().toString().trim();
        return Data!=null?Data.length()>=Length:false;
    }

    @Override
    public String getMessage(Context context) {
        return Message;
    }
}
