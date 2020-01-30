package ua.betagroup.betagroup.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import javax.inject.Inject;

import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;

public class MainActivity extends AppCompatActivity implements Imvp.IMainView {
    @Inject
    Imvp.IMainPresenter mainPresenter;

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerIComponent.builder().build().inject(this);
        mainPresenter.attachView(this);

        webView = findViewById(R.id.webpage);
        webView.loadUrl("https://www.google.com.ua/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
