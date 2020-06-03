package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.eghl.sdk.EGHL;
import com.eghl.sdk.params.PaymentParams;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class EghlModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {

    private final ReactApplicationContext reactContext;

    @Nullable
    private Callback paymentCallback;

    private static final String PROD_HOST = "https://securepay.e-ghl.com/IPG/Payment.aspx";
    private static final String TEST_HOST = "https://test2pay.ghl.com/IPGSG/Payment.aspx";

    public EghlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);
        this.reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "Eghl";
    }

    @ReactMethod
    public void startPayment(ReadableMap params, Callback callback) {
        PaymentParams.Builder paramsBuilder = new PaymentParams.Builder();

        if (params.hasKey("TransactionType")) {
            paramsBuilder.setTransactionType(params.getString("TransactionType"));
        }

        if (params.hasKey("Amount")) {
            paramsBuilder.setAmount(params.getString("Amount"));
        }

        if (params.hasKey("CurrencyCode")) {
            paramsBuilder.setCurrencyCode(params.getString("CurrencyCode"));
        }

        if (params.hasKey("PaymentID")) {
            paramsBuilder.setPaymentId(params.getString("PaymentID"));
        }

        if (params.hasKey("OrderNumber")) {
            paramsBuilder.setOrderNumber(params.getString("OrderNumber"));
        }

        if (params.hasKey("PaymentDesc")) {
            paramsBuilder.setPaymentDesc(params.getString("PaymentDesc"));
        }

        if (params.hasKey("PymtMethod")) {
            paramsBuilder.setPaymentMethod(params.getString("PymtMethod"));
        }

        if (params.hasKey("CustEmail")) {
            paramsBuilder.setCustEmail(params.getString("CustEmail"));
        }

        if (params.hasKey("CustName")) {
            paramsBuilder.setCustName(params.getString("CustName"));
        }

        if (params.hasKey("CustPhone")) {
            paramsBuilder.setCustPhone(params.getString("CustPhone"));
        }

        if (params.hasKey("ServiceID")) {
            paramsBuilder.setServiceId(params.getString("ServiceID"));
        }

        if (params.hasKey("Password")) {
            paramsBuilder.setPassword(params.getString("Password"));
        }

        if (params.hasKey("LanguageCode")) {
            paramsBuilder.setLanguageCode(params.getString("LanguageCode"));
        }

        if (params.hasKey("PageTimeout")) {
            paramsBuilder.setPageTimeout(params.getString("PageTimeout"));
        }

        if (params.hasKey("MerchantName")) {
            paramsBuilder.setMerchantName(params.getString("MerchantName"));
        }

        if (params.hasKey("Prod")) {
            if (params.getBoolean("Prod")) {
                paramsBuilder.setPaymentGateway(PROD_HOST);
            } else {
                paramsBuilder.setPaymentGateway(TEST_HOST);
            }
        } else {
            paramsBuilder.setPaymentGateway(TEST_HOST);
        }

        paramsBuilder.setMerchantReturnUrl("SDK");

        EGHL.getInstance().executePayment(paramsBuilder.build(), reactContext.getCurrentActivity());
        paymentCallback = callback;
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == EGHL.REQUEST_PAYMENT && data != null) {
            switch (resultCode) {
                case EGHL.TRANSACTION_SUCCESS:
                case EGHL.TRANSACTION_AUTHORIZED:
                    if (paymentCallback != null) {
                        paymentCallback.invoke("OK");
                    }
                    break;
                case EGHL.TRANSACTION_FAILED:
                case EGHL.TRANSACTION_CANCELLED:
                default:
                    if (paymentCallback != null) {
                        paymentCallback.invoke("ERROR");
                    }
                    break;
            }
        } else if (resultCode == EGHL.TRANSACTION_CANCELLED) {
            if (paymentCallback != null) {
                paymentCallback.invoke("ERROR");
            }
        } else {
            if (paymentCallback != null) {
                paymentCallback.invoke("ERROR");
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {
        paymentCallback = null;
    }
}
