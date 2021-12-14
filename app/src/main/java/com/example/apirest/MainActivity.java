package com.example.apirest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    TextView txtUser;
    RequestQueue requestQueue;
    String URL = "https://gorest.co.in/public/v1/users";
    ArrayList<String> lstUser = new ArrayList<String> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        stringRequest();
    }

    public void stringRequest(){
        txtUser = (TextView)findViewById(R.id.txtListUser);

        StringRequest request = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            JSONArray JSONlista = object.getJSONArray("data");
                            for(int i=0; i< JSONlista.length();i++){
                                JSONObject user=  JSONlista.getJSONObject(i);
                                lstUser.add("Nombre: "+user.getString("name")+ "\n"
                                        + "Email: "+user.getString("email")+"\n"
                                        + "Genero: "+user.getString("gender")+"\n"
                                        + "Estado: "+user.getString("status")+"\n\n");
                            }
                            txtUser.setKeyListener(null);
                            txtUser.setText(lstUser.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }
}