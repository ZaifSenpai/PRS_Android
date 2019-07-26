package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class SigUpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageView back_button = findViewById(R.id.Img_SignUp_Back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        TextView sign_up_button = findViewById(R.id.TV_sign_up);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.ET_SignUp_Name);
                EditText email = findViewById(R.id.ET_SignUp_Mail);
                EditText username = findViewById(R.id.ET_SignUp_Username);
                EditText password = findViewById(R.id.ET_SignUp_Password);

                if (name.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    name.setError(getString(R.string.login_invalid_name));
                if (!_Methods.isValidEmail(email.getText().toString()))
                    email.setError(getString(R.string.login_invalid_email));
                if (username.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    username.setError(getString(R.string.login_invalid_username));
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    password.setError(getString(R.string.login_invalid_password));

                Intent returnIntent = new Intent();
                if (name.getError() == null && email.getError() == null && username.getError() == null && password.getError() == null) {
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
