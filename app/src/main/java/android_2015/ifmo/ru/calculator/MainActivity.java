package android_2015.ifmo.ru.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private TextView view1, view2;
    private CalcEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = (TextView) findViewById(R.id.textView1);
        view2 = (TextView) findViewById(R.id.textView2);
        if (savedInstanceState != null && savedInstanceState.containsKey("engine")) {
            engine = savedInstanceState.getParcelable("engine");
        } else {
            engine = new CalcEngine();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable("engine", engine);
    }

    public void numberClick(View view) {
        char number = ((Button) view).getText().charAt(0);
        engine.addChar(number);
        update();
    }


    public void signChangeClick(View view) {
        engine.changeSign();
        update();
    }

    public void operatorClick(OperationType ot) {
        try {
            engine.doOperation(ot);
        } catch (Exception ex) {
            Toast toast = makeText(getApplicationContext(), "Ошибка вычисления", Toast.LENGTH_SHORT);
            toast.show();
            engine.clear();
        } finally {
            update();
        }

    }

    public void plusClick(View view) {
        operatorClick(OperationType.Plus);
        update();
    }

    private void update() {
        view1.setText(engine.getNum1());
        view2.setText(engine.getNum2());
    }

    public void evalClick(View view) {
        engine.doOperation(OperationType.Eval);
        update();
    }

    public void clearClick(View view) {
        engine.clear();
        update();
    }

    public void mulClick(View view) {
        operatorClick(OperationType.Mul);
        update();
    }

    public void divClick(View view) {
        operatorClick(OperationType.Div);
        update();
    }

    public void minusClick(View view) {
        operatorClick(OperationType.Minus);
        update();
    }
}
