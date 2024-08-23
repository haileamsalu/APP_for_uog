package com.example.uselogin;

import static java.lang.Double.parseDouble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Feed_Activity extends AppCompatActivity {
    public static final String IMAGE_TYPE = "application/pdf";//only pdf file
    public static final int SELECT_PDF_FILE=200;
    AppCompatAutoCompleteTextView autoCompleteTextView;
    Button file_uploadbtn,submit_btn;
    Uri filepath;
    InputStream inputStream;
    byte [] buffer;
    EditText TitleText, AbstractText;
    String Title, Abstract,ProType;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        autoCompleteTextView = findViewById(R.id.doctype_menu);
        file_uploadbtn = findViewById(R.id.file_btn);
        submit_btn = findViewById(R.id.submit_btnid);
        TitleText = findViewById(R.id.thesis_idText);
        AbstractText = findViewById(R.id.abstract_idText);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<CharSequence>drop_adapter = ArrayAdapter.createFromResource(this,R.array.drop_down, android.R.layout.simple_spinner_item);
        drop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
       autoCompleteTextView.setAdapter(drop_adapter);
       file_uploadbtn.setOnClickListener(view -> {
           try {
               Intent intent = new Intent();
               intent.setType(IMAGE_TYPE);
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent,
                       "Select a PDF File"), SELECT_PDF_FILE);
           } catch (android.content.ActivityNotFoundException ex) {
               Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
           }
       });
       submit_btn.setOnClickListener(view -> {
           Title = TitleText.getText().toString();
           Abstract = AbstractText.getText().toString();
           ProType = autoCompleteTextView.getText().toString();
           if(inputStream == null || Title.equals("") || Abstract.equals("")){
               Toast.makeText(getApplicationContext(),"Please select PDF file first", Toast.LENGTH_LONG).show();
           }else{
              Bundle intentB = new Bundle();
              intentB.putString("Title",Title);
              intentB.putString("Abstract",Abstract);
              intentB.putString("Protype",ProType);
              intentB.putByteArray("Content",buffer);
              LoaderManager.getInstance(this).initLoader(0,intentB,uploadCall);
           }
       });
    }
  private LoaderManager.LoaderCallbacks<Integer> uploadCall = new LoaderManager.LoaderCallbacks<Integer>() {
      String title ="",abstractd="", protype="";
      byte [] pdffile;
      @NonNull
      @Override
      public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {

          if (args != null) {
              title = args.getString("Title");
              abstractd = args.getString("Abstract");
              protype = args.getString("Protype");
              pdffile = args.getByteArray("Content");
          }
          return new uploadAsynck(getApplicationContext(),title,abstractd,protype,pdffile);
      }

      @Override
      public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
          Toast.makeText(Feed_Activity.this, "Success", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLoaderReset(@NonNull Loader<Integer> loader) {

      }
  };

    public byte[] getBytes(InputStream inputStream, int lengths) throws IOException {
        ByteArrayOutputStream blobBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[lengths];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            blobBuffer.write(buffer, 0, len);
        }
        return blobBuffer.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PDF_FILE && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            filepath = data.getData();
            file_uploadbtn.setText(filepath.toString());
            file_uploadbtn.setTextColor(getResources().getColor(R.color.success));
            try {
                InputStream iStream = Feed_Activity.this.getContentResolver().openInputStream(filepath);
                int len = iStream.available();
                inputStream = iStream;
                buffer = getBytes(iStream,len);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.threedots_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(Feed_Activity.this, MainActivity.class));
                overridePendingTransition(0, R.anim.toptobottom_exit);
                finish();
            case R.id.user_name:
                break;
            case R.id.sign_out:
                startActivity(new Intent(Feed_Activity.this, MainActivity.class));
                overridePendingTransition(0, R.anim.toptobottom_exit);
                finish();
                break;
            case R.id.about_us:
                startActivity(new Intent(Feed_Activity.this, About.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
