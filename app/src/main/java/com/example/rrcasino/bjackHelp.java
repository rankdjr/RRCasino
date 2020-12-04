package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class bjackHelp extends AppCompatActivity {

    private Button overviewBtn;
    private Button actionsBtn;
    private Button winningBtn;
    private TextView helpTitle;
    private TextView helpDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_bjack_help);
        overviewBtn = findViewById(R.id.overviewBtn);
        actionsBtn = findViewById(R.id.actionsBtn);
        winningBtn = findViewById(R.id.winningBtn);
        helpTitle = findViewById(R.id.helpTitle);
        helpDescription = findViewById(R.id.helpDescription);

        overviewBtn.setOnClickListener(click);
        actionsBtn.setOnClickListener(click);
        winningBtn.setOnClickListener(click);

    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.overviewBtn:
                    helpTitle.setText(R.string.Overview);
                    helpTitle.setVisibility(View.VISIBLE);
                    helpDescription.setText(R.string.bjOverviewDescription);
                    helpDescription.setVisibility(View.VISIBLE);
                    break;
                case R.id.actionsBtn:
                    helpTitle.setText(R.string.Actions);
                    helpTitle.setVisibility(v.VISIBLE);
                    helpDescription.setText(R.string.bjActionsDescription);
                    helpDescription.setVisibility(v.VISIBLE);
                    break;
                case R.id.winningBtn:
                    helpTitle.setText(R.string.HowToWin);
                    helpTitle.setVisibility(v.VISIBLE);
                    helpDescription.setText(R.string.bjWinningDescription);
                    helpDescription.setVisibility(v.VISIBLE);
                    break;
            }
        }
    };

}