package com.example.rrscasino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    private Button gameSelectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSelectionButton= (Button) findViewById(R.id.playButton);
        gameSelectionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadGameSelection = new Intent (MainActivity.this, gameSelection.class);
                startActivity(intentLoadGameSelection);
            }
        });
    }

    boolean isTouch = false;
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventAction = event.getAction();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        switch(eventAction) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouch && (height-Y) > (height/2))
                    finishAffinity();
                break;
            /*case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
            */
        }
        return true;
    }
}