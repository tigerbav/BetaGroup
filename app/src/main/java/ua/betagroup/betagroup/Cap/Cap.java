package ua.betagroup.betagroup.Cap;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ua.betagroup.betagroup.Constants;
import ua.betagroup.betagroup.Dagger.DaggerIComponent;
import ua.betagroup.betagroup.Imvp;
import ua.betagroup.betagroup.R;

public class Cap extends AppCompatActivity implements Imvp.ICapView {
    @Inject
    Imvp.ICapPresenter capPresenter;

    private Button button;
    private TextView counter;
    private TextView timer;
    private int currentCounter = 0;
    private SharedPreferences myPreferences;
    private Display display;
    private int width;
    private int height;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cap_layout);

        DaggerIComponent.builder().build().inject(this);
        capPresenter.attachView(this);

        button = findViewById(R.id.play);
        counter = findViewById(R.id.counter);
        timer = findViewById(R.id.timer);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(Cap.this);
        display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        playGame();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCounter++;
                counter.setText(Constants.SCORE_TEXT + currentCounter);
                changeButton();
            }
        });
    }

    private void playGame() {

        new AlertDialog.Builder(Cap.this)
                .setTitle(Constants.TITLE_GAME)
                .setCancelable(false)
                .setMessage(Constants.RULES + myPreferences.getInt(Constants.RECORD, 0))
                .setNegativeButton(Constants.PLAY, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        new CountDownTimer(Constants.MINUTE, Constants.SECOND){

                            @Override
                            public void onTick(long l) {
                                timer.setText(Constants.LEFT_TIME + l/Constants.SECOND + Constants.SEC_TEXT);
                            }

                            @Override
                            public void onFinish() {
                                int record = myPreferences.getInt(Constants.RECORD, 0);
                                if(record  < currentCounter) {
                                    SharedPreferences.Editor myEditor = myPreferences.edit();
                                    myEditor.putInt(Constants.RECORD, currentCounter);
                                    myEditor.commit();
                                }
                                currentCounter = 0;
                                counter.setText(Constants.SCORE_TEXT + "0");
                                playGame();
                            }
                        }.start();
                    }
                })
        .create()
        .show();
    }


    private void changeButton() {
        int btnWidth = button.getWidth();
        int btnHeight = button.getHeight();

        int maxPosWidth = width - btnWidth - 8 * Constants.MARGIN;
        int maxPosHeight = height - btnHeight - 8 * Constants.MARGIN;

        int minPosWidth = 0;
        int minPosHeight = 0;

        LinearLayout.LayoutParams linear_lay = new LinearLayout.LayoutParams(btnWidth, btnHeight);
        linear_lay.leftMargin = (int) (minPosWidth + Math.random() * maxPosWidth);
        linear_lay.topMargin = (int) (minPosHeight + Math.random() * maxPosHeight);
        button.setLayoutParams(linear_lay);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        capPresenter.detachView();
    }
}
