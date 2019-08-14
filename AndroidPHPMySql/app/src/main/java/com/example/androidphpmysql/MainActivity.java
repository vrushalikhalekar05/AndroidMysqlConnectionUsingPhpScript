package com.example.androidphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername,editTextPassword,editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        if(SharedPrefManager.getInstance ( this ).isLoggedIn ()){
            finish ();
            startActivity ( new Intent ( this,ProfileActivity.class ) );
            return;
        }

        editTextEmail=(EditText) findViewById ( R.id.editTextEmail );
        editTextUsername=(EditText) findViewById ( R.id.editTextUsername );
        editTextPassword=(EditText) findViewById ( R.id.editTextPassword );
        textViewLogin=(TextView)findViewById ( R.id.textViewLogin );

        buttonRegister=(Button) findViewById (R.id.buttonRegister );
        progressDialog=new ProgressDialog ( this );
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener ( this );
    }
    private void registerUser(){
        final String email=editTextEmail.getText ().toString ().trim ();
        final String password=editTextUsername.getText ().toString ().trim ();
        final String username=editTextPassword.getText ().toString ().trim ();

        progressDialog.setMessage ( "Registering user" );
        progressDialog.show();

        StringRequest stringRequest = new StringRequest ( Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss ();
                        try {
                            JSONObject jsonObject = new JSONObject ( response );
                            Toast.makeText ( getApplicationContext (),jsonObject.getString ( "message" ),Toast.LENGTH_LONG ).show ();
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                    }
                },
                new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide ();
                        Toast.makeText ( getApplicationContext (),error.getMessage (),Toast.LENGTH_LONG ).show ();
                    }
                }//eof errorlistener
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<> (  );
                params.put (  "username",username);
                params.put (  "email",email);
                params.put (  "password",password);
                return params;
            }
        };
        RequestHandler.getInstance ( this ).addToRequestQueue ( stringRequest );
    }
    public void onClick(View view){
        if(view==buttonRegister)
        {
            registerUser ();
        }
        if(view==textViewLogin){
            startActivity ( new Intent ( this,LoginActivity.class ) );
        }
    }
}
