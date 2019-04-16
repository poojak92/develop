package com.scarlett.Validation;

import android.content.Context;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.QuickRule;

import commons.validator.routines.EmailValidator;


// to validate Email
public class EmailRules extends QuickRule<EditText> {
    String Email;
    String Data;


    public EmailRules(int sequence, String email) {
        super(sequence);
        Email = email;
    }

    @Override
    public boolean isValid(EditText view) {
        Data = view.getText().toString().trim();
        return EmailValidator.getInstance().isValid(Data);
    }

    @Override
    public String getMessage(Context context) {
        return Email;
    }
}
