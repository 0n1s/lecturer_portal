package com.jisort.lectportal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.jisort.lectportal.LoginActivity.data;

/**
 * Created by jjsikini on 11/24/17.
 */
public class Lec_portal_home extends Fragment
{

    public static String lec_number;
    public static String chat_data;
//    public static String lec_number;

    public String[] details = new String[4];
    RecyclerView recyclerView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_menu, container, false);

        JSONObject json = null;
        try {

            json = new JSONObject(data);
            JSONArray array = json.getJSONArray("result");
            JSONObject c = array.getJSONObject(0);
            String addition_data =c.getString("addition_data");


            Log.d("additional_data", addition_data);

            JSONArray jsonArray = new JSONArray(addition_data);

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String my_student_count = jsonObject.getString("my_student_count");
            Log.d("mystudentcount", my_student_count);
            details[0]=my_student_count;

            String remaining_studens = jsonObject.getString("remaining_studens");
            Log.d("remaining_studens", remaining_studens);
            details[1]=remaining_studens;

            String allstudents = jsonObject.getString("allstudents");
            Log.d("allstudents", allstudents);
            details[2]=allstudents;

            String picked = jsonObject.getString("picked");
            Log.d("picked", picked);
            details[3]=picked;

            lec_number= jsonObject.getString("lec_number");






        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {
            Context mContext = getActivity();
            RelativeLayout mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativelayout);
            //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
            recyclerView.setLayoutManager(layoutManager);
            // grid.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new RecyclerAdapter();
            recyclerView.setAdapter(adapter);
            //grid.setAdapter((ListAdapter) adapter);
        } catch (Exception ex) {
            //    Toast.makeText(mContext, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }


        return rootView;


    }




    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        SharedPreferences sharedpreferences;
        private String[] titles =
                {

                        "ALL STUDENTS",
                        "THIS YEAR'S STUDENTS",
                        "PICKED STUDENTS",
                        "REMAINING STUDENTS"


                };



        private int[] images = {
                R.drawable.studenticonmenu,
                R.drawable.thisyear,
                R.drawable.pickedstudents,
                R.drawable.remainingsturents

        };


        class ViewHolder extends RecyclerView.ViewHolder {

            public int currentItem;
            public ImageView itemImage;
            public TextView itemTitle;
            public TextView itemDetail;
            public ViewHolder(View itemView) {
                super(itemView);

                itemImage = (ImageView) itemView.findViewById(R.id.item_image);
                itemTitle = (TextView) itemView.findViewById(R.id.item_title);
                itemDetail = (TextView)itemView.findViewById(R.id.textView2);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Fragment  fragment;

                        switch (position) {


                        }


                    }
                });
            }
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.mainactivitycardview, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }



}
