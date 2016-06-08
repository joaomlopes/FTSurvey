/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class BaseActivity extends Activity {
    private Database db;
    private String activityClassName;
    protected Database getDatabase(){
        return db;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(getApplicationContext());
        activityClassName = this.getClass().getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean firstTime = db.getBoolean(activityClassName);
        if(!firstTime){
            db.put(activityClassName, true);
            db.putSkill("Android");
            db.putSkill("Java");
            db.putSkill("Software Developer");
        }

    }

    protected void animateText(List<String> textArray, final AnimationListDone doneListener) {
        new TextAnimator(db.getBoolean(activityClassName), this, R.id.scrollView, R.id.textLayout, textArray, new AnimationListDone() {
            public void done() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        doneListener.done();
                    }
                });
            }
        });
    }
    protected void animateText(List<String> textArray) {
        new TextAnimator(db.getBoolean(activityClassName), this, R.id.scrollView, R.id.textLayout, textArray);
    }

    protected void openInputDialog(final View.OnClickListener onClickListener) {
        final Dialog dlg = new Dialog(this);
        dlg.setContentView(R.layout.dialog);
        try{
            ((Button) dlg.findViewById(R.id.okBtn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dlg.dismiss();
                    onClickListener.onClick(dlg.getWindow().getDecorView());
                }
            });
            dlg.setOnCancelListener(
                    new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            dlg.dismiss();
                        }
                    });
            dlg.show();
        }catch (Exception e){
        }
    }

    protected void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}

