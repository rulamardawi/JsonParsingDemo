package com.example.rulamardawi.jsonparsingdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHit = (Button)findViewById(R.id.btnHit);
        Button btnnext = (Button)findViewById(R.id.btnnext);

        tvData = (TextView) findViewById(R.id.tvJsonItem);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://10.100.6.177/Get_All_Instructor.aspx");
                //new JSONTask().execute("https://raw.githubusercontent.com/mobilesiri/JSON-Parsing-in-Android/master/index.html");
            }
        });


    }
    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

               String finalJson = buffer.toString();
               JSONObject parentObject = new JSONObject(finalJson);
               JSONArray parentArray = parentObject.getJSONArray("MyUsers");
               JSONObject finalObject = parentArray.getJSONObject(1);
               String instructorname = finalObject.getString("Firstname");
               return instructorname;
            }
            catch (Exception exception)
            {
                System.out.print(exception.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            tvData.setText(result);
        }

    }
    public void Login(View view)
    {

        startActivity(new Intent(this, Login.class));
    }

}
