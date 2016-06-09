/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private String userName;
    private Button nextBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutFTActivity.class));
            }
        });

        View mainTextView = findViewById(R.id.textLayout);
        mainTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestUserName();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        nextBtn.setTextColor(Color.GRAY);
        nextBtn.setEnabled(false);
        ((ViewGroup)findViewById(R.id.textLayout)).removeAllViews();
        List<String> textArray;
        if((userName=getDatabase().get("usersName"))!=null){
            textArray = new ArrayList<>(3);
            textArray.add("Hi "+userName+"!");
            textArray.add("Welcome back to the survey of Futuretek.");
            textArray.add("If you want to know more about Futuretek touch the 'NEXT \u25B7' button.");
            animateText(textArray, new AnimationListDone() {
                public void done() {
                    Button nextBtn = ((Button) findViewById(R.id.nextBtn));
                    nextBtn.setTextColor(Color.GREEN);
                    nextBtn.setEnabled(true);
                }
            });
        }else{
            textArray = new ArrayList<>(3);
            textArray.add("Hi there!");
            textArray.add("This is the survey of Futuretek.");
            textArray.add("What's your name?");
            animateText(textArray, new AnimationListDone() {
                public void done() {
                    requestUserName();
                }
            });
        }
    }

    private void requestUserName(){
        if(userName==null){
            openInputDialog(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText userInput = ((EditText) v.findViewById(R.id.userInput));

                    /**
                     * Exercise 1
                     * Changed the declaration of userName as null to the value of the user Input
                     */
                    userName = userInput.getText().toString();

                    if (userName == null || userName.isEmpty()) {
                        List<String> textArray = new ArrayList<String>(1);
                        textArray.add("Didn't get your name...");

                        /**
                         * Exercise 2
                         * To disable the next button, we have to remove the method that activates the button.
                         * Because we don't need anymore an animation, we just need to call the animateText method
                         * where you just need to send the textArray as an argument.
                         */
                        animateText(textArray);

                    } else {
                        getDatabase().put("usersName", userName);
                        List<String> textArray = new ArrayList<String>(2);
                        textArray.add("Ah, nice.");
                        textArray.add("Hi " + userName + "!");
                        animateText(textArray, new AnimationListDone() {
                            public void done() {
                                activateNextButton();
                            }
                        });
                    }
                }
            });
        }
    }

    private void activateNextButton(){
        Button nextBtn = ((Button) findViewById(R.id.nextBtn));
        nextBtn.setTextColor(Color.GREEN);
        nextBtn.setEnabled(true);
    }
}

