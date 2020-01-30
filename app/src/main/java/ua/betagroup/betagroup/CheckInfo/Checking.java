package ua.betagroup.betagroup.CheckInfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseObject;

import javax.inject.Inject;

import ua.betagroup.betagroup.Cap.Cap;
import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;
import ua.betagroup.betagroup.WebView.MainActivity;

public class Checking extends AppCompatActivity implements Imvp.ICheckingView {
    @Inject
    Imvp.ICheckingPresenter checkingPresenter;


    private WifiManager wm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking_layout);

        ConnectivityManager connectivityManager = (ConnectivityManager) Checking.this.getSystemService(Context.CONNECTIVITY_SERVICE);;
        DaggerIComponent.builder().build().inject(this);
        checkingPresenter.attachView(this);
        try{
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId(getString(R.string.back4app_app_id))
                    .clientKey(getString(R.string.back4app_client_key))
                    .server(getString(R.string.back4app_server_url))
                    .build()
            );
            wm = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());


            String model = Build.MODEL;
            checkingPresenter.checkDevice(ip, connectivityManager, model);
        }catch (Exception e) {
            Log.w("Error", e);
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
}
