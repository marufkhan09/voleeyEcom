package com.app.volleyecom;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MyObject> myObjectList;
    MyAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myObjectList = new ArrayList<>();
        adapter  = new MyAdapter(myObjectList);
        recyclerView.setAdapter(adapter);
        fetchData();
    }

   private void fetchData(){
    String url = "https://fakestoreapi.com/products";

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            parseResponse(response);
            Toast.makeText(MainActivity.this, "Data fetched", Toast.LENGTH_SHORT).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    });

       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(jsonArrayRequest);
    }

    private void parseResponse(JSONArray response){
        Log.d("res len", String.format("parseResponse: %d", response.length()));
        try{
            for(int i = 0; i< response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);
                int id  = jsonObject.getInt("id");
                String title  = jsonObject.getString("title");
                String description  = jsonObject.getString("description");
                String category  = jsonObject.getString("category");
                String price  = jsonObject.getString("price");
                String image = jsonObject.getString("image");
                MyObject myobject = new MyObject(id, title, description, price,image,category);
                myObjectList.add(myobject);
            }
            adapter.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}