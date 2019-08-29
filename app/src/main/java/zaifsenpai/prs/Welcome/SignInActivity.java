package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class SignInActivity extends Activity {
    RequestQueue LoginRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        LoginRequestQueue = Volley.newRequestQueue(getApplicationContext());

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
                final EditText u_name = findViewById(R.id.ET_SignIn_Username);
                final EditText password = findViewById(R.id.ET_SignIn_Password);

                if (u_name.getText().length() < _Properties.USERNAME_MIN_LENGTH)
                    u_name.setError(getString(R.string.login_invalid_username));
                if (password.getText().length() < _Properties.PASSWORD_MIN_LENGTH)
                    password.setError(getString(R.string.login_invalid_password));

                Intent returnIntent = new Intent();
                if (u_name.getError() == null && password.getError() == null) {
                    StringRequest jsonObjRequest = new StringRequest(
                            Request.Method.POST,
                            _Properties.ASP_SERVER_Login_API_ADDRESS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("volley", "Error: " + error.getMessage());
                                    // Fail
                                }
                            }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Email", u_name.getText().toString().trim());
                            params.put("Password", password.getText().toString().trim());
                            return params;
                        }
                    };

                    LoginRequestQueue.add(jsonObjRequest);

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
