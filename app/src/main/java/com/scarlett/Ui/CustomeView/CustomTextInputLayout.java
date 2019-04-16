package com.scarlett.Ui.CustomeView;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;


import com.scarlett.R;
import com.scarlett.helper.FontHelper;

import java.lang.reflect.Field;

/**
 * Created by rac on 24/1/19.
 */

public class CustomTextInputLayout extends TextInputLayout {

    public CustomTextInputLayout(Context context) {
        super(context);
    }
    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    private void initFont(Context context) {

        EditText editText = getEditText();
        if (editText != null) {
            editText.setTypeface(FontHelper.getGothamBook(context));
        }
        try {
            // Retrieve the CollapsingTextHelper Field
            final Field cthf = TextInputLayout.class.getDeclaredField("mCollapsingTextHelper");
            cthf.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object cth = cthf.get(this);
            final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);


            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(cth)).setTypeface(FontHelper.getGothamBook(context));
        } catch (Exception ignored) {
            // Nothing to do
        }

        setHintTextAppearance(R.style.TextInputLayoutHintText);
        setErrorTextAppearance(R.style.ErrorTextAppearance);
    }


}
