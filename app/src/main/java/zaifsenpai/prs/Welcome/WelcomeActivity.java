package zaifsenpai.prs.Welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.net.Socket;

import zaifsenpai.prs.General.DnsServersDetector;
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

        if (FindServer()) {
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

    boolean FindServer() {
        DnsServersDetector serversDetector = new DnsServersDetector(WelcomeActivity.this);
        String[] servers = serversDetector.getServers();
        boolean found = false;
        for (int i = 0; i < servers.length && !found; i++) {
            String[] chunks = servers[i].trim().split("\\.");
            if (chunks.length != 4) continue;
            String ip = chunks[0] + "." + chunks[1] + "." + chunks[2] + ".";

            int start = Integer.parseInt(chunks[3]) + 1;
            int end = 256;

            for (; start < end && !found; start++) {
                if (isPortOpen(ip + start, _Properties.DJANGO_SERVER_PORT, 2000)) {
                    _Properties.DJANGO_SERVER_ADDRESS = "http://" + ip + start + ":" + _Properties.DJANGO_SERVER_PORT;
                    found = true;
                }
            }
        }

        return found;
    }

    public static boolean isPortOpen(String ip, int port, int timeout) {
        Log.i(_Properties.LOG_TAG, "1. Testing: " + ip + ":" + port);
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.setSoTimeout(3000);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String fromServer = in.readLine();
//            Log.i(_Properties.LOG_TAG, "2. From server: " + fromServer);
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
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
