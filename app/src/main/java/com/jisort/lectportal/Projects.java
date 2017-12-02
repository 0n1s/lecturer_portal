package com.jisort.lectportal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jisort.lectportal.Lec_portal_home.lec_number;
import static com.jisort.lectportal.LoginActivity.data;

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
                                ft.addToBackStack("projectsamsupervising");
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
                    String s = rh.sendPostRequest(URLs.Main_url+"postreguests.php", paramms);
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
                    String s = rh.sendPostRequest(URLs.Main_url+"postreguests.php", paramms);
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
                        Fragment   fragment =  new ProjectsAmSupervisingMenu();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place_place, fragment);
                        ft.addToBackStack("studentsapsupervising.");
                        ft.commit();


                    }
                });

            } catch (JSONException e) {


                e.printStackTrace();
            }


            return rootView;


        }

public void FetchStudent_data(final String student_id)
{



}


    }

    public static class ProjectsAmSupervisingMenu  extends Fragment
    {

        RecyclerView recyclerView ;
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

                            switch (position)
                            {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    Toast.makeText(getActivity(), "Only supported in web version!", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
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
}
