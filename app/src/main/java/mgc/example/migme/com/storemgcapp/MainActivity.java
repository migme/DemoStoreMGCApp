package mgc.example.migme.com.storemgcapp;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String GOOGLE_PLAY        = "https://play.google.com/store/apps/details?id=";
    public static final String GOOGLE_REFFERRER   = "&referrer=utm_source%3Dmigme%26utm_medium%3Dfunkey%26utm_campaign%3Dfunkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCallRecharge = (Button) findViewById(R.id.btnCallRecharge);
        Button btnCallCredit = (Button) findViewById(R.id.btnCallCredit);
        btnCallRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMigmePaymentActivity("com.projectgoth.payment.RECHARGE");
            }
        });
        btnCallCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMigmePaymentActivity("com.projectgoth.payment.VIEW");
            }
        });
    }

    protected void callMigmePaymentActivity(String action) {
        try {
            Intent i = new Intent(action);
            i.addCategory("android.intent.category.DEFAULT");
            startActivity(i);
        } catch(ActivityNotFoundException e) {
            if(!migmeInstalled()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(GOOGLE_PLAY + "com.projectgoth" + GOOGLE_REFFERRER));
                startActivity(intent);
            } else {
                onCreateDialog("Please upgrade your migme to latest version above v6.00.029").show();
            }
        }catch (Exception e) {
            Log.d(TAG, "Happen Exception: " + e.getMessage());
        }
    }

    protected boolean migmeInstalled() {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.projectgoth", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    public Dialog onCreateDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        return builder.create();
    }
}
