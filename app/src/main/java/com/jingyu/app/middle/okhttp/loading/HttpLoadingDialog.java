package com.jingyu.app.middle.okhttp.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.jingyu.app.R;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class HttpLoadingDialog extends Dialog {

    public HttpLoadingDialog(Context context) {
        super(context, R.style.TransDialog);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_http_loading, null));
        setWindowLayoutStyleAttr();
    }

    private void setWindowLayoutStyleAttr() {
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp != null) {
            lp.alpha = 0.7f;
            lp.dimAmount = 0.3f;
            window.setAttributes(lp);
        }
    }

}



