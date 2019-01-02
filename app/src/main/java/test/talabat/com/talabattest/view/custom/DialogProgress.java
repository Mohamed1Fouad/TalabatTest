package test.talabat.com.talabattest.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import test.talabat.com.talabattest.R;


public class DialogProgress extends Dialog {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setCancelable(false);

    }

    public DialogProgress(Context context) {
        super(context);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }

}

