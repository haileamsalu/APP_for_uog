package com.example.uselogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity<Private> extends AppCompatActivity {
    EditText password;
    Button LoginBtn, studentbtn;
    EditText username;
    ImageView logview;
    private String Password, Username;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username_ID);
        password = findViewById(R.id.username_password);
        LoginBtn = findViewById(R.id.Login_Id);
        studentbtn = findViewById(R.id.student_ID);
        logview = findViewById(R.id.logo_id);
        progressBar = findViewById(R.id.progress_circular);
        logview.setImageResource(R.mipmap.uog_foreground);
        studentbtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Reading_Activity.class));
            overridePendingTransition(R.anim.bottomtotop_enter,R.anim.constant);
            finish();
        });
        LoginBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Username = username.getText().toString();
            Password = password.getText().toString();

            if (Username.equals("") || Password.equals("")) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Wrong username and password, Please try again!", Toast.LENGTH_SHORT).show();
            }
          else  {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Bundle thisbundle = new Bundle();
                thisbundle.putString("Username",Username);
                thisbundle.putString("Password",Password);
                LoaderManager.getInstance(this).initLoader(0,thisbundle,loaderCallbacks);
            }
        });
    }
    private final LoaderManager.LoaderCallbacks<Integer> loaderCallbacks = new LoaderManager.LoaderCallbacks<Integer>() {
        String user,password;
        @NonNull
        @Override
        public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
            assert args != null;
            this.user = args.getString("Username");
            this.password = args.getString("Password");
            return new LoginTask(getApplicationContext(),user,password);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
            if( data==1)
            {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, Feed_Activity.class));
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Integer> loader) {

        }
    };
}