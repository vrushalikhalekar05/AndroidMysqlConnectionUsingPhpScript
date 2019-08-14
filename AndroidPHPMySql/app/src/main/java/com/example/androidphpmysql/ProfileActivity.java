package com.example.androidphpmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername,textViewUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );

        if(!SharedPrefManager.getInstance ( this ).isLoggedIn ()){
            finish ();
            startActivity ( new Intent ( this,LoginActivity.class ) );
        }
        textViewUsername=(TextView) findViewById ( R.id.textViewUsername );
        textViewUserEmail=(TextView) findViewById ( R.id.textViewUserEmail  );

        textViewUserEmail.setText ( SharedPrefManager.getInstance ( this ).getUserEmail () );
        textViewUsername.setText ( SharedPrefManager.getInstance ( this ).getUsername () );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId ()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance ( this ).logout ();
                finish ();
                startActivity ( new Intent ( this,MainActivity.class ) );
                break;
        }
        return true;
    }
}
