package ua.betagroup.betagroup.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import javax.inject.Inject;

import ua.betagroup.betagroup.Constants;
import ua.betagroup.betagroup.MyBroadCastReceiver;
import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;

public class MainActivity extends AppCompatActivity implements Imvp.IMainView {
    @Inject
    Imvp.IMainPresenter mainPresenter;

    private WebView webView;
    private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

        DaggerIComponent.builder().build().inject(this);
        mainPresenter.attachView(this);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;


        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

        setUpAlarm(this, new Intent(this, MyBroadCastReceiver.class), 28_800_000);

        createWebView(mainPresenter.setUrl());
    }


    public static void setUpAlarm(final Context context, final Intent intent, final int timeInterval)
    {
        final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final PendingIntent pi = PendingIntent.getBroadcast(context, timeInterval, intent, 0);
        am.cancel(pi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            final AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + timeInterval, pi);
            am.setAlarmClock(alarmClockInfo, pi);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInterval, pi);
        else
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInterval, pi);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void createWebView(String url){
        webView = findViewById(R.id.webpage);
        webView.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkLoads (false);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(url);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
