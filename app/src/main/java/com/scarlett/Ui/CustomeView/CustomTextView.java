package com.scarlett.Ui.CustomeView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.scarlett.R;
import com.scarlett.constants.AppConstants;
import com.scarlett.helper.FontHelper;


/**
 * Created by rac on 2/1/19.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(context, attrs);
    }

    public String getStringText() {
        if(getText() != null) {
            return getText().toString().trim();
        }
        return null;
    }

    public void initFont(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.textFonts);

        int mTextFont = a.getInteger(R.styleable.textFonts_fontType, AppConstants.Fonts.GOTHAM_BOOK);

        switch (mTextFont) {
            case AppConstants.Fonts.GOTHAM_BLACK:
                setTypeface(FontHelper.getGothamBlack(context));

                break;
            case AppConstants.Fonts.GOTHAM_BOLD:
                setTypeface(FontHelper.getGothamBold(context));

                break;
            case AppConstants.Fonts.GOTHAM_BOOK:
                setTypeface(FontHelper.getGothamBook(context));

                break;
            case AppConstants.Fonts.GOTHAM_LIGHT:
                setTypeface(FontHelper.getGothamLight(context));

                break;
            case AppConstants.Fonts.GOTHAM_MEDIUM:
                setTypeface(FontHelper.getGothamMedium(context));
                break;

            case AppConstants.Fonts.GOTHAM_THIN:
                setTypeface(FontHelper.getGothamThin(context));
                break;

            default:
                setTypeface(FontHelper.getGothamBook(context));
                break;
        }
    }
}
