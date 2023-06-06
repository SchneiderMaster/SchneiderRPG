package com.example.schneider_rpg;

import static com.example.schneider_rpg.MainActivity.questDao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class QuestDialog extends DialogFragment {

    View view;
    Quest quest = new Quest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.quest_dialog_layout, container, false);


        NumberPicker numberPicker = view.findViewById(R.id.maxNumber);
        numberPicker.setMaxValue(99);
        numberPicker.setMinValue(2);
        numberPicker.setWrapSelectorWheel(false);

        CheckBox checkBox = view.findViewById(R.id.isCheckable);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    view.findViewById(R.id.maxNumber).setBackgroundColor(Color.argb(255, 55, 55, 55));
                    view.findViewById(R.id.maxNumber).setEnabled(false);
                }
                else {
                    view.findViewById(R.id.maxNumber).setBackgroundColor(Color.rgb(0,0,0));
                    view.findViewById(R.id.maxNumber).setEnabled(true);
                }

            }
        });


        return view;
    }

    public static QuestDialog newInstance(){
        QuestDialog fragment = new QuestDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Quest")
                .setPositiveButton("Create Quest", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox checkBox = view.findViewById(R.id.isCheckable);
                        NumberPicker numberPicker = view.findViewById(R.id.maxNumber);

                        EditText questName = view.findViewById(R.id.editQuestName);
                        quest.name = questName.getText().toString();
                        quest.progress = 0;


                        if(checkBox.isChecked()){
                            quest.max = 1;
                        } else {
                            quest.max = numberPicker.getValue();
                        }



                        questDao.insert(quest);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });




        AlertDialog dialog = builder.create();

        return dialog;
    }
}
