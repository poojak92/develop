package com.scarlett.manager;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarlett.constants.ApiUrl;
import com.scarlett.core.ScarletApplication;
import com.scarlett.manager.exception.NetworkResponseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class BaseApiManager {

    private RequestQueue mRequestQueue;
    private static Context mContext;
    private final static int TIME_OUT = 180000; // 180 SEC = 3 MINS...
    private final static int RETRIES = 0;
    private IMiddlewareResponse mIMiddlewareResponse;
    private String mRequestTag;
    private Request.Priority priority = Request.Priority.NORMAL;


    public BaseApiManager(String mRequestTag, IMiddlewareResponse mIMiddlewareResponse) {
        this.mRequestTag = mRequestTag;
        this.mIMiddlewareResponse = mIMiddlewareResponse;
    }

    public interface IMiddlewareResponse<T> {
        ///void onResponse(Object items,int RequestCode);
        // void onErrorResponse(VolleyError error, int RequestCode);
        void onResponse(T items);

        void onErrorResponse(VolleyError error);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mContext = ScarletApplication.getContext();

            //
            if (mContext != null)
                mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? BaseApiManager.class.getName() : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    // Parses pojo object to json object.
    protected JSONObject getParam(Object request) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject param = null;
        try {
            String sJSONString = mapper.writeValueAsString(request);
            param = new JSONObject(sJSONString);
        } catch (JSONException e) {
            // TODO log exception
        } catch (JsonProcessingException e) {
            // TODO log exception
        }
        return param;
    }


    // The requests that does not has(require)a parameters to be sent to get response and has a NORMAL priority.
    protected void makeCall(String sURL, int method, final boolean isAuthTokenRequired) {
        makeCall(sURL, null, method, isAuthTokenRequired, Request.Priority.NORMAL);
    }

    // The rquests who does not requies custom priority are set to NORMAL priority.
    protected void makeCall(String sURL, final Object param, int method, final boolean isAuthTokenRequired) {
        makeCall(sURL, getParam(param), method, isAuthTokenRequired, Request.Priority.NORMAL);
    }

    // Makes a call to server with headers and returns a response.
    protected void makeCall(String sURL, final JSONObject param, int method, final boolean isAuthTokenRequired, final Request.Priority priority) {

        final JsonObjectRequest request = new JsonObjectRequest(method, sURL, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mIMiddlewareResponse.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setError(error);

                    }
                }) {


            @Override
            public Priority getPriority() {
                return priority;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        addToRequestQueue(request, mRequestTag);
    }

    public void logError(VolleyError error, String message) {
        NetworkResponseException networkResponseException = new NetworkResponseException(mRequestTag + " " + message, error);
      //  ExceptionManager.logException(networkResponseException);
    }


    public void setError(VolleyError error) {
        String message = "Network error";
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            // filed request for network timeout
        }

        if (error.networkResponse != null) {
            if (error.networkResponse.data != null) {
                try {
                    String body = new String(error.networkResponse.data, "UTF-8");
                    JSONObject jsonObject = new JSONObject(body);

                    // Log error message to firebase
                    ObjectMapper objectMapper = new ObjectMapper();
                   /* BaseResponse baseResponse = objectMapper.readValue(jsonObject.toString(), BaseResponse.class);
                    if (baseResponse.getMessage() != null) {
                        message = baseResponse.getMessage();
                    }
                    logError(error, message);*/
                    mIMiddlewareResponse.onResponse(jsonObject);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }/* catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        } else {
            // TODO log exception with request tag
            logError(error, message);
            mIMiddlewareResponse.onErrorResponse(error);
        }
    }


    protected void makeRegisterCall(final String name, final String email , final String mobileno , final String password , final String action) {

            final String loginURL = ApiUrl.Register;

        final StringRequest request = new StringRequest(POST, loginURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mIMiddlewareResponse.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setError(error);

                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action",action);
                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobileno);
                params.put("password",password);

                return params;
            }


        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        addToRequestQueue(request, mRequestTag);
    }

}
