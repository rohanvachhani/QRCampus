package com.codeblasters.qrcampus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class QrResult extends WithManuActivity {

    private ImageView imageView;
    private EditText title;
    private EditText date;
    private TextView details;
    private info i;
    private Uri imguri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static String name ;

   /* Random generator = new Random();
    int n = 10000;*/

    private static String FILE = Environment.getExternalStorageDirectory() + File.separator +"file1" +".pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        imageView = findViewById(R.id.img_view_disaply);
        title = findViewById(R.id.title_iin);
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
                date.setText(date_ii);
                details.setText(details_ii);

                title.setFocusable(false);
                title.setClickable(false);


                date.setFocusable(false);
                date.setClickable(false);


                details.setFocusable(false);
                details.setClickable(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //pdf save tasks
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("CodeBlasters");
        document.addCreator("CodeBlasters");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph(title.getText().toString().trim(), catFont));//add title here

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Date : " + date.getText().toString().trim(),
                smallBold));
        addEmptyLine(preface, 1);
        document.add(preface);
        //for Image
        Image image = null;

        imageView.buildDrawingCache();
        Bitmap bmp = imageView.getDrawingCache();
        // Bitmap bmp = bitDw.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        try {
            image = Image.getInstance(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.add(image);
        addEmptyLine(preface, 3);


        //till here..
        Paragraph pre = new Paragraph();

        pre.add(new Paragraph(
                details.getText().toString().trim(),
                smallBold));

        addEmptyLine(pre, 8);
        document.add(pre);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void download_data(View view) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            // addContent(document);
            //createImage();
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
