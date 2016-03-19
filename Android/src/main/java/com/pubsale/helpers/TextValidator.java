package com.pubsale.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by Nahum on 06/03/2016.
 */
public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}