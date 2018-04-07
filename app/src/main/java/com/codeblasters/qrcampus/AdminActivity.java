package com.codeblasters.qrcampus;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class AdminActivity extends AppCompatActivity {

    private static final int READ_EXTERNAL_STORAGE = 0;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private static final int GALLERY_INTENT = 2;
    private Uri mImageUri;
    private Button select_image;
    private EditText title_input;
    private EditText details;
    private ProgressBar mProgressBar;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask mUploadTask;

    String title_in;
    String details_input;
    String QR_gen_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        storageReference = FirebaseStorage.getInstance().getReference("info");
        databaseReference = FirebaseDatabase.getInstance().getReference("info");

        select_image = (Button) findViewById(R.id.btn_pick);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        title_input = findViewById(R.id.title);
        details = findViewById(R.id.description);
        mProgressBar = findViewById(R.id.progress_bar);
        storageReference = FirebaseStorage.getInstance().getReference("info");
        databaseReference = FirebaseDatabase.getInstance().getReference("info");


        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for Runtime Permission
                if (
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Call for Permission", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    }
                } else {
                    callgalary();
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

    }


    //Check for Runtime Permissions for Storage Access
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callgalary();
                return;
        }
        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
    }

    //If Access Granted gallery Will open
    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    //After Selecting     image from     gallery image     will directly     uploaded to     Firebase Database
    //and Image will Show in Image View
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            Toast.makeText(getApplicationContext(), mImageUri.toString(), Toast.LENGTH_SHORT).show();

        }
    }


    //submit button click
    public void makeQR(View view) {
        Toast.makeText(this, "QR Code GEenerating..", Toast.LENGTH_SHORT).show();

        //taking data and make class object
        title_in = title_input.getText().toString().trim();

        details_input = details.getText().toString().trim();
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(this, "Uploading is in progress", Toast.LENGTH_LONG).show();
        } else {
            if (title_in != "") {
                upload_file();
            } else {
                Toast.makeText(this, "Please ADD Atleast a title of event or information", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void upload_file() {
        StorageReference filereference = storageReference.child(System.currentTimeMillis() + ".jpg");

        mUploadTask = filereference.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        }, 500);

                        Toast.makeText(AdminActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                        String date = dateView.getText().toString();
                        info upload = new info(title_in, taskSnapshot.getDownloadUrl().toString(), date, details_input);
                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(upload);
                        QR_gen_string = uploadId.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });

    }
}
