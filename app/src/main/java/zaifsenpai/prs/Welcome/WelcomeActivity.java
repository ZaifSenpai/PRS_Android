package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class WelcomeActivity extends Activity {

    LinearLayout circle;
    TextView sin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sin = findViewById(R.id.TV_sign_in);
        circle = findViewById(R.id.circle);

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivityForResult(it, 222);
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(WelcomeActivity.this, SigUpActivity.class);
                startActivityForResult(it, 333);
            }
        });

        ActivityCompat.requestPermissions(this, _Properties.permissions, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 222 && resultCode == Activity.RESULT_OK) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else if (requestCode == 333 && resultCode == Activity.RESULT_OK) {
            Intent it = new Intent(WelcomeActivity.this, SignInActivity.class);
            startActivityForResult(it, 222);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
