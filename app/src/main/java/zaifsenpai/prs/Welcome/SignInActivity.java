package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.Home.MainActivity;
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
                Intent it = new Intent(SignInActivity.this, WelcomeActivity.class);
                startActivity(it);
            }
        });

        TextView sign_in_button = findViewById(R.id.TV_sign_in);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText u_name = findViewById(R.id.ET_SignIn_Username);
                EditText password = findViewById(R.id.ET_SignIn_Password);
                String error_text = "";

                if (u_name.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    error_text = getString(R.string.login_invalid_username);
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    error_text += "\n" + getString(R.string.login_invalid_password);

                if (error_text.length() > 0)
                    Toast.makeText(getApplicationContext(), error_text, Toast.LENGTH_LONG).show();
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
                else {
                    Intent it = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(it);
                }
            }
        });
    }
}
