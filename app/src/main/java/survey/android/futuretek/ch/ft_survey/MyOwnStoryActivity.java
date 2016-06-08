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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyOwnStoryActivity extends BaseActivity {
    private Button nextBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MyOwnStoryActivity.this, SkillsActivity.class));
            }
        });

    }

    protected void onResume() {
        super.onResume();
        nextBtn.setTextColor(Color.GRAY);
        nextBtn.setEnabled(false);
        ((ViewGroup)findViewById(R.id.textLayout)).removeAllViews();
        List<String> textArray;

        textArray = new ArrayList<>();
        textArray.add("My Story!");
        textArray.add("As you know, I'm a software Developer. I'm 25 years old and since my childhood that computers has been part of my life.");
        textArray.add("Of course, part of it is the curiosity and the needing to understand how things work! How a metal box that makes so much noise can do all the things that I want?");
        textArray.add("That's the reason that I followed computers on a professional career. I've been developing for almost 10years and on this decade I had the opportunity to see the biggest evolution of technology.");
        textArray.add("The internet and the websites, the smartphones and their applications, the IoT...");
        textArray.add("I've used, for work or for fun, so many languages and frameworks, I've tested and I'm testing everything that I think that I should test. I've developed websites, apps, games on Unity and also on Robotics: cars and a football goalkeeper.");
        textArray.add("But the reason that you're seeing my candidature is that I wanna be part of something bigger! I want to learn, I want to teach! I want to be part of a bigger team with bigger responsabilities! I want to be part of Futuretek. :)");
        textArray.add("Click next to see my skills");
        animateText(textArray, new AnimationListDone() {
            public void done() {
                Button nextBtn = ((Button) findViewById(R.id.nextBtn));
                nextBtn.setTextColor(Color.GREEN);
                nextBtn.setEnabled(true);
            }
        });
    }
}