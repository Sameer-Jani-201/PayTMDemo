package com.paytmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//Created by Sameer Jani 01 Feb 2018.

public class MainActivity extends AppCompatActivity {

//Notes :: at all where ? given there is user has to enter suggested value.

    private String orderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public synchronized void onStartTransaction(View view) {
        PaytmPGService service = PaytmPGService.getStagingService();

        orderID = "OrderID" + System.currentTimeMillis();


        String checkSum = checksumGeneration("MerchantID?", orderID, "CUST_ID?", "Retail", "WAP", "ammount?", "APP_STAGING", null, null, "merchant");//Creating checksum to pass it for transaction.

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "MerchantID?");//Replace your merchant id here.
        paramMap.put("ORDER_ID", orderID);//This is entered by yourself but must be unique. Any unique here unique created by system timestamp.
        paramMap.put("CUST_ID", "CUST_ID?");//Created by yourself.//Add your customer id like CUST123
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");//fix retail
        paramMap.put("CHANNEL_ID", "WAP");//Fix WAP for Mobile App and WEB for website
        paramMap.put("TXN_AMOUNT", "ammount?");//Transaction Ammount like 1.00
        paramMap.put("WEBSITE", "APP_STAGING");//APP_STAGING or WEB_STAGING or your own website host name if you have entered (STAGING for given name)
        paramMap.put("EMAIL", "email_id?");//Email id of customer from whom you want to take payment(Optional)
        paramMap.put("MOBILE_NO", "mobile_number?");//Mobile number of customer from whom you want to take payment(Optional)
        //paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/payTMSecured/app/auth/login");//Callback url if you want to set yourself.
        paramMap.put("THEME", "merchant");//DEFAULT merchant
        paramMap.put("CHECKSUMHASH", checkSum);//Checksum generate by given above data need to pass here.

        PaytmOrder order = new PaytmOrder(paramMap);
        //PaytmClientCertificate clientCertificate = new PaytmClientCertificate(password,filename);
        service.initialize(order, null);

        service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        if (inErrorMessage != null)
                            Toast.makeText(MainActivity.this, inErrorMessage, Toast.LENGTH_SHORT).show();
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.e("LOG", "Payment Transaction : " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                        Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                        // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {

                        if (inErrorMessage != null)
                            Toast.makeText(MainActivity.this, inErrorMessage, Toast.LENGTH_SHORT).show();
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,

                                                      String inErrorMessage, String inFailingUrl) {
                        try {
                            if (inErrorMessage != null)
                                Toast.makeText(MainActivity.this, inErrorMessage + "inFailing Url : " + inFailingUrl + " iniErrorCode : " + iniErrorCode, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.e("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }

    public synchronized String checksumGeneration(String mid, String orderId, String cust_id, String industry_type_id, String channel_id, String amount, String website, String email, String mobilenumber, String theme) {
        TreeMap<String, String> paramMap = new TreeMap<String, String>();

        paramMap.put("MID", mid);
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", cust_id);
        paramMap.put("INDUSTRY_TYPE_ID", industry_type_id);
        paramMap.put("CHANNEL_ID", channel_id);
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("WEBSITE", website);
        if (email != null)
            paramMap.put("EMAIL", email);//Optional
        if (mobilenumber != null)
            paramMap.put("MOBILE_NO", mobilenumber);//Optional
        paramMap.put("THEME", theme);
        String checkSum = "";
        try {
            checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("Merchant_Private_Key?", paramMap);
            paramMap.put("CHECKSUMHASH", checkSum);

            Log.e("Paytm Checksum: ", checkSum);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return checkSum;
    }
}
