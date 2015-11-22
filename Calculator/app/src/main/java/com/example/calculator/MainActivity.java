package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static Engine engine = new Engine();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textViewDisplay);
        textView.setText(engine.toString());
    }

    public void onClick(View view) {
        Button button = (Button) view;
        engine.put(button.getText().charAt(0));
        textView.setText(engine.toString());
    }
}
