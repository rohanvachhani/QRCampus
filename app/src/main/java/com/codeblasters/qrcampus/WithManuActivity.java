package com.codeblasters.qrcampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

/**
 * Created by Rohan on 4/7/2018.
 */

public class WithManuActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:        //admin login
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
           /* case R.id.delete_user:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                builder1.setTitle("Confirm");
                builder1.setMessage("Are you sure For Delete your account?");

                builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog1, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Your account is deleted.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to delete your account!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        dialog1.dismiss();
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });

                builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder1.create();
                alert.show();
                break;*/

            /*case R.id.delete_database:
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        reference.child("student_table").removeValue();

                        Toast.makeText(getApplicationContext(), "DataBase Cleared", Toast.LENGTH_SHORT).show();


                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert1 = builder.create();
                alert1.show();

                break;
                */

           /* case R.id.user:
                startActivity(new Intent(getApplicationContext(), expense.class));
                finish();
                break;*/
            case R.id.about_us:
                startActivity(new Intent(getApplicationContext(), AboutUs.class));
                finish();
                break;
            case R.id.ScanQR:
                startActivity(new Intent(getApplicationContext(), QrScannerActivity.class));
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
