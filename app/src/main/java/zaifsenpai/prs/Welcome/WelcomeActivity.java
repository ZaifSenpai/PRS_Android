package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zaifsenpai.prs.General.SmsGetService;
import zaifsenpai.prs.General.SmsUploadService;
import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class WelcomeActivity extends Activity {

    TextView sin;
    LinearLayout circle;

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
        ActivityCompat.requestPermissions(this, _Properties.permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (_Methods.hasPermissions(this, permissions)) {
            startService(new Intent(WelcomeActivity.this, SmsGetService.class));
            startService(new Intent(WelcomeActivity.this, SmsUploadService.class));
        }
        else
            Toast.makeText(WelcomeActivity.this, "Unable to start services.", Toast.LENGTH_LONG).show();
    }
}
