package com.example.hossein.androidanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue rq = Volley.newRequestQueue(getApplication());



        String url = "http://rabetha.com/ad/json/site/catagories";




        textView1 = new TextView(getApplicationContext());
        textView2 = new TextView(getApplicationContext());
        textView2.setText("text2");
        textView1.setText("textview1");
        textView1.setGravity(Gravity.CENTER_VERTICAL);
        textView2.setGravity(Gravity.CENTER_VERTICAL);

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        int height = metrics.heightPixels;


        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(-width/2,0,0,0);

        textView2.setLayoutParams(layoutParams);

        //FrameLayout frameLayout = new FrameLayout(getApplicationContext());
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_main);
        frameLayout.addView(textView1);
        frameLayout.addView(textView2);

        frameLayout.getChildAt(0).setVisibility(View.VISIBLE);
        frameLayout.getChildAt(1).setVisibility(View.VISIBLE);

        makeJsonArrayRequest(url);




    }

    public void makeJsonObjectRequest(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, (String)null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String id = response.getString("id");


                    String jsonResponse = "Name: " + name + "\n\n";
                    jsonResponse += "id: " + id + "\n\n";

                    textView1.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }

        });

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private void makeJsonArrayRequest(String urlJsonArry) {

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // Parsing json array response
                            // loop through each json object
                            String jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String name = person.getString("name");
                                String id = person.getString("id");


                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + id + "\n\n";


                            }

                            textView1.setText(jsonResponse);
                            textView2.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    public void reStart(View view) {


        ObjectAnimator animator1 = ObjectAnimator.ofFloat(textView1,"TranslationX",0, width  );
        animator1.setDuration(1000);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setRepeatCount(0);
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView2,"TranslationX",0,width);
        animator2.setDuration(1000);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setRepeatCount(0);
        animator2.start();
    }


}
