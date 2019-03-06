package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    }
}
