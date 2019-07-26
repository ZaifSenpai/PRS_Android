package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ImageView back_button = findViewById(R.id.Img_SignIn_Back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        TextView sign_in_button = findViewById(R.id.TV_sign_in);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText u_name = findViewById(R.id.ET_SignIn_Username);
                EditText password = findViewById(R.id.ET_SignIn_Password);

                if (u_name.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    u_name.setError(getString(R.string.login_invalid_username));
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    password.setError(getString(R.string.login_invalid_password));

                Intent returnIntent = new Intent();
                if (u_name.getError() == null && password.getError() == null) {
                    setResult(Activity.RESULT_OK, returnIntent);
                } else {
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
