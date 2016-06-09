/**
 * Copyright (C) futuretek AG 2016
 * All Rights Reserved
 *
 * @author Artan Veliju
 */
package survey.android.futuretek.ch.ft_survey;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SkillsActivity extends BaseActivity {
    private Button btn_add;
    private String skillName;
    private ListView listview;
    public List<String> _productlist = new ArrayList<String>();
    private ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        listview = (ListView) findViewById(R.id.listView);
        View mainTextView = findViewById(R.id.textLayout);
        mainTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        /**
         * Optional Exercise 1
         * Implement a functionality with which the user can insert a skill himself.
         * Assign the button and the event to add skill
         */
        btn_add = (Button) findViewById(R.id.addBtn);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkill();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((ViewGroup)findViewById(R.id.textLayout)).removeAllViews();
        List<String> textArray = new ArrayList<>(1);
        textArray.add("Please add a developer skill");
        animateText(textArray, new AnimationListDone() {
            @Override
            public void done() {
                /**
                 * Optional Exercise 1 and Optional Exercise 2
                 * Used to Refresh the skills list
                 * Implement a functionality with which the user can insert a skill himself.
                 * Call the listing skills so we don't have duplicate code on different methods
                 * Also, I changed the animateText method to just list the skills after the animation
                 */
                listSkills();
            }
        });

    }

    /**
     * Method to list all Skills
     */
    private void listSkills() {
        _productlist.clear();
        _productlist = getDatabase().getAllSkills();
        adapter = new ListAdapter(this);
        listview.setAdapter(adapter);
    }

    private class ListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        ViewHolder viewHolder;

        public ListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return _productlist.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView1);
                /**
                 * Optional Exercise 2
                 * Implement a functionality with which the user can update a skill.
                 * Add the Edit button click event
                 */
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editBtn);
                viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ViewGroup row = ((ViewGroup)v.getParent());
                        String id = ((TextView)row.findViewById(R.id.textView1)).getText().toString();
                        //Assign value to the skill name variable
                        skillName = id;
                        //Call method to edit the skill
                        editSkill();
                    }
                });

                viewHolder.delBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ViewGroup row = ((ViewGroup)v.getParent());
                        String id = ((TextView)row.findViewById(R.id.textView1)).getText().toString();
                        getDatabase().deleteSkill(id);
                        _productlist.remove(id);
                        adapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(_productlist.get(position));
            return convertView;
        }
    }

    /**
     * Optional Exercise 1
     * Implement a functionality with which the user can insert a skill himself.
     * Method to call the input to add the skill
     */
    private void addSkill() {

        skillName = null;

        openInputDialog(new View.OnClickListener() {
            public void onClick(View v) {
                EditText skillInput = ((EditText) v.findViewById(R.id.userInput));

                /**
                 * Exercise 1
                 * Changed the declaration of userName as null to the value of the user Input
                 */
                skillName = skillInput.getText().toString();

                if (skillName == null || skillName.isEmpty()) {

                    toast("Please insert the skill");

                } else {
                    insertSkill(skillName);
                }
            }
        });
    }

    /**
     * Optional Exercise 2
     * Implement a functionality with which the user can update a skill.
     * Method to call the input to edit the skill
     */
    private void editSkill() {

//        final EditText editInput = ((EditText) .findViewById(R.id.userInput));

//        editInput.setText(skillName, TextView.BufferType.EDITABLE);

        openInputDialog(new View.OnClickListener() {
            public void onClick(View v) {

                EditText editInput = ((EditText) v.findViewById(R.id.userInput));

                //Call method to update value on Database
                getDatabase().updateSkill(skillName, editInput.getText().toString());

                //Call method to list all the skills
                listSkills();

            }
        });

    }

    private class ViewHolder {
        TextView textView;
        Button delBtn;
        /**
         * Optional Exercise 2
         * Declaration of the edit button variable
         */
        Button editBtn;

    }

    private void insertSkill(String skill){
        try {
            getDatabase().putSkill(skill);
            _productlist = getDatabase().getAllSkills();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}