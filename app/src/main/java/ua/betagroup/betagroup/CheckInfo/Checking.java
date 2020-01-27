package ua.betagroup.betagroup.CheckInfo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;

public class Checking extends AppCompatActivity implements Imvp.ICheckingView {
    @Inject
    Imvp.ICheckingPresenter checkingPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checking_layout);

        DaggerIComponent.builder().build().inject(this);
        checkingPresenter.attachView(this);
        checkingPresenter.checkDevice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkingPresenter.detachView();
    }
}
