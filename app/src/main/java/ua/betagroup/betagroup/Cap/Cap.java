package ua.betagroup.betagroup.Cap;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;

public class Cap extends AppCompatActivity implements Imvp.ICapView {
    @Inject
    Imvp.ICapPresenter capPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cap_layout);

        DaggerIComponent.builder().build().inject(this);
        capPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capPresenter.detachView();
    }
}
