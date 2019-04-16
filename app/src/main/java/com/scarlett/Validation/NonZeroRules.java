package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;


// it will check weather string contains 0
public class NonZeroRules extends QuickRule<EditText> {
    String Message,Data;

    public NonZeroRules(int sequence, String message) {
        super(sequence);
        Message = message;
    }

    @Override
    public boolean isValid(EditText view) {
        Data = view.getText().toString().trim();
        if (Data.equals("0")){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String getMessage(Context context) {
        return Message;
    }
}
