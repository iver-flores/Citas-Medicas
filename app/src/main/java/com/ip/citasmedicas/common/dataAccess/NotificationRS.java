package com.ip.citasmedicas.common.dataAccess;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.activities.CitasMedicasActivity;
import com.ip.citasmedicas.common.Constants;
import com.ip.citasmedicas.common.model.PedidoEvent;
import com.ip.citasmedicas.common.model.EventErrorTypeListener;
import com.ip.citasmedicas.common.utils.UtilsCommon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationRS {
    private static final String VEODO_RS = "https://veodo.000webhostapp.com/veodo/dataAccess/VeodoRS.php";
    private static final String SEND_NOTIFICATION = "sendNotification";

    public void sendNotification(String title, String message, String email, String uid,
                                 String myEmail,final EventErrorTypeListener listener){
        JSONObject params = new JSONObject();
        try {
            params.put(Constants.METHOD, SEND_NOTIFICATION);
            params.put(Constants.TITLE, title);
            params.put(Constants.MESSAGE, message);
            params.put(Constants.TOPIC, UtilsCommon.getEmailToTopic(email));
         /*   params.put(Usuario.UID, uid);
            params.put(Usuario.EMAIL, UtilsCommon.getEmailEncoded(myEmail));
            params.put(Usuario.USERNAME, title);*/

        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(PedidoEvent.ERROR_PROCESS_DATA, R.string.common_error_process_data);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, VEODO_RS,
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt(Constants.SUCCESS);

                    switch (success){
                        case PedidoEvent.SEND_NOTIFICATION_SUCCESS:
                            listener.onOk(PedidoEvent.OK, R.string.ok);
                            break;
                        case PedidoEvent.ERROR_METHOD_NOT_EXIST:
                            listener.onError(PedidoEvent.ERROR_METHOD_NOT_EXIST, R.string.chat_error_method_not_exist);
                            break;
                        default:
                            listener.onError(PedidoEvent.ERROR_SERVER, R.string.common_error_server);

                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(PedidoEvent.ERROR_PROCESS_DATA, R.string.common_error_process_data);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley error", error.getLocalizedMessage());
                listener.onError(PedidoEvent.ERROR_VOLLEY, R.string.common_error_volley);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };

        //CitasMedicasActivity.getInstance().addToReqQueue(jsonObjectRequest);
    }
}