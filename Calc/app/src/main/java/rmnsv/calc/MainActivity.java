package rmnsv.calc;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView out;
    TextView total;
    TextView operation;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button zero;
    Button point;
    Button equals;
    Button add;
    Button sub;
    Button multiply;
    Button divide;
    String expr = "";
    String overall = "";
    double first;
    double second;
    boolean enter_second = false;
    byte last_were = 0;
    boolean allowed = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = (TextView) findViewById(R.id.out);
        operation = (TextView) findViewById(R.id.operation);
        total = (TextView) findViewById(R.id.total);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        point = (Button) findViewById(R.id.point);
        equals = (Button) findViewById(R.id.equals);
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                DecimalFormat format = new DecimalFormat();
                format.setDecimalSeparatorAlwaysShown(false);

                switch (view.getId()) {

                    case R.id.one:
                        expr += '1';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.two:
                        expr += '2';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.three:
                        expr += '3';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.four:
                        expr += '4';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.five:
                        expr += '5';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.six:
                        expr += '6';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.seven:
                        expr += '7';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.eight:
                        expr += '8';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.nine:
                        expr += '9';
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.zero:
                        if (!Objects.equals(expr, "") && (!Objects.equals(expr, "0")))
                            expr += '0';
                        else
                            expr = "0";
                        out.setText(expr);
                        allowed = true;
                        break;

                    case R.id.point:
                        if (Objects.equals(expr, ""))
                            expr = "0.";
                        if ((!Objects.equals(expr, "")) && !(expr.contains(".")))
                            expr += '.';
                        out.setText(expr);
                        break;

                    case R.id.add:
                        if (enter_second && allowed) {
                            second = Double.parseDouble(expr);
                            if (last_were != 5) {
                                out.setText(format.format(String.valueOf(second + first)));
                                first = second + first;
                                total.setText(overall + "+");
                            }
                            overall += String.valueOf(second) + "+";
                            total.setText(overall);
                        } else
                            if (allowed) {
                                first = Double.parseDouble(expr);
                                enter_second = true;
                                overall += String.valueOf(first) + "+";
                            }
                        expr = "";
                        operation.setText("+");
                        last_were = 1;
                        allowed = false;
                        break;

                    case R.id.sub:
                        if (enter_second && allowed) {
                            second = Double.parseDouble(expr);
                            if (last_were != 5) {
                                out.setText(String.valueOf(first - second));
                                first = first - second;
                            }
                            overall += String.valueOf(second) + "-";
                            total.setText(overall);
                        } else
                            if (allowed) {
                                first = Double.parseDouble(expr);
                                enter_second = true;
                                overall += String.valueOf(first) + "-";
                            }
                        expr = "";
                        operation.setText("-");
                        last_were = 2;
                        allowed = false;
                        break;

                    case R.id.multiply:
                        if (enter_second && allowed) {
                            second = Double.parseDouble(expr);
                            if (last_were != 5) {
                                out.setText(String.valueOf(second * first));
                                first = second * first;
                            }
                            overall += String.valueOf(second) + "*";
                            total.setText(overall);
                        } else
                            if (allowed) {
                                first = Double.parseDouble(expr);
                                enter_second = true;
                                overall += String.valueOf(first) + "*";
                            }
                        expr = "";
                        operation.setText("*");
                        last_were = 3;
                        allowed = false;
                        break;

                    case R.id.divide:
                        if (enter_second && allowed) {
                            second = Double.parseDouble(expr);
                            if (last_were != 5) {
                                out.setText(String.valueOf(first / second));
                                first = first / second;
                            }
                            overall += String.valueOf(second) + "/";
                            total.setText(overall);
                        } else
                            if (allowed) {
                                first = Double.parseDouble(expr);
                                enter_second = true;
                                overall += String.valueOf(first) + "/";
                            }
                        expr = "";
                        operation.setText("/");
                        last_were = 4;
                        allowed = false;
                        break;

                    case R.id.equals:
                        if (enter_second) {
                            second = Double.parseDouble(expr);
                            switch (last_were) {
                                case 1:
                                    out.setText(String.valueOf(first + second));
                                    total.setText(overall + String.valueOf(second) + "=" + String.valueOf(first + second));
                                    first = first + second;
                                    break;
                                case 2:
                                    out.setText(String.valueOf(first - second));
                                    total.setText(overall + String.valueOf(second) + "=" + String.valueOf(first - second));
                                    first = first - second;
                                    break;
                                case 3:
                                    out.setText(String.valueOf(first * second));
                                    total.setText(overall + String.valueOf(second) + "=" + String.valueOf(first * second));
                                    first = first * second;
                                    break;
                                case 4:
                                    out.setText(String.valueOf(first / second));
                                    total.setText(overall + String.valueOf(second) + "=" + String.valueOf(first / second));
                                    first = first / second;
                                    break;
                            }
                            operation.setText("=");
                            last_were = 5;
                            break;
                        }

                }
            }
        };

        one.setOnClickListener(onClickListener);
        two.setOnClickListener(onClickListener);
        three.setOnClickListener(onClickListener);
        four.setOnClickListener(onClickListener);
        five.setOnClickListener(onClickListener);
        six.setOnClickListener(onClickListener);
        seven.setOnClickListener(onClickListener);
        eight.setOnClickListener(onClickListener);
        nine.setOnClickListener(onClickListener);
        zero.setOnClickListener(onClickListener);
        equals.setOnClickListener(onClickListener);
        add.setOnClickListener(onClickListener);
        sub.setOnClickListener(onClickListener);
        multiply.setOnClickListener(onClickListener);
        divide.setOnClickListener(onClickListener);
        point.setOnClickListener(onClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
