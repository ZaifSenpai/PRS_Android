package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import zaifsenpai.prs.General._Properties;
import zaifsenpai.prs.R;

public class WelcomeActivity extends Activity {

    LinearLayout circle;
    TextView sin;
    private String[] ServerInputResultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sin = findViewById(R.id.TV_sign_in);
        circle = findViewById(R.id.circle);
        ServerInputResultValue = new String[]{"", ""};

        if (SetServerIfFirstTime()) {
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
            builder.setTitle("No server!");
            builder.setMessage("App failed to find the server.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });

            builder.create().show();
        }
    }

    private boolean SetServerIfFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (preferences.getBoolean("RanBefore", false)) {
            _Properties.setDjangoServerAddress(preferences.getString("DjangoServerAddress", "0.0.0.0"));
            _Properties.setAspServerAddress(preferences.getString("AspServerAddress", "0.0.0.0"));
        } else {
            String[] addresses = GetServerAddresses();
            if (addresses[0].isEmpty() || addresses[1].isEmpty())
                return false;

            _Properties.setDjangoServerAddress(addresses[0]);
            _Properties.setAspServerAddress(addresses[1]);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("DjangoServerAddress", addresses[0]);
            editor.putString("AspServerAddress", addresses[1]);
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }

        return true;
    }

    private String[] GetServerAddresses() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message ignored) {
                throw new RuntimeException();
            }
        };

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Server Addresses");
        alert.setMessage("Please input addresses of Django and Asp.net app servers separated by semicolon.");

        final EditText input = new EditText(this);
        input.setHint("IP:Port;IP:Port");
        alert.setView(input);
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String[] splits = input.getText().toString().split(";");
                if (splits.length == 2) {
                    ServerInputResultValue[0] = splits[0].trim();
                    ServerInputResultValue[1] = splits[1].trim();
                }

                handler.sendMessage(handler.obtainMessage());
            }
        });

        alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ServerInputResultValue[0] = "";
                ServerInputResultValue[1] = "";

                handler.sendMessage(handler.obtainMessage());
            }
        });
        alert.show();

        try {
            Looper.loop();
        } catch (RuntimeException ignored) {
        }

        return ServerInputResultValue;
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
