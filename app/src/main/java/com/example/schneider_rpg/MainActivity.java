package com.example.schneider_rpg;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.schneider_rpg.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public SkillAppDatabase sdb;
    public static SkillDao skillDao;

    public QuestAppDatabase qdb;
    public static QuestDao questDao;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    private BottomNavigationView bottomNavigationView;
    private FirstFragment firstFragment;
    private AddFragment addFragment;
    private Fragment activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Log.d("important info", "total xp: " + sharedPreferences.getInt("experience", 0));

        sdb = Room.databaseBuilder(getApplicationContext(),
                        SkillAppDatabase.class, "skill")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        skillDao = sdb.skillDao();



        qdb = Room.databaseBuilder(getApplicationContext(),
                        QuestAppDatabase.class, "quest")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        questDao = qdb.questDao();



//        Skill skill = new Skill();
//        skill.experience = 0;
//        skill.name = "Coding";
//        skill.color = Color.rgb(250, 10, 10);
//        skill.level = 0;
//        skillDao.insert(skill);
//
//        skill = new Skill();
//        skill.experience = 0;
//        skill.name = "Dating";
//        skill.color = Color.rgb(250, 250, 10);
//        skill.level = 0;
//        skillDao.insert(skill);
//
//        skill = new Skill();
//        skill.experience = 0;
//        skill.name = "Sport";
//        skill.color = Color.rgb(10, 250, 245);
//        skill.level = 0;
//        skillDao.insert(skill);
//
//
//
//
//        Quest quest = new Quest();
//        quest.name = "Plane ein Date mit Leilani!";
//        quest.max = 1;
//        quest.progress = 0;
//        quest.reward = 25;
//        quest.skillId = skillDao.getIdFromName("Dating");
//        questDao.insert(quest);
//
//        quest = new Quest();
//        quest.name = "Besuche das Gym und mache ein Workout!";
//        quest.max = 5;
//        quest.progress = 0;
//        quest.reward = 500;
//        quest.skillId = skillDao.getIdFromName("Sport");
//        questDao.insert(quest);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        bottomNavigationView = binding.bottomNavigationView;

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_add_fragment) {
                    navController.navigate(R.id.action_global_navigation_add);
                    return true;
                } else if (item.getItemId() == R.id.navigation_fragment1) {
                    navController.navigate(R.id.action_global_navigation_first);
                    return true;
                }
                return false;
            }
        });








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void switchFragment(Fragment fragment) {
        if (activeFragment == fragment)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.hide(activeFragment).show(fragment);
        } else {
            fragmentTransaction.hide(activeFragment).add(R.id.include, fragment);
        }
        fragmentTransaction.commit();

        activeFragment = fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up addHourButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}