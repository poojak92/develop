package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

// use pattern rule for Validating regular expressions and send message

public class PatternRule extends QuickRule<EditText> {

    String Message , Expression ,Data;

    public PatternRule(int sequence, String pattern , String message) {
        super(sequence);
        Expression = pattern;
        Message = message;
    }

    @Override
    public boolean isValid(EditText view) {
         Data = view.getText().toString().trim();
         return Data!=null ? Data.matches(Expression) : false;
    }

    @Override
    public String getMessage(Context context) {
        return Message;
    }
}
