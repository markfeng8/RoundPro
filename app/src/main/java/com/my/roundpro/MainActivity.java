package com.my.roundpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private RoundProView roundview;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roundview = (RoundProView) findViewById(R.id.roundview);
    }

    public void start(View view) {

        roundview = (RoundProView) findViewById(R.id.roundview);

        new CountDownTimer(1000 * 60, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                if (progress <= 100) {
                    roundview.setProgress(progress);
                }

                if (progress == 100) {
                    Toast.makeText(MainActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
