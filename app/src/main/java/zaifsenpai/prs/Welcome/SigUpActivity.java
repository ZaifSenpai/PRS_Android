package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class SigUpActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageView back_button = findViewById(R.id.Img_SignUp_Back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SigUpActivity.this, WelcomeActivity.class);
                startActivity(it);
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
                String error_text = "";

                if (name.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    error_text = getString(R.string.login_invalid_name);
                if (!_Methods.isValidEmail(email.getText().toString()))
                    if (error_text.length() > 0)
                        error_text += "\n" + getString(R.string.login_invalid_email);
                    else
                        error_text += getString(R.string.login_invalid_email);
                if (username.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    if (error_text.length() > 0)
                        error_text += "\n" + getString(R.string.login_invalid_username);
                    else
                        error_text += getString(R.string.login_invalid_username);
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    if (error_text.length() > 0)
                        error_text += "\n" + getString(R.string.login_invalid_password);
                    else
                        error_text += getString(R.string.login_invalid_password);

                if (error_text.length() > 0) {
                    Snackbar sb = Snackbar.make(v, error_text, Snackbar.LENGTH_INDEFINITE);
                    sb.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                    sb.setActionTextColor(Color.RED);
                    View sbView = sb.getView();
                    sbView.setBackgroundColor(Color.WHITE);
                    sb.show();
//                    Toast.makeText(getApplicationContext(), error_text, Toast.LENGTH_LONG).show();
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("Invalid Input")
//                            .setMessage(error_text)
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            })
//                            .setCancelable(true)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
                }
                else {
                    finish();
                }
            }
        });
    }
}
