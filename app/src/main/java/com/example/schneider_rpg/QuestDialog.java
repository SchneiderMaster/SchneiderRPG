package com.example.schneider_rpg;

import static com.example.schneider_rpg.MainActivity.questDao;
import static com.example.schneider_rpg.MainActivity.skillDao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class QuestDialog extends DialogFragment {

    View view;
    Quest quest = new Quest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        view = inflater.inflate(R.layout.quest_dialog_layout, container, false);
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

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.quest_dialog_layout, null);






        String[] values = new String[100];

        for(int i = 0; i < values.length; i++){
            values[i] = String.valueOf(i +2);
        }

        NumberPicker numberPicker = view.findViewById(R.id.maxNumber);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(99);
        numberPicker.setDisplayedValues(values);

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
                    view.findViewById(R.id.maxNumber).setBackgroundColor(Color.argb(0,0,0,0));
                    view.findViewById(R.id.maxNumber).setEnabled(true);
                }

            }
        });

        Spinner spinner = view.findViewById(R.id.skillType);

        List<String> allSkills = skillDao.getAllSkillNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, allSkills);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);





        builder.setView(view);

        builder.setTitle("Add Quest")
                .setPositiveButton("Create Quest", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox checkBox = view.findViewById(R.id.isCheckable);
                        NumberPicker numberPickerMax = view.findViewById(R.id.maxNumber);

                        Spinner spinner = view.findViewById(R.id.skillType);
                        quest.skillId = skillDao.getIdFromName(spinner.getSelectedItem().toString());

                        EditText questName = view.findViewById(R.id.editQuestName);
                        quest.name = questName.getText().toString();
                        quest.progress = 0;

                        EditText reward = view.findViewById(R.id.rewardNumber);
                        quest.reward = Integer.parseInt(reward.getText().toString());


                        if(checkBox.isChecked()){
                            quest.max = 1;
                        } else {
                            quest.max = numberPickerMax.getValue();
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
