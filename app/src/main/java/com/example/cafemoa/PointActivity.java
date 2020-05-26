package com.example.cafemoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PointActivity extends AppCompatActivity {
    TextView pointCount = null;
    Button pointUp = null;
    Button pointDown = null;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
    }

    private void setup() {
        Button pointUp = (Button) findViewById(R.id.pointUp);
        Button pointDown = (Button) findViewById(R.id.pointDown);
        Button pointinitial =(Button)findViewById(R.id.pointinitial);
        final TextView seatsCount = (TextView) findViewById(R.id.seatsCount);

       pointUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                seatsCount.setText("" + count);
            }
        });
        pointDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                seatsCount.setText("" + count);
            }
        });

        pointinitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                seatsCount.setText("" + count);
            }
        });
    }
}
