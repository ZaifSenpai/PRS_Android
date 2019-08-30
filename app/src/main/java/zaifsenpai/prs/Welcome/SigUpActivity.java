package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import zaifsenpai.prs.General._Methods;
import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class SigUpActivity extends Activity {
    RequestQueue SignUpRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SignUpRequestQueue = Volley.newRequestQueue(getApplicationContext());

        ImageView back_button = findViewById(R.id.Img_SignUp_Back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        final TextView sign_up_button = findViewById(R.id.TV_sign_up);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText username = findViewById(R.id.ET_SignUp_Username);
                final EditText email = findViewById(R.id.ET_SignUp_Mail);
                final EditText dob = findViewById(R.id.ET_DT_Picker);
                final EditText password = findViewById(R.id.ET_SignUp_Password);

                if (username.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    username.setError(getString(R.string.login_invalid_username));
                if (!_Methods.isValidEmail(email.getText().toString()))
                    email.setError(getString(R.string.login_invalid_email));
                if (!_Methods.isValidDoB(dob.getText().toString()))
                    dob.setError(getString(R.string.login_invalid_dob));
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    password.setError(getString(R.string.login_invalid_password));

                if (email.getError() == null && username.getError() == null && dob.getError() == null && password.getError() == null) {
                    StringRequest jsonObjRequest = new StringRequest(
                            Request.Method.POST,
                            _Properties.ASP_SERVER_Signup_API_ADDRESS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    setResult(Activity.RESULT_OK, new Intent());
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("volley", "Error: " + error.getMessage());
                                    sign_up_button.setError(getString(R.string.invalid_register_request));
                                }
                            }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("Username", username.getText().toString().trim());
                            params.put("Email", email.getText().toString().trim());
                            params.put("Password", password.getText().toString().trim());
                            params.put("ConfirmPassword", password.getText().toString().trim());
                            params.put("DateofBirth", dob.getText().toString().trim());
                            params.put("Role", "2");
                            return params;
                        }
                    };

                    SignUpRequestQueue.add(jsonObjRequest);

                }
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
