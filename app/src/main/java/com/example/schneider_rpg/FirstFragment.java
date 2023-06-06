package com.example.schneider_rpg;

import static com.example.schneider_rpg.MainActivity.editor;
import static com.example.schneider_rpg.MainActivity.questDao;
import static com.example.schneider_rpg.MainActivity.sharedPreferences;
import static com.example.schneider_rpg.MainActivity.skillDao;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.schneider_rpg.databinding.FragmentFirstBinding;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);


        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });

        binding.totalLevelText.setText("LVL. " + sharedPreferences.getInt("level", 0));
        binding.totalLevelBar.setMax(getMaxExperience(sharedPreferences.getInt("level", 0)));
        binding.totalLevelBar.setProgress(sharedPreferences.getInt("experience", 0));



        generateQuests();




        //initiate all the skills and draw them to the screen
        generateSkills();



    }

    @SuppressLint("SetTextI18n")
    void generateSkills(){
        //initiate all the skills and draw them to the screen
        binding.skills.removeAllViews();

        TextView textView = new TextView(getContext());
        textView.setId(R.id.skillsTitle);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("Skills");
        textView.setTextAppearance(R.style.Base_Theme_SchneiderRPG_Title);
        textView.setGravity(Gravity.START);

// If you are using a ConstraintLayout, set the constraints programmatically
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        textView.setLayoutParams(layoutParams);

// Add the TextView to the parent view
        binding.skills.addView(textView);



        List<Skill> skills = skillDao.getAll();
        for(int i = 0; i < skillDao.count(); i++) {
            ConstraintLayout skillLayout = binding.skills;
            @SuppressLint("InflateParams") View includeView = getLayoutInflater().inflate(R.layout.skill_layout, null);



            TextView title = (TextView) includeView.findViewById(R.id.skill_title);
            title.setText(skills.get(i).name + " LVL. " + skills.get(i).level);
            title.setTextColor(skills.get(i).color);



            ProgressBar bar = (ProgressBar) includeView.findViewById(R.id.skillProgress);
            bar.setProgressTintList(ColorStateList.valueOf(skills.get(i).color));
            bar.setMax(getMaxExperience(skills.get(i).level));
            bar.setProgress((skills.get(i).experience));



            Button button = (Button) includeView.findViewById(R.id.addHourButton);

            int finalI = i;
            while(skills.get(finalI).experience >= getMaxExperience(skills.get(finalI).level)){

                updateTotalLevel(10);

                skillDao.increaseLevel(skills.get(finalI).id);
                skillDao.updateXP(skills.get(finalI).experience - getMaxExperience(skills.get(finalI).level), skills.get(finalI).id);

                skills = skillDao.getAll();

                TextView title1 = (TextView) includeView.findViewById(R.id.skill_title);
                title1.setText(skills.get(finalI).name + " LVL. " + skills.get(finalI).level);

                ProgressBar bar3 = (ProgressBar) includeView.findViewById(R.id.skillProgress);
                bar3.setMax(getMaxExperience(skills.get(finalI).level));
                bar3.setProgress((skills.get(finalI).experience));

            }


            button.setOnClickListener(v -> {

                updateTotalLevel(10);

                Log.d("aöslkdjf", "level: " + sharedPreferences.getInt("level", 0) + "; progress: " + sharedPreferences.getInt("experience", 0));

                List<Skill> skills1 = skillDao.getAll();
                skillDao.addHour(skills1.get(finalI).id);

                ProgressBar bar1 = (ProgressBar) includeView.findViewById(R.id.skillProgress);
                bar1.setMax(getMaxExperience(skills1.get(finalI).level));
                bar1.setProgress((skills1.get(finalI).experience));

                skills1 = skillDao.getAll();

                Log.e("test", "id: " + skills1.get(finalI).id + "; xp: " + skills1.get(finalI).experience + "; maxNumber: " + getMaxExperience(skills1.get(finalI).level));

                while(skills1.get(finalI).experience >= getMaxExperience(skills1.get(finalI).level)){



                    skillDao.increaseLevel(skills1.get(finalI).id);
                    skillDao.updateXP(skills1.get(finalI).experience - getMaxExperience(skills1.get(finalI).level), skills1.get(finalI).id);

                    skills1 = skillDao.getAll();

                    TextView title1 = (TextView) includeView.findViewById(R.id.skill_title);
                    title1.setText(skills1.get(finalI).name + " LVL. " + skills1.get(finalI).level);

                    ProgressBar bar3 = (ProgressBar) includeView.findViewById(R.id.skillProgress);
                    bar3.setMax(getMaxExperience(skills1.get(finalI).level));
                    bar3.setProgress((skills1.get(finalI).experience));

                }

                ProgressBar bar2 = (ProgressBar) includeView.findViewById(R.id.skillProgress);
                bar2.setMax(getMaxExperience(skills1.get(finalI).level));
                bar2.setProgress((skills1.get(finalI).experience));
            });




            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            if(i == 0) {
                params.topToBottom = R.id.skillsTitle;
            }else {
                params.topToBottom = R.layout.skill_layout + skills.get(i-1).id;
            }
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.topMargin = 16;
            params.bottomMargin = 16;
            includeView.setLayoutParams(params);
            includeView.setId((R.layout.skill_layout) + skills.get(i).id);
            skillLayout.addView(includeView);
        }
    }

    @SuppressLint("SetTextI18n")
    void generateQuests(){
        //initiate all the skills and draw them to the screen
        binding.quests.removeAllViews();

        TextView textView = new TextView(getContext());
        textView.setId(R.id.questTitle);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText("Quests");
        textView.setTextAppearance(R.style.Base_Theme_SchneiderRPG_Title);
        textView.setGravity(Gravity.START);

// If you are using a ConstraintLayout, set the constraints programmatically
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        textView.setLayoutParams(layoutParams);

// Add the TextView to the parent view
        binding.quests.addView(textView);



        //initiate all the quests and draw them to the screen
        List<Quest> quests = questDao.getAll();
        for(int i = 0; i < questDao.count(); i++){
            ConstraintLayout questLayout = binding.quests;
            @SuppressLint("InflateParams") View includeView = getLayoutInflater().inflate(R.layout.quest_layout, null);



            TextView questName = includeView.findViewById(R.id.questName);
            String fullText = quests.get(i).name + " (" + quests.get(i).reward + " " + skillDao.getNameFromId(quests.get(i).skillId) + "-Exp, " + quests.get(i).progress + "/" + quests.get(i).max + ")";
            Log.e("test ", "fullText: " + fullText);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(fullText);
            int startIndex = quests.get(i).name.length() + 2;
            int endIndex = fullText.length() - 1;
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(skillDao.getColorFromId(quests.get(i).skillId));
            spannableStringBuilder.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            questName.setText(spannableStringBuilder);



            ProgressBar bar = (ProgressBar) includeView.findViewById(R.id.questProgress);
            bar.setProgressTintList(ColorStateList.valueOf(skillDao.getColorFromId(quests.get(i).skillId)));
            bar.setMax(quests.get(i).max);
            bar.setProgress(quests.get(i).progress);


            Button button = (Button) includeView.findViewById(R.id.addProgressButton);
            int finalI = i;
            button.setOnClickListener(v -> {

                //update the quest-text and the quest-progress when the button is clicked
                List<Quest> quests1 = questDao.getAll();
                questDao.addProgress(quests1.get(finalI).id);
                quests1 = questDao.getAll();
                String fullText1 = quests1.get(finalI).name + " (" + quests1.get(finalI).reward + " " + skillDao.getNameFromId(quests1.get(finalI).skillId) + "-Exp, " + quests1.get(finalI).progress + "/" + quests1.get(finalI).max + ")";
                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(fullText1);
                int startIndex1 = quests1.get(finalI).name.length() + 2;
                int endIndex1 = fullText1.length() - 1;
                ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(skillDao.getColorFromId(quests1.get(finalI).skillId));
                spannableStringBuilder1.setSpan(colorSpan1, startIndex1, endIndex1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                questName.setText(spannableStringBuilder1);
                bar.setProgress(quests1.get(finalI).progress);

                if(quests1.get(finalI).progress >= quests1.get(finalI).max){
                    updateTotalLevel(quests1.get(finalI).reward);

                    Toast.makeText(getContext(), "You completed a quest and gained " + quests1.get(finalI).reward + " " + skillDao.getNameFromId(quests1.get(finalI).skillId) + "-experience!", Toast.LENGTH_LONG).show();

                    skillDao.addXP(quests1.get(finalI).reward, quests1.get(finalI).skillId);




                    questDao.deleteQuest(quests1.get(finalI).id);

                    generateQuests();
                    generateSkills();

                }
            });

            if (quests.get(i).max == 1){
                button.setText("✓");
                button.setTextColor(Color.GREEN);
            }





            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            if(i == 0) {
                params.topToBottom = R.id.questTitle;
            }else {
                params.topToBottom = R.layout.quest_layout + quests.get(i-1).id;
            }
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.topMargin = 16;
            params.bottomMargin = 16;
            includeView.setLayoutParams(params);
            includeView.setId((R.layout.quest_layout) + quests.get(i).id);
            questLayout.addView(includeView);
        }


    }

    @SuppressLint("SetTextI18n")
    void updateTotalLevel(int gainedExp){
        int progress = binding.totalLevelBar.getProgress() + gainedExp;

        binding.totalLevelBar.setProgress(progress);
        Log.d("progress", "progress: " + binding.totalLevelBar.getProgress() + "; maxNumber: " + binding.totalLevelBar.getMax());


        while(progress >= binding.totalLevelBar.getMax()) {
            Log.e("test", "increased a level!!!!!!!");
            editor.putInt("level", sharedPreferences.getInt("level", 0) + 1);
            editor.apply();
            binding.totalLevelText.setText("LVL. " + sharedPreferences.getInt("level", 0));
            binding.totalLevelBar.setProgress(progress - getMaxExperience(sharedPreferences.getInt("level", 0)));
            binding.totalLevelBar.setMax(getMaxExperience(sharedPreferences.getInt("level", 0)));
        }



        editor.putInt("experience", binding.totalLevelBar.getProgress());
        editor.apply();

    }

    Integer getMaxExperience(int currentLevel){

        return currentLevel*currentLevel/4 +10;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}