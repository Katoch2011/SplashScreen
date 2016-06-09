package com.example.priya.splashscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //rotation animation
        final ImageView image=(ImageView)findViewById(R.id.image);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                image.startAnimation(an);
                an.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        checkInternetConenction();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT);
                }
            }

        }).start();

        //blink animation
        final TextView net=(TextView)findViewById(R.id.net);
        new Thread(new Runnable() {
            @Override
            public void run() {
                net.clearAnimation();
                Animation ani=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
                net.startAnimation(ani);
            }
        }).start();



    }
    //checking internet connection
    private boolean checkInternetConenction() {

        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oops! Internet not available.");
            builder.setMessage("Connect to Internet and Restart App.");
            builder.setCancelable(true);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory( Intent.CATEGORY_HOME );
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
        return false;
    }
}

