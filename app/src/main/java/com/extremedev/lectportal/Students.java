package com.extremedev.lectportal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.extremedev.lectportal.LoginActivity.data;

/**
 * Created by jjsikini on 12/2/17.
 */



public class Students  extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.students, container, false);

        ListView listview = (ListView)rootView.findViewById(R.id.listview);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        JSONObject json;
        try {



            json = new JSONObject(data);
            JSONArray array = json.getJSONArray("result");
            JSONObject c = array.getJSONObject(0);
            String students_list =c.getString("students_list");
            Log.d("students_list", students_list);
            JSONArray jsonArray = new JSONArray(students_list);
            Log.d("departmentslenght", String.valueOf(jsonArray.length()));
            for (int i =0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String student_name = jsonObject.getString("fname");
                String department = jsonObject.getString("department");
                String regno = jsonObject.getString("regno");
                String phone = jsonObject.getString("phone");
                String email = jsonObject.getString("email");
                String course = jsonObject.getString("course");
                String image = jsonObject.getString("image");

                HashMap<String, String> employees = new HashMap<>();
                employees.put("student_name",student_name );
                employees.put("department",department );
                employees.put("regno",regno);
                employees.put("image",image);
                employees.put("email",email);
                employees.put("phone",phone);
                employees.put("course",course );
                list.add(employees);




            }


            ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.studentslist,
                    new String[]{
                    "student_name",
                    "regno",
                    "email",
                            "department"
                    }, new int[]{
                    R.id.textView21,
                    R.id.textView26,
                    R.id.textView28, R.id.textView27});
            listview.setAdapter(adapter);







        } catch (JSONException e) {
            e.printStackTrace();
        }










        return rootView;







    }
}
