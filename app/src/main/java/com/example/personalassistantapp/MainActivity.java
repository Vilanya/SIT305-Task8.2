package com.example.personalassistantapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalassistantapp.data.DatabaseHelper;
import com.example.personalassistantapp.model.Event;
import com.example.personalassistantapp.model.EventAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button createEvent;
    ImageButton showAll,completed;
    DatabaseHelper db;
    EventAdapter eventAdapter;
    RecyclerView recycler_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        createEvent = findViewById(R.id.create_event);
        showAll = findViewById(R.id.show_all);
        completed = findViewById(R.id.completed);


        recycler_view = findViewById(R.id.recyclerView);
        createEvent.setOnClickListener(this);
        showAll.setOnClickListener(this);
        completed.setOnClickListener(this);

        List<Event> list = db.fetchUpcoming();
        eventAdapter = new EventAdapter(getApplicationContext(), list);
        recycler_view.setAdapter(eventAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

    }

    @Override
    public void onClick(View v) {
        if (v ==createEvent){
            Intent intent = new Intent(getApplicationContext(),CreateEvent.class);
            startActivity(intent);
        }
        else if (v == showAll){
            Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
            intent.putExtra("code", "1");
            startActivity(intent);
        }
        else if (v == completed){
            Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
            intent.putExtra("code", "2");
            startActivity(intent);
        }

    }
}
