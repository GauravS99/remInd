package com.gaurav.remindersapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class RemindersActivity extends FileActivity implements RemindersAdapter.ItemListener {

    private RemindersAdapter adapter;
    private SwipeRefreshLayout swipe;
    private ImageView no_reminders_img;
    private Toolbar toolbar;

    public static int ITEM_CLICKED = 0;
    public static int ITEM_DELETED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        no_reminders_img = findViewById(R.id.no_reminders_img);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView chat_rooms_recycler = findViewById(R.id.reminder_recycler);

        loadFromFile();

        if(reminders == null || reminders.size() == 0){
            adapter = new RemindersAdapter(this, new ArrayList<Reminder>());
            getSupportActionBar().setTitle("");
        }
        else {
            adapter = new RemindersAdapter(this, reminders);
            getSupportActionBar().setTitle("Your Reminders");
        }

        checkEmpty();

        chat_rooms_recycler.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        chat_rooms_recycler.setLayoutManager(llm);

        swipe =  findViewById(R.id.swipe_container);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp = new Intent(getApplicationContext(), ReminderEditActivity.class);
                startActivity(tmp);
            }
        });

        loadItems();
    }

    public void loadItems(){

    }

    public void checkEmpty(){
        if(reminders == null || reminders.size() == 0){
            Log.w("ZWX", "checkEmpty: ");
            no_reminders_img.setVisibility(View.VISIBLE);
        }
    }

    //prevents any backwards moving
    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item:   //this item has your app icon
                //todo add a user settings page
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInteraction(int type, Reminder arg) {
        int index = -1;

        for(int i = 0; i < reminders.size(); i++){
            if(arg == reminders.get(i)){ //this is the item clicked on
                index = i;
            }
        }

        if(type == ITEM_DELETED){
            reminders.remove(index);
            saveToFile();
            adapter.notifyDataSetChanged();
            checkEmpty();
        }
        //todo handle item clicks, to edit them
    }


}
