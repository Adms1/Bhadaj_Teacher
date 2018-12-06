package anandniketan.com.anbcteacher.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private String notificationMsg;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                try {
                    notificationMsg = getIntent().getStringExtra("message");
                    if (notificationMsg != null) {
                        Utility.setPref(SplashActivity.this, "Push_Notification_message", notificationMsg);
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

