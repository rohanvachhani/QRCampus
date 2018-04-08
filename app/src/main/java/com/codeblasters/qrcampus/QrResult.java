package com.codeblasters.qrcampus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class QrResult extends WithManuActivity {

    private ImageView imageView;
    private EditText title;
    private EditText date;
    private TextView details;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        details = findViewById(R.id.details);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        databaseReference = FirebaseDatabase.getInstance().getReference("info");
    }

    @Override
    protected void onStart() {
        super.onStart();
        
    }
}
