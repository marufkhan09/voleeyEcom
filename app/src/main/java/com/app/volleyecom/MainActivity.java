package com.app.volleyecom;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
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
    //start implementing recycler view
    //Step 1: Declare RecyclerView
    RecyclerView recyclerView;

    List<MyObject> myObjectList;
    MyAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //step 2: find recycler view
        recyclerView = findViewById(R.id.recycler_view);
        //step 10: Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //step 11: Initialise list
        myObjectList = new ArrayList<>();
        adapter  = new MyAdapter(myObjectList);
        //step 12: Set adapter to recycler view
        recyclerView.setAdapter(adapter);
        //step 9: Fetch data
        fetchData();
    }

   private void fetchData(){
    String url = "https://fakestoreapi.com/products";
    //step 3: Create request
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
        //step 4: Handle response
        @Override
        public void onResponse(JSONArray response) {
            //step 5: Parse response
            parseResponse(response);
            Toast.makeText(MainActivity.this, "Data fetched", Toast.LENGTH_SHORT).show();
        }
    }, new Response.ErrorListener() {
        //step 7: Handle error
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    });
    //step 8: Add request to queue
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(jsonArrayRequest);
    }

    private void parseResponse(JSONArray response){
        try{
            for(int i = 0; i< response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);
                int id  = jsonObject.getInt("id");
                String title  = jsonObject.getString("title");
                String description  = jsonObject.getString("description");
                String category  = jsonObject.getString("category");
                String price  = jsonObject.getString("price");
                String image = jsonObject.getString("image");
                Log.i(TAG, "This is an info log message."+ image);
                MyObject myobject = new MyObject(id, title, price,description,category,image);
                //step 6: Add data to list
                myObjectList.add(myobject);
            }
            adapter.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}