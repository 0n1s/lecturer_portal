package com.jisort.lectportal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.jisort.lectportal.LoginActivity.data;

/**
 * Created by jjsikini on 12/2/17.
 */


public class Profile  extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profile, container, false);

        AwesomeTextView textView10 = rootView.findViewById(R.id.textView10);
        AwesomeTextView textView17 = rootView.findViewById(R.id.textView17);
        AwesomeTextView textView18 = rootView.findViewById(R.id.textView18);
        AwesomeTextView textView20 = rootView.findViewById(R.id.textView20);

        ImageView imageView = rootView.findViewById(R.id.imageView5);

        JSONObject json = null;
        try {
            json = new JSONObject(data);
            JSONArray array = json.getJSONArray("result");
            JSONObject c = array.getJSONObject(0);
            String personal_data = c.getString("personal_data");
            Log.d("personal_data", personal_data);

            JSONArray  jsonArray =  new JSONArray(personal_data);
            for(int i=0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String lecno = jsonObject.getString("lecno");
                String image = jsonObject.getString("image");
                textView20.setText(jsonObject.getString("department"));
                String phone = jsonObject.getString("phone");
                textView18.setText(phone);
                String email = jsonObject.getString("email");
                textView17.setText(email);
                String fname = jsonObject.getString("fname");
                textView10.setText(fname);
String image_url= jsonObject.getString("image_url");
                Picasso.with(getActivity())
                        .load(image_url)
                        .into(imageView);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;



    }
}
