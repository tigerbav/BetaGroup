package ua.betagroup.betagroup.CheckInfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.parse.Parse;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ua.betagroup.betagroup.Cap.Cap;
import ua.betagroup.betagroup.Constants;
import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;
import ua.betagroup.betagroup.WebView.MainActivity;

public class Checking extends AppCompatActivity implements Imvp.ICheckingView {
    @Inject
    Imvp.ICheckingPresenter checkingPresenter;


    private WifiManager wm;
    private NetworkInfo wifiInfo;
    private ConnectivityManager connectivityManager;
    private TelephonyManager tm;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                for (String attrName : map.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + map.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d("LOG_TAG", "error getting conversion data: " + s);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(Constants.AF_DEV_KEY, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(getApplication());

        setContentView(R.layout.checking_layout);

        connectivityManager = (ConnectivityManager) Checking.this.getSystemService(Context.CONNECTIVITY_SERVICE);;
        DaggerIComponent.builder().build().inject(this);
        checkingPresenter.attachView(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Checking.this);
        checkingPresenter.setSharedPreferences(sharedPreferences);

        try {
            if (hasConnection()) {
                Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId(getString(R.string.back4app_app_id))
                        .clientKey(getString(R.string.back4app_client_key))
                        .server(getString(R.string.back4app_server_url))
                        .build()
                );
                wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                AppsFlyerLib.getInstance().setDebugLog(true);
                tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String countryCodeValue = tm.getNetworkCountryIso();


                String model = Build.MODEL;
                checkingPresenter.checkDevice(ip, connectivityManager, model, countryCodeValue);
            } else
                openCap();
        }catch (Exception e){
            openCap();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkingPresenter.detachView();
    }

    @Override
    public void openCap() {
        Intent intent = new Intent(Checking.this, Cap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openWebView() {
        Intent intent = new Intent(Checking.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean hasConnection()
    {
        wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
            return true;
        wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
            return true;
        wifiInfo = connectivityManager.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }

}
