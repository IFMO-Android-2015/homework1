package homework01.calculate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String number = "";
    double ans = 0;
    ArrayList<String> expr = new ArrayList<>();
    ArrayList<String> oper = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    TextView textView;

    public void onClick(View view) {
        Button button = (Button)findViewById(view.getId());
        String text = (String) button.getText();
        textView = (TextView) findViewById(R.id.editText);

        if (text.equals("AC")) {
            number = "";
            expr.clear();
            textView.setText("");
        } else if (text.equals(".")) {
            if (!number.contains(".") && !expr.contains(String.valueOf(ans))) {
                number += ".";
                textView.append(text);
            }
        } else if (oper.contains(text)) {
            if ((expr.size() != 0) && (!oper.contains(expr.get(expr.size() - 1)))) {
                expr.add(text);
            } else {
                if (!number.isEmpty()) {
                    expr.add(number);
                }
                if ((expr.size() != 0) && (oper.contains(expr.get(expr.size() - 1)))) {
                    expr.set(expr.size() - 1, text);
                } else {
                    expr.add(text);
                }
            }
            number = "";
        } else if (text.equals("+/-")) {
            if (number.isEmpty()) {
                if (ans != 0) {
                    ans = -ans;
                    expr.clear();
                    expr.add(String.valueOf(ans));
                    textView.setText(String.valueOf(ans));
                }
            } else {
                if (number.charAt(0) == '-') {
                    number = number.substring(1);
                } else {
                    number = "-" + number;
                }
                textView.setText(number);
            }
        } else if (text.equals("=")) {
            if (expr.isEmpty()) {
                ans = 0.0;
            } else {
                expr.add(number);
                parse();
            }
            textView.setText(String.valueOf(ans));
            number = "";
        } else if (text.equals("Del")) {
            if (!number.isEmpty()) {
                number = number.substring(0, number.length() - 1);
                textView.setText(number);
            }
        } else {
            if (number.isEmpty() || number.equals(".")) {
                if (expr.size() != 0) {
                    if (!oper.contains(expr.get(expr.size() - 1))) {
                        expr.clear();
                    }
                }
                number = "";
                textView.setText("");
            }
            textView.append(text);
            number += text;
        }
    }

    public void parse() {
        double chs;
        ans = Double.parseDouble(expr.get(0));
        if (expr.get(expr.size() - 1).isEmpty()) {
            expr.remove(expr.size() - 1);
            expr.remove(expr.size() - 2);
        }
        for (int i = 2; i < expr.size(); i += 2) {
            chs = Double.parseDouble(expr.get(i));
            String op = expr.get(i - 1);
            if (op.equals("+")) {
                ans += chs;
            } else if (op.equals("-")) {
                ans -= chs;
            } else if (op.equals("*")) {
                ans *= chs;
            } else if (op.equals("/")) {
                ans /= chs;
            }
        }
        expr.clear();
        expr.add(String.valueOf(ans));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("number", number);
        outState.putStringArrayList("expression", expr);
        String state = textView.getText().toString();
        outState.putString("state", state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        number = savedInstanceState.getString("number");
        expr = savedInstanceState.getStringArrayList("expression");
        textView = (TextView) findViewById(R.id.editText);
        textView.setText(savedInstanceState.getString("state"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
