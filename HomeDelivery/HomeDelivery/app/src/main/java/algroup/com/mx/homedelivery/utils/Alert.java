package algroup.com.mx.homedelivery.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import algroup.com.mx.homedelivery.R;


public class Alert extends AlertDialog {

    private Context context;
    private AlertDialog alertDialog;
    private String message;
    private String title;

    @Override
    public boolean isShowing() {
        return alertDialog.isShowing();
    }

    @Override
    public Button getButton(int whichButton) {
        return alertDialog.getButton(whichButton);
    }

    public Alert(Context context) {
        super(context);
        title = "";
        this.context = context;
        Builder builder = new Builder(context);
        alertDialog = builder.create();
    }

    public void setButton(int whichButton, CharSequence text,
            OnClickListener listener) {
        alertDialog.setButton(whichButton, text, listener);
    }

    public void setMessage(CharSequence message) {
        this.message = message.toString();
    }

    public void setTitle(CharSequence title) {
        this.title = title.toString();
    }

    public void show() {
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (title != null && title.length() > 0) {
            View viewTilte = new View(context);
            viewTilte = inflate.inflate(R.layout.alert_simple_title, null);
            alertDialog.setCustomTitle(viewTilte);
        }
        
        View viewBody = new View(context);
        viewBody = inflate.inflate(R.layout.alert_simple_body, null);
        alertDialog.setView(viewBody);

        alertDialog.show();

        TextView viewMsgBody = (TextView) alertDialog
                .findViewById(R.id.alert_simple_body);
        viewMsgBody.setText(message);

        TextView viewMsgTitle = (TextView) alertDialog
                .findViewById(R.id.alert_simple_title);
        if (title != null && title.length() > 0) {
            viewMsgTitle.setText(title);
        }
    }

    public void dismiss() {
        alertDialog.dismiss();
    }

}
