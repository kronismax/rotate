package io.caster.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
  private ImageView imageView;
  private int mDegrees = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    imageView = (ImageView) findViewById(R.id.image);
    imageView.setOnTouchListener(this);
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    imageView.setRotation(mDegrees++);
    mDegrees += 5;
    return true;
  }
}
