package android.ifmo2015.edu.java.calculator;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private TextView screen;
    private State currentState;
    private StringBuilder buffer;
    private char operation;
    private Double number;
    private Boolean error_music_played;
    enum State {
        FIRST_NUMBER, OPERATION, SECOND_NUMBER, RESULT, ERROR;
    }

    private void setResult() {
        if (currentState == State.ERROR) {
            if (!error_music_played) {
                mp.start();
                error_music_played = true;
            }
            screen.setText("YOU IDIOT");
        }
        else {
            String answer = buffer.toString();
            Double res = Double.parseDouble(answer);
            if (Math.abs(res - res.intValue()) < 1e-7)
                answer = Integer.toString(res.intValue());
            else if (answer.length() >= 10) {
                if (Math.abs(res) > 1)
                    answer = new DecimalFormat("#.######E0", DecimalFormatSymbols.getInstance(Locale.US)).format(res);
                else
                    answer = new DecimalFormat("0.#######", DecimalFormatSymbols.getInstance(Locale.US)).format(res);
            }
            screen.setText(answer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.error);
        screen = (TextView) findViewById(R.id.screen);
        if (savedInstanceState == null) {
            error_music_played = false;
            number = 0.0;
            buffer = new StringBuilder("0");
            currentState = State.FIRST_NUMBER;
            operation = '\0';
        } else {
            number = savedInstanceState.getDouble("NUMBER");
            buffer = new StringBuilder(savedInstanceState.getString("BUFFER"));
            currentState = (State)savedInstanceState.getSerializable("CURRENT_STATE");
            operation = savedInstanceState.getChar("OPERATION");
            error_music_played = savedInstanceState.getBoolean("ERRORMUSIC");
        }
        setResult();
    }

    public void digitButton(View view) {
        if (currentState == State.RESULT || currentState == State.ERROR)
            this.clearButton(view);
        if (currentState == State.OPERATION) {
            buffer = new StringBuilder("0");
            currentState = State.SECOND_NUMBER;
        }
        String text = ((TextView) view).getText().toString();
        if (text.equals(".") && buffer.toString().contains("."))
            return;
        if (buffer.length() >= 9 && buffer.toString().charAt(0) != '-')
            return;
        buffer.append(text);
        if (buffer.charAt(0) == '0' && buffer.length() > 1 && buffer.charAt(1) != '.') buffer.deleteCharAt(0);
        screen.setText(buffer.toString());
    }

    public void clearButton(View view) {
        error_music_played = false;
        currentState = State.FIRST_NUMBER;
        number = 0.0;
        buffer = new StringBuilder("0");
        setResult();
    }

    public void changeSignButton(View view) {
        if (buffer.toString().equals("0"))
            return;
        if (buffer.toString().charAt(0) != '-')
            buffer = new StringBuilder("-" + buffer.toString());
        else
            buffer.deleteCharAt(0);
        screen.setText(buffer.toString());
    }

    public void resultButton(View view) {
        execute();
    }

    public void operationButton(View view) {
        if (currentState == State.SECOND_NUMBER) {
            execute();
        }
        number = Double.parseDouble(buffer.toString());
        currentState = State.OPERATION;
        operation = ((TextView) view).getText().toString().charAt(0);
    }

    private void execute() {
        Double cur = Double.parseDouble(buffer.toString());
        switch(operation) {
            case '\0':
                number = cur;
                break;
            case '+':
                number += cur;
                break;
            case '–':
                number -= cur;
                break;
            case '×':
                number *= cur;
                break;
            case '÷':
                if (Math.abs(cur - 0) < 1e-9) {
                    currentState = State.ERROR;
                    setResult();
                    return;
                }
                else
                    number /= cur;
                break;
            default:

        }
        buffer = new StringBuilder(number.toString());
        currentState = State.RESULT;
        operation = '\0';
        setResult();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("BUFFER", buffer.toString());
        outState.putChar("OPERATION", operation);
        outState.putSerializable("CURRENT_STATE", currentState);
        outState.putDouble("NUMBER", number);
        outState.putBoolean("ERRORMUSIC", error_music_played);
    }
}
