package com.gaurav.remindersapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ReminderHolder>{
    private static final String TAG = "ChatRoomsAdapter";

    private List<Reminder> reminders;
    private String defaultTitle;
    private ItemListener listener;


    public RemindersAdapter(ItemListener listener, List<Reminder> reminders) {
        this.reminders = reminders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //responsible for inflating view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder, parent, false);

        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {

        final Reminder reminder = reminders.get(position);

        holder.reminder_description.setText(reminder.getDescription());

        holder.reminder_time.setText(Formatter.getTime(reminder.getTime()));
        holder.reminder_date.setText(Formatter.getDate(reminder.getTime()));

        holder.reminder_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInteraction(RemindersActivity.ITEM_DELETED, reminder);
            }
        });

        holder.reminder_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInteraction(RemindersActivity.ITEM_CLICKED, reminder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }


    public class ReminderHolder extends RecyclerView.ViewHolder{
        ImageView reminder_delete;
        TextView reminder_date;
        TextView reminder_time;
        TextView reminder_description;
        ConstraintLayout reminder_layout;

        public ReminderHolder(View itemView){
            super(itemView);
            reminder_delete = itemView.findViewById(R.id.reminder_delete);
            reminder_date = itemView.findViewById(R.id.reminder_date);
            reminder_time = itemView.findViewById(R.id.reminder_time);
            reminder_description = itemView.findViewById(R.id.reminder_description);
            reminder_layout = itemView.findViewById(R.id.reminder_layout);
        }

    }

   public interface ItemListener{
       void onInteraction(int type, Reminder arg);
   }

}
