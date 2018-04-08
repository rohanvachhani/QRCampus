package com.codeblasters.qrcampus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

public class MainActivity extends WithManuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, QrScannerActivity.class);
        startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //code foe reader
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(getApplicationContext(), data.getExtras().getString(QrScannerActivity.QR_RESULT_STR), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), QrResult.class);
                i.putExtra("id", data.getExtras().getString(QrScannerActivity.QR_RESULT_STR));
                startActivity(i);


            } else {
                Toast.makeText(getApplicationContext(), "Scanned Nothing!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
