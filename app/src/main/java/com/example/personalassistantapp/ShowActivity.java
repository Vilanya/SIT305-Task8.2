package com.example.personalassistantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalassistantapp.data.DatabaseHelper;
import com.example.personalassistantapp.model.Event;
import com.example.personalassistantapp.model.EventAdapter;

import java.util.List;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper db;
    EventAdapter eventAdapter;
    RecyclerView recycler_view;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        db = new DatabaseHelper(this);
        back = (Button)findViewById(R.id.mainButton);
        recycler_view = findViewById(R.id.recyclerViewShow);
        String code = getIntent().getStringExtra("code");
        TextView label = findViewById(R.id.label);
        back.setOnClickListener(this);

        if (code.equals("1")){
            List<Event> list = db.fetchEvents();
            eventAdapter = new EventAdapter(getApplicationContext(), list);
            recycler_view.setAdapter(eventAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(layoutManager);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            label.setText("All Events");
        }
        else if (code.equals("2")){
            List<Event> list = db.fetchCompleted();
            eventAdapter = new EventAdapter(getApplicationContext(), list);
            recycler_view.setAdapter(eventAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(layoutManager);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            label.setText("Completed");
        }

//        else{
////            onClickMain();
//        }
    }
    public void onClick(View v) {
            if (v ==back) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

    }



}
