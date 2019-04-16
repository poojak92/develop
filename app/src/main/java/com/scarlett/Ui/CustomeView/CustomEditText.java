package com.scarlett.Ui.CustomeView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import com.scarlett.R;
import com.scarlett.Ui.Callback.IOnTextPastedListener;
import com.scarlett.Validation.DecimalFilter;
import com.scarlett.constants.AppConstants;
import com.scarlett.helper.FontHelper;


/**
 * Created by rac on 2/1/19.
 */

public class CustomEditText extends AppCompatEditText {

    private IOnTextPastedListener iOnTextPastedListener;
    private int prevCount = -1;
    private Context mContext;

    public CustomEditText(Context context) {
        super(context);
        mContext = context;
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initFont(context, attrs);
        initInputFilter(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initFont(context, attrs);
        initInputFilter(context, attrs);
    }


    public String getStringText() {
        if (getText() != null) {
            return getText().toString().trim();
        }
        return null;
    }

    public void addOnTextPastedListener(IOnTextPastedListener iOnTextPastedListener) {
        this.iOnTextPastedListener = iOnTextPastedListener;
    }

    @Override
    public boolean onTextContextMenuItem(int id) {

        boolean consumed = super.onTextContextMenuItem(id);
        switch (id) {
            case android.R.id.paste:
                if (iOnTextPastedListener != null)
                    iOnTextPastedListener.onPaste();
                break;
        }

        return consumed;
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

    public void initInputFilter(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.filterType);

        boolean isAmountField = a.getBoolean(R.styleable.filterType_isAmountField, false);

        if (isAmountField) {
            setFilters(new InputFilter[] {new DecimalFilter()});

           /* addOnTextPastedListener(new IOnTextPastedListener() {
                @Override
                public void onPaste() {
                    String clipboardText = CommanUtils.getClipboardTextIfAny(context);
                    filterClipboardText(clipboardText);
                }
            });*/
        }

        //setFilters(new InputFilter[] {new SpaceInputFilter()});
       addTextChangedListener(mTextWatcher);
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String enteredText = s.toString();
            StringBuffer sbEnteredText = new StringBuffer(enteredText);


            if(sbEnteredText.length() == 1 && sbEnteredText.equals(" ")){
                sbEnteredText.deleteCharAt(0);
                removeTextChangedListener(mTextWatcher);
                setText(sbEnteredText);
                addTextChangedListener(mTextWatcher);
            }else if(sbEnteredText.length() > 1){

                int prevIndex = sbEnteredText.length() - 2;
                int currentIndex = sbEnteredText.length() - 1;

                String prevChar = sbEnteredText.charAt(prevIndex)+"";
                String currentChar = sbEnteredText.charAt(currentIndex)+"";

                if(prevChar.equalsIgnoreCase(" ") && currentChar.equalsIgnoreCase(" ")){
                    sbEnteredText = sbEnteredText.deleteCharAt(currentIndex);
                    removeTextChangedListener(mTextWatcher);
                    clearComposingText();
                    setText(sbEnteredText);
                    setSelection(sbEnteredText.length());
                    addTextChangedListener(mTextWatcher);
                }

            }


            prevCount = count;


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




    public void filterClipboardText(final String clipboardText){

        if(clipboardText != null){
            String a[] = clipboardText.split("\\.");
            Log.d("TAGTAG","");
        }
    }

}
