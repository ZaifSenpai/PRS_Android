package zaifsenpai.prs.Welcome;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zaifsenpai.prs.General.SmsGetService;
import zaifsenpai.prs.R;

public class WelcomeActivity extends Activity {

    TextView sin;
    LinearLayout circle;
    String[] permissions = new String[]{
            Manifest.permission.READ_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        circle = findViewById(R.id.circle);
        sin = findViewById(R.id.TV_sign_in);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(WelcomeActivity.this, SigUpActivity.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(it);
            }
        });

        // Get permissions
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasPermissions(this, permissions))
            // Start service
            startService(new Intent(WelcomeActivity.this, SmsGetService.class));
        else
            Toast.makeText(WelcomeActivity.this, "Unable to start service.", Toast.LENGTH_LONG).show();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
