package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

// it will check for empty String and returns accordingly
public class NotEmptyRule extends QuickRule<EditText> {

    String Message;
    String Data;

    public NotEmptyRule(int sequence, String message) {
        super(sequence);
        Message = message;
    }

    @Override
    public boolean isValid(EditText view) {
        Data = view.getText().toString().trim();
        if (Data == null || Data.isEmpty()){

            return false;
        }
        return true;
    }

    @Override
    public String getMessage(Context context) {
        return Message;
    }
}
