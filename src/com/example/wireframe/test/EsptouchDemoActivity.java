package com.example.wireframe.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.demo_activity.EspWifiAdminSimple;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.example.wireframe.R;
import com.example.wireframe.db.QueryElec;

public class EsptouchDemoActivity extends Activity {

    private static final String TAG = "EsptouchDemoActivity";

    private TextView mTvApSsid;

    private EditText mEdtApPassword;

    private Button mBtnConfirm;

    private EspWifiAdminSimple mWifiAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esptouch_demo);

        mWifiAdmin = new EspWifiAdminSimple(this);
        mTvApSsid = (TextView) findViewById(R.id.tvApSssidConnected);
        mEdtApPassword = (EditText) findViewById(R.id.edtApPassword);
        mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
        mBtnConfirm.setEnabled(true);
        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mEdtApPassword.setText("uiyuy");
                if (v == mBtnConfirm) {
                    String apSsid = mTvApSsid.getText().toString();
                    String apPassword = mEdtApPassword.getText().toString();
                    if (__IEsptouchTask.DEBUG) {
                        Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                                + ", " + " mEdtApPassword = " + apPassword);
                    }
                    new EsptouchAsyncTask2().execute(apSsid, apPassword);
                }
                QueryElec qe =new QueryElec(EsptouchDemoActivity.this);
                qe.AddDevice("bulb");
                startActivity(new Intent(EsptouchDemoActivity.this, SlidingTest.class));
            }
        });







    }

    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            mTvApSsid.setText(apSsid);
        } else {
            mTvApSsid.setText("");
        }
        // check whether the wifi is connected
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        mBtnConfirm.setEnabled(!isApSsidEmpty);
    }



    private class EsptouchAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog mProgressDialog;

        private IEsptouchTask mEsptouchTask;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(EsptouchDemoActivity.this);
            mProgressDialog
                    .setMessage("����ģʽ ����������, �����Ե�...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (__IEsptouchTask.DEBUG) {
                        Log.i(TAG, "progress dialog is canceled");
                    }
                    if (mEsptouchTask != null) {
                        mEsptouchTask.interrupt();
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Waiting...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String apSsid = params[0];
            String apPassword = params[1];
            mEsptouchTask = new EsptouchTask(apSsid, apPassword,
                    EsptouchDemoActivity.this);
            boolean result = mEsptouchTask.execute();
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "Confirm");
            if (result) {
                mProgressDialog.setMessage("���óɹ�");
            } else {
                mProgressDialog.setMessage("����ʧ��");
            }
        }
    }

    private class EsptouchAsyncTask2 extends AsyncTask<String, Void, IEsptouchResult> {

        private ProgressDialog mProgressDialog;

        private IEsptouchTask mEsptouchTask;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(EsptouchDemoActivity.this);
            mProgressDialog
                    .setMessage("����ģʽ ����������, �����Ե�...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (__IEsptouchTask.DEBUG) {
                        Log.i(TAG, "progress dialog is canceled");
                    }
                    if (mEsptouchTask != null) {
                        mEsptouchTask.interrupt();
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Waiting...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(false);
        }

        @Override
        protected IEsptouchResult doInBackground(String... params) {
            String apSsid = params[0];
            String apPassword = params[1];
            mEsptouchTask = new EsptouchTask(apSsid, apPassword,
                    EsptouchDemoActivity.this);
            IEsptouchResult result = mEsptouchTask.executeForResult();
            return result;
        }

        @Override
        protected void onPostExecute(IEsptouchResult result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "Confirm");
            if (result.isSuc()) {
                mProgressDialog.setMessage("���óɹ�, bssid = " + result.getBssid());
            } else {
                mProgressDialog.setMessage("����ʧ��");
            }
        }
    }
}
