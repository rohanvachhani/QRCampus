package com.codeblasters.qrcampus;

import android.content.Intent;
import android.os.Bundle;

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUs extends WithManuActivity {


    /*for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboout_us);


    }


}
