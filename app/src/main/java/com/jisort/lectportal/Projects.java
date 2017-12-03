package com.jisort.lectportal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.orhanobut.dialogplus.DialogPlus;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import am.appwise.components.ni.NoInternetDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.jisort.lectportal.Lec_portal_home.lec_number;
import static com.jisort.lectportal.LoginActivity.data;
import static com.jisort.lectportal.MainActivity.MY_PREFS_NAME;
import static com.jisort.lectportal.MainActivity.chat_data;
import static com.jisort.lectportal.URLs.Main_url;

/**
 * Created by jjsikini on 12/2/17.
 */

public class Projects  extends Fragment
{RecyclerView recyclerView ;

public static String current_student;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.projects, container, false);


        try {
            Context mContext = getActivity();
            RelativeLayout mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativelayout);
            //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(layoutManager);
            // grid.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new RecyclerAdapter();
            recyclerView.setAdapter(adapter);
            //grid.setAdapter((ListAdapter) adapter);
        } catch (Exception ex) {
             Toast.makeText(getActivity(), String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }


        return rootView;


    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


        private String[] titles =
                {

                        "Add research area",
                        "View all proposals",
                        "Projects am supervising"
                };



        private int[] images = {
                R.drawable.addresearcharea,
                R.drawable.viewallproposals,
                R.drawable.projectsamsupervising


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
                        FragmentManager fm;
                        FragmentTransaction ft;
                        switch (position)
                        {
                            case 0:

                                fragment =  new AddReasearchArea();
                                fm = getFragmentManager();
                                ft = fm.beginTransaction();
                                ft.replace(R.id.fragment_place_place, fragment);
                                ft.addToBackStack("addresearcharea");
                                ft.commit();
                                break;
                            case 1:
                                fragment =  new ViewAllProposals();
                                fm = getFragmentManager();
                                ft = fm.beginTransaction();
                                ft.replace(R.id.fragment_place_place, fragment);
                                ft.addToBackStack("viewallproposals");
                                ft.commit();
                                break;

                            case 2:
                                fragment =  new ProjectsAmSupervising();
                                fm = getFragmentManager();
                                ft = fm.beginTransaction();
                                ft.replace(R.id.fragment_place_place, fragment);
                                ft.addToBackStack("ProjectsAmSupervising");
                                ft.commit();
                                break;
                        }



                    }
                });
            }
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.projects_menu, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


     public static class AddReasearchArea  extends Fragment
    {
         BootstrapButton button;
        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.addresearcharea, container, false);
             button = (BootstrapButton)rootView.findViewById(R.id.button);
            final Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
            final EditText project_name = (EditText)rootView.findViewById(R.id.editText2);
          final   EditText description =(EditText)rootView.findViewById(R.id.editText);
            final ArrayList<String> dept = new ArrayList<String>();
            JSONObject json = null;
            try {
                json = new JSONObject(data);
                JSONArray array = json.getJSONArray("result");
                JSONObject c = array.getJSONObject(0);
                String departments =c.getString("departments");
                Log.d("departments", departments);
                JSONArray jsonArray = new JSONArray(departments);
                Log.d("departmentslenght", String.valueOf(jsonArray.length()));

                for (int i =0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String department_name = jsonObject.getString("department");
                    dept.add(department_name);
                }

            } catch (JSONException e) {
                Log.d("JSONException", String.valueOf(e));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dept);
            spinner.setAdapter(adapter);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(project_name.getText().toString().length()<1||description.getText().toString().length()<1)
                    {
                        project_name.setError("Field is required");
                        description.setError("Field is required!");
                    }
                    else
                    {
                        project_name.setError(null);
                        description.setError(null);


                        String p_name= project_name.getText().toString().trim();
                        String desc= description.getText().toString().trim();
                        String dpt= spinner.getSelectedItem().toString();
                        register_research_area(p_name, desc, dpt);
                        button.setClickable(false);

                    }
                }
            });

            return rootView;


        }
        public void register_research_area(final String name,final String dept,final String desc)
        {
            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;
                ProgressDialog pDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog.setMessage("Adding research area...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> paramms = new HashMap<>();
                    paramms.put("to", "add_research_area");
                    paramms.put("name", name);
                    paramms.put("dep", dept);
                    paramms.put("desc", desc);
                    String s = rh.sendPostRequest(Main_url+"postreguests.php", paramms);
                    return s;

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pDialog.dismiss();
                    button.setClickable(true);
                    if(s.equals("1"))
                    {
                     new AlertDialog.Builder(getActivity())
                             .setMessage("Research area addition success!")
                             .setPositiveButton("Okay", null)
                             .show();
                    }

                }


            }
            GetJSON jj = new GetJSON();
            jj.execute();

        }

    }

    public static class ViewAllProposals  extends Fragment
    {
        String student_reg_numnber;
        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewallproposals, container, false);

            ListView listview = (ListView)rootView.findViewById(R.id.listview);
            JSONObject jsonObject = null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();



            try {

                jsonObject = new JSONObject(data);
                JSONArray result = jsonObject.getJSONArray("result");
                JSONObject c = result.getJSONObject(0);
                String student_proposals =c.getString("student_proposals");
                result = new JSONArray(student_proposals);

                for (int i = 0; i < result.length(); i++)
                {  JSONObject jo = result.getJSONObject(i);

                    JSONObject  jjj =result.getJSONObject(i);
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("reg_no",jjj.getString("reg_no") );
                    employees.put("student_name",jjj.getString("student_name") );
                    employees.put("research_area",jjj.getString("research_area") );
                    employees.put("title",jjj.getString("title") );
                    employees.put("description",jjj.getString("description") );
                    list.add(employees);


                }

                ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.projects_am_supervising,
                        new String[]{"reg_no", "research_area", "title" ,"description"}, new int[]{R.id.textView3,
                        R.id.textView7, R.id.textView8, R.id.textView5});
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView reg_number= (TextView)view.findViewById(R.id.textView3);
                         student_reg_numnber= reg_number.getText().toString().trim();

                        new AlertDialog.Builder(getActivity()).setMessage("Are you sure you wan to select " +student_reg_numnber+ "?")
                                .setTitle("Please confirm").setPositiveButton("Select", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                book_student(lec_number,student_reg_numnber);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                                .show();




                    }
                });

            } catch (JSONException e) {


                e.printStackTrace();
            }


            return rootView;


        }
        public void book_student(final String lec_number, final String student_id)
        {

            class GetJSON extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;
                ProgressDialog pDialog = new ProgressDialog(getActivity());

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog.setMessage("Adding research area...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                @Override
                protected String doInBackground(Void... params)
                {
                    RequestHandler rh = new RequestHandler();
                    HashMap<String, String> paramms = new HashMap<>();
                    paramms.put("to", "pick_student");
                    paramms.put("regno", student_id);
                    paramms.put("lec_number", lec_number);
                    String s = rh.sendPostRequest(Main_url+"postreguests.php", paramms);
                    return s;

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pDialog.dismiss();
Log.d("result",s);
                    if(s.equals("1"))
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Message")
                                .setMessage("Research area addition success!")
                                .setPositiveButton("Okay", null)
                                .setNegativeButton("Cancel", null)
                                .show();
                    }
                    else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Message")
                                .setMessage("Operation failed. Please contact the administrator!")
                                .setPositiveButton("Okay", null)
                                .setNegativeButton("Cancel", null)
                                .show();
                    }

                }


            }
            GetJSON jj = new GetJSON();
            jj.execute();

        }

    }



    public static class ProjectsAmSupervising  extends Fragment
    {

        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewallproposals, container, false);

            //Toast.makeText(getActivity(), "ProjectsAmSupervising", Toast.LENGTH_SHORT).show();

            ListView listview = (ListView)rootView.findViewById(R.id.listview);
            JSONObject jsonObject = null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {
                jsonObject = new JSONObject(data);
                JSONArray result = jsonObject.getJSONArray("result");
                JSONObject c = result.getJSONObject(0);
                String student_proposals =c.getString("student_proposals");
                //Toast.makeText(getActivity(), "student_proposals"+student_proposals, Toast.LENGTH_SHORT).show();
                result = new JSONArray(student_proposals);

                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject jo = result.getJSONObject(i);
                    JSONObject  jjj =result.getJSONObject(i);
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("reg_no",jjj.getString("reg_no") );
                    employees.put("student_name",jjj.getString("student_name") );
                    employees.put("research_area",jjj.getString("research_area") );
                    employees.put("title",jjj.getString("title") );
                    employees.put("description",jjj.getString("description") );
                    list.add(employees);

                }
                Log.d("ProjectsAmSupervising", String.valueOf(list));
                ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.proposalsamsupervising,
                        new String[]{"reg_no", "research_area", "title" ,"description"}, new int[]{R.id.textView3,
                        R.id.textView7, R.id.textView8, R.id.textView5});
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        TextView reg_number= (TextView)view.findViewById(R.id.textView3);
                        current_student=reg_number.getText().toString().trim();
                        FetchStudent_data(current_student);
                    }
                });

            } catch (JSONException e) {


                e.printStackTrace();
            }


            return rootView;


        }

public void FetchStudent_data(final String student_id)
{
    class GetJSON extends AsyncTask<Void, Void, String> {

        ProgressDialog loading;
        ProgressDialog pDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Fetching student data..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params)
        {
            RequestHandler rh = new RequestHandler();
            HashMap<String, String> paramms = new HashMap<>();
            paramms.put("reg", student_id);
            String s = rh.sendPostRequest(Main_url+"student_dataa.php", paramms);
            return s;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            pDialog.dismiss();
            Log.d("result",s);

            //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                studentAllData=s;
                Bundle bundle = new Bundle();
                bundle.putString("data", s);
                Fragment   fragment =  new ProjectsAmSupervisingMenu();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_place_place, fragment);
                ft.addToBackStack("studentsapsupervising.");
                ft.commit();






        }


    }
    GetJSON jj = new GetJSON();
    jj.execute();



}


    }

    public static  String studentTimelineData, studentDocumentsData, studentChatData, studentAllData;

    public static class ProjectsAmSupervisingMenu  extends Fragment
    {

        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.projects, container, false);

            Bundle bundle = this.getArguments();
            String data  = bundle.getString("data", "null");
            JSONObject json = null;


            try
            {
                json = new JSONObject(data);
                JSONArray array = json.getJSONArray("result");
                JSONObject c = array.getJSONObject(0);
                String student_docs =c.getString("student_docs");
                studentDocumentsData=student_docs;
                String time_line =c.getString("time_line");
                studentTimelineData=time_line;

            } catch (JSONException e) {
                e.printStackTrace();
            }



            try {
                Context mContext = getActivity();
                RelativeLayout mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativelayout);
                //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
                recyclerView.setLayoutManager(layoutManager);
                // grid.setLayoutManager(layoutManager);
                RecyclerView.Adapter adapter = new RecyclerAdapter();
                recyclerView.setAdapter(adapter);
                //grid.setAdapter((ListAdapter) adapter);
            } catch (Exception ex) {
                 Toast.makeText(getActivity(), String.valueOf(ex), Toast.LENGTH_SHORT).show();
            }
            return rootView;


        }

        class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


            private String[] titles =
                    {

                            "Student timeline",
                            "Proposal documents",
                            "View student source code",
                            "Message student"


                    };



            private int[] images = {
                    R.drawable.studenttimeline,
                    R.drawable.studentdocumets,
                    R.drawable.studentcode,
                    R.drawable.textstudent

            };


            class ViewHolder extends RecyclerView.ViewHolder
            {

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
                            FragmentManager fm;
                            FragmentTransaction ft;

                            switch (position)
                            {
                                case 0:

                                    fragment =  new StudentsTimeline();
                                    fm = getFragmentManager();
                                    ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_place_place, fragment);
                                    ft.addToBackStack("studentstimeline.");
                                    ft.commit();
                                    break;
                                case 1:
                                    fragment =  new StudentsDocuments();
                                    fm = getFragmentManager();
                                    ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_place_place, fragment);
                                    ft.addToBackStack("studentstimeline.");
                                    ft.commit();
                                    break;
                                case 2:
                                    Toast.makeText(getActivity(), "Only supported in web version!", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    //StudentsChat
                                    fragment =  new StudentsChat();
                                    fm = getFragmentManager();
                                    ft = fm.beginTransaction();
                                    ft.replace(R.id.fragment_place_place, fragment);
                                    ft.addToBackStack("StudentsChat.");
                                    ft.commit();
                                    break;

                            }


                        }
                    });
                }
            }



            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.projects_menu, viewGroup, false);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int i) {
                viewHolder.itemTitle.setText(titles[i]);
                viewHolder.itemImage.setImageResource(images[i]);
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }
        }


    }



    public static class StudentsTimeline  extends Fragment
    {
        public RecyclerView recyclerView;
        public RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;
        public List<TimeLineData> listitems;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_menu, container, false);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);//done here
            layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);

            listitems = new ArrayList<>();
                Log.d("studentTimelineData", studentTimelineData);
            try {
                JSONArray jsonArray = new JSONArray(studentTimelineData);
                for(int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String date= jsonObject.getString("date");
                    String event_name = jsonObject.getString("event_name");
                    String brief_description = jsonObject.getString("brief_description");
                    String lec_id = jsonObject.getString("lec_id");
                    String student_id = jsonObject.getString("student_id");
                    String sender = jsonObject.getString("sender");
                    TimeLineData timeLineData = new TimeLineData(date,event_name,brief_description,student_id,sender, lec_id);
                    listitems.add(timeLineData);

                }
                adapter = new TimeLineAdapter(listitems, getActivity());
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return rootView;


        }




    }




    public static class StudentsDocuments  extends Fragment
    {

        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.studentdocuments, container, false);
            ListView listview= rootView.findViewById(R.id.listview);

            Log.d("student_documents", studentDocumentsData);
            JSONObject jsonObject = null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try {

                JSONArray result;
                result = new JSONArray(studentDocumentsData);
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject  jjj =result.getJSONObject(i);
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("timestamp",jjj.getString("timestamp") );
                    employees.put("type",jjj.getString("type") );
                    employees.put("doclink",jjj.getString("doclink") );
                    employees.put("student_id",jjj.getString("student_id") );
                    employees.put("docname", jjj.getString("docname"));
                    employees.put("brief_description",jjj.getString("brief_description") );
                    list.add(employees);
                }
                ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.studentdocumentslistview,
                        new String[]{"type", "timestamp"}, new int[]{R.id.textView12,
                        R.id.textView15});
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);


                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                .setMessage("Do you want to download student document or upload modified document!");

                        builder.addButton("Download", Color.parseColor("#FFFFFF"),
                                Color.parseColor("#5cb85c"),
                                CFAlertDialog.CFAlertActionStyle.POSITIVE,
                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                download_documents(map.get("doclink"),map.get("docname"));

                            }
                        });
                        builder.addButton("Upload", Color.parseColor("#FFFFFF"),
                                Color.parseColor("#5bc0de"),
                                CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        uploadFile(map.get("docname"));
                                    }
                                });
                        builder.addButton("Cancel", Color.parseColor("#FFFFFF"),
                                Color.parseColor("#d9534f"),
                                CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.show();
                    }
                });

            } catch (JSONException e)
            {
               Log.d("JsonError", String.valueOf(e));
            }

            return rootView;


        }

        private void download_documents(final String url_data,final String file_name)
        {

            class TestAsync extends AsyncTask<Void, Integer, String>
            {
                ProgressDialog progressDialog;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setMessage("Downloading document..");
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    progressDialog.dismiss();

                    if(s.equals("true"))
                    {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_VIEW);
                        String type = "application/msword";
                        String file_url="/LecPortalDocuments/"+file_name;
                        String file_name = Environment.getExternalStorageDirectory().toString()+ file_url;
                        File file = new File(file_name);
                        intent.setDataAndType(Uri.fromFile(file), type);
                        startActivity(intent);
                    }
                    super.onPostExecute(s);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    String success="true";

                    try
                    {
                        URL url = new URL(url_data);
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setRequestMethod("GET");
                        c.setDoOutput(true);
                        c.connect();
                        String PATH = Environment.getExternalStorageDirectory().toString()+ "/LecPortalDocuments/";
                        Log.v("LOG_TAG", "PATH: " + PATH);
                        File file = new File(PATH);
                        file.mkdirs();
                        File outputFile = new File(file, file_name);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        InputStream is = c.getInputStream();
                        byte[] buffer = new byte[4096];
                        int len1 = 0;
                        while ((len1 = is.read(buffer)) != -1)
                        {
                            fos.write(buffer, 0, len1);
                        }
                        fos.close();
                        is.close();


                    }
                    catch (IOException e)
                    {
                        Log.d("IOEXCeption", String.valueOf(e));
                        success="false";
                    }

                    return success;
                }
            }
            new TestAsync().execute();

        }

        public void uploadFile(final String file_name)
        {

            String PATH = Environment.getExternalStorageDirectory().toString()+ "/LecPortalDocuments/";
            String full_path=PATH+file_name;
            Log.v("LOG_TAG", "PATH: " + full_path);
            File file = new File(full_path);

            if(file==null)
            {
                Toast.makeText(getActivity(), "Please download and edit student document first!", Toast.LENGTH_SHORT).show();
            }
            else {
                try
                {
                    String uploadId = UUID.randomUUID().toString();
                    new MultipartUploadRequest(getActivity(), uploadId, Main_url+"docupload.php")
                            .addFileToUpload(full_path, "resume")
                            .addParameter("resume", file_name)
                            .addParameter("reg_no", current_student)
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload();
                } catch (Exception exc)
                {
                    Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public static class StudentsChat  extends Fragment
    {

        ChatView mChatView;
        RecyclerView recyclerView ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.lecstudentchat, container, false);
            mChatView =rootView.findViewById(R.id.chat_view);


            SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String chatdata = prefs.getString("chat_data", null);
            //Toast.makeText(getActivity(), chat_data, Toast.LENGTH_SHORT).show();


            JSONObject json = null;
            try {

                Log.d("chat_data", chatdata);

                JSONArray jsonArray = new JSONArray(chatdata);
                for(int i=0;  i<jsonArray.length(); i++)
                {
                    JSONObject  jsonObject = jsonArray.getJSONObject(i);
                    String sender= jsonObject.getString("sender");
                    String receiver= jsonObject.getString("receiver");
                    String receiver_type= jsonObject.getString("receiver_type");
                    String time= jsonObject.getString("time");
                    String msg= jsonObject.getString("msg");

                                if(sender.equals(lec_number))
                                {
                                    Bitmap myIcon = null;
                                    User me = new User(1, sender, myIcon);
                                    Message message = new Message.Builder()
                                            .setUser(me)
                                            .setRightMessage(true)
                                            .setMessageText(msg)
                                            .hideIcon(true)
                                            .build();
                                    mChatView.send(message);
                                }
                                else
                                {

                                    Bitmap yourIcon = null;
                                    User you = new User(2,receiver , yourIcon);
                                    Message receivedMessage = new Message.Builder()
                                            .setUser(you)
                                            .setRightMessage(false)
                                            .setMessageText(msg)
                                            .hideIcon(true)
                                            .build();
                                    mChatView.receive(receivedMessage);
                                }




                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

            mChatView.setRightBubbleColor(ContextCompat.getColor(getActivity(), R.color.green));
            mChatView.setLeftBubbleColor(ContextCompat.getColor(getActivity(), R.color.blue));
            mChatView.setSendButtonColor(ContextCompat.getColor(getActivity(), R.color.cyan900));
            mChatView.setSendIcon(R.drawable.ic_action_send);
            mChatView.setRightMessageTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mChatView.setLeftMessageTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            mChatView.setUsernameTextColor(R.color.white);
            mChatView.setSendTimeTextColor(R.color.white);
            mChatView.setDateSeparatorColor(R.color.white);
            mChatView.setInputTextHint("new message...");
            mChatView.setMessageMarginTop(5);
            mChatView.setMessageMarginBottom(5);
            mChatView.setOnClickSendButtonListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String sms= mChatView.getInputText();
                    mChatView.setInputText("");
                    Bitmap myIcon = null;
                    User me = new User(1, lec_number, myIcon);
                    Message message = new Message.Builder()
                            .setUser(me)
                            .setRightMessage(true)
                            .setMessageText(sms)
                            .hideIcon(true)
                            .build();
                    mChatView.send(message);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                    ChatMessage send_msg=new ChatMessage(lec_number, current_student,timeStamp, "lec",sms);
                    send_data(send_msg, rootView);

                }

            });

            return rootView;


        }

        public void send_data(final ChatMessage chatMessage , final ViewGroup rootView)
        {
            class AddEmployee extends AsyncTask<Void,Void,String>
            {
                ProgressDialog progressDialog;
                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();
                }
                @Override
                protected String doInBackground(Void... v)
                {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("to","chat_update");
                    params.put("sender",chatMessage.getSender());
                    params.put("receiver",chatMessage.getReceiver());
                    params.put("time",chatMessage.getTime());
                    params.put("conversation_id","conversation_id");
                    params.put("msg",chatMessage.getMsg());
                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Main_url+"postreguests.php", params);
                    return res;
                }

                @Override
                protected void onPostExecute(final String s)
                {
                    Log.d("chat_data", s);
                    JSONObject json = null;
                    try
                    {
                        json = new JSONObject(s);
                        JSONArray array = json.getJSONArray("result");
                        JSONObject c = array.getJSONObject(0);
                        String success =c.getString("success");
                        if(success.equals("true"))
                        {


                            mChatView =rootView.findViewById(R.id.chat_view);
                            Toast.makeText(getActivity(), "Message sent:)", Toast.LENGTH_SHORT).show();
                            String chatdata= c.getString("chat_data");
                            Log.d("chat_data_after", chatdata);
                           // Toast.makeText(getActivity(), chatdata, Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("chat_data", chatdata);
                            editor.commit();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Sending failed!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    }
            }
            AddEmployee ae = new AddEmployee();
            ae.execute();

        }




    }
}
