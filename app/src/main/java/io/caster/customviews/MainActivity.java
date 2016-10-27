package io.caster.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
  private static float maxSpeed = 0.8f;

  private ImageView imageView;
  private Boolean isRotating = null;

  private float mDegrees = 0;
  private Long prevTimestamp = null;
  private float speed;
  private float a;
  Random mRandom = new Random();
  float minX = 0.7f;
  float maxX = 1.2f;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    getWindow().getDecorView()
        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    setContentView(R.layout.main);
    imageView = (ImageView) findViewById(R.id.image);
    imageView.setOnTouchListener(this);
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      maxSpeed = mRandom.nextFloat() * (maxX - minX) + minX;
    }

    if (isRotating != null && isRotating) {
      isRotating = false;
    }

    while (isRotating != null) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    isRotating = true;
    prevTimestamp = System.currentTimeMillis();
    speed = 0;
    a = 0.0002f;
    new Thread(new Runnable() {
      @Override public void run() {
        mainThread();
      }
    }).start();

    return true;
  }

  private void mainThread() {
    while (isRotating) {
      long time = prevTimestamp;
      prevTimestamp = System.currentTimeMillis();
      time = prevTimestamp - time;

      speed += a * time;
      if (speed > maxSpeed) {
        speed = maxSpeed;
        a = -0.0001f;
      }

      if (speed < 0) {
        speed = 0;
        isRotating = false;
      }

      mDegrees += speed * time;
      imageView.setRotation(mDegrees);
    }
    isRotating = null;
    prevTimestamp = null;
  }
}
