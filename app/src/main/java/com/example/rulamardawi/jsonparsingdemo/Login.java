package com.example.rulamardawi.jsonparsingdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONPareser jsonParser = new JSONPareser();

    EditText username;
    EditText password;


    // url to create new product
    private static String url_create_product = "http://192.168.43.158/stdsignup.aspx";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button btnLogin = (Button) findViewById(R.id.login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewProduct().execute();
                //new JSONTask().execute("https://raw.githubusercontent.com/mobilesiri/JSON-Parsing-in-Android/master/index.html");
            }
        });




    }

    class CreateNewProduct extends AsyncTask<String, String, String> {


        String id = username.getText().toString();
        String pwd = password.getText().toString();

        /**
         * Before starting background thread Show Progress Dialog
      * */
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(Login.this);
           pDialog.setMessage("Creating Product..");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {



            HashMap<String, String> parms = new HashMap<>();
            parms.put("StudentID", id);
            parms.put("Password", pwd);

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,"GET",parms);

            if (json == null)
                return "";
//          try{
//
//             String success = json.getString("result");
//
//                if(success.equals("0"))
//                  startActivity(new Intent(Login.this, Failure.class));
//                 // Toast.makeText(getApplicationContext(), "result = 0", Toast.LENGTH_LONG).show();
//              else  if(success.equals("1"))
//                    startActivity(new Intent(Login.this, Success.class));
//
//              finish();
//
//
//
//          }
//          catch (JSONException e) {
//              e.printStackTrace();
//          }

            return null;
        }


        }

}
