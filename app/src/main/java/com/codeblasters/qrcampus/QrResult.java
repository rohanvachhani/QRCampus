package com.codeblasters.qrcampus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class QrResult extends WithManuActivity {

    private ImageView imageView;
    private EditText title;
    private EditText date;
    private TextView details;
    private info i;
    private Uri imguri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);
        imageView = findViewById(R.id.img_view_disaply);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        details = findViewById(R.id.details);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        i = new info();
        databaseReference = FirebaseDatabase.getInstance().getReference("info").child(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   /* Toast.makeText(getApplicationContext(),"getting the data...",Toast.LENGTH_SHORT).show();*/

                String img_url = dataSnapshot.child("imageUri").getValue(String.class);
                if (img_url.equals("no Image")) {
                    imguri = Uri.parse("android.resources://" + BuildConfig.APPLICATION_ID + "/" + R.drawable.no_image);
                    imageView.setImageURI(imguri);
                } else {
                    img_url = dataSnapshot.child("imageUri").getValue(String.class);
                    Picasso.with(getApplicationContext()).load(img_url).into(imageView);

                }
                String title_ii = dataSnapshot.child("title").getValue(String.class);
                String date_ii = dataSnapshot.child("date").getValue(String.class);
                String details_ii = dataSnapshot.child("info").getValue(String.class);

                title.setText(title_ii);
                title.setFocusable(false);
                title.setClickable(false);

                date.setText(date_ii);
                date.setFocusable(false);
                date.setClickable(false);


                details.setText(details_ii);
                details.setFocusable(false);
                details.setClickable(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
