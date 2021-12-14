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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    TextView txtUser;
    RequestQueue requestQueue;
    String URL = "https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList";
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
//                            JSONObject object = new JSONObject();
//                            object.put("name",response);
                            JSONArray JSONlista = new JSONArray(response);
//                            JSONlista.put(object);
                            for(int i=0; i< JSONlista.length();i++){
                                JSONObject user=  JSONlista.getJSONObject(i);
                                lstUser.add("Codigo: "+user.getString("code")+ "\n"
                                        + "Nombre: "+user.getString("name")+"\n\n"
                                );
                            }
                            txtUser.setKeyListener(null);
                            txtUser.setText(lstUser.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                            txtUser.setKeyListener(null);
                            txtUser.setText(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
                return params;
            }
        }
        ;
        requestQueue.add(request);
    }
}