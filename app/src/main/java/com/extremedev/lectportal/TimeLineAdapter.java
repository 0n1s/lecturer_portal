package com.extremedev.lectportal;
/**
 * Created by sikinijjs on 10/1/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder>
{

    private List<TimeLineData> listItems;
    SharedPreferences sharedPreferences;
    String myPrefs="ALARMS";

    public TimeLineAdapter(List<TimeLineData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent,false);
        return new ViewHolder(v);
    }
    String day;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {

        final TimeLineData dataClass =listItems.get(position);


        holder.unit_name.setText(dataClass.getEvent_name());
        holder.tutor.setText(dataClass.getBrief_description());
        holder.startTime.setText(dataClass.getDate());
        holder.room_number.setText("Source:"+dataClass.getSender());





    }

    @Override
    public int getItemCount() {
        return  listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
TextView startTime, endtime, tutor, room_number,unit_name;
ImageView alarm_image;
LinearLayout linear_layout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            startTime = (TextView)itemView.findViewById(R.id.startTime);
            endtime=itemView.findViewById(R.id.endTime);
            tutor =itemView.findViewById(R.id.prof);
            alarm_image=(ImageView)itemView.findViewById(R.id.alarm_image);
            unit_name=itemView.findViewById(R.id.name);
            room_number=itemView.findViewById(R.id.place);
            linear_layout=itemView.findViewById(R.id.linear_layout);


        }
    }
    {

    }



}