package com.example.personalassistantapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.personalassistantapp.R;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    Context context;
    List<Event> events_list;

    public EventAdapter(Context context, List<Event> entityClasses) {
        this.context = context;
        this.events_list = entityClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.eventTitle.setText(events_list.get(position).getName());
        holder.dateTime.setText(events_list.get(position).getDate() + " " + events_list.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return events_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventTitle, dateTime;
        private LinearLayout toplayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            dateTime = (TextView) itemView.findViewById(R.id.date_time);
            toplayout = (LinearLayout) itemView.findViewById(R.id.toplayout);
        }
    }
}
