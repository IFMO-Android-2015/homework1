package ifmo.ru.zernov.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private TextView input;
    private static final String EXPRESSION_MESSAGE = "expr";
    private String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (TextView) findViewById(R.id.input);

        findViewById(R.id.but9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("9");
            }
        });
        findViewById(R.id.but8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("8");
            }
        });
        findViewById(R.id.but7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("7");
            }
        });
        findViewById(R.id.but6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("6");
            }
        });
        findViewById(R.id.but5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("5");
            }
        });
        findViewById(R.id.but4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("4");
            }
        });
        findViewById(R.id.but3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("3");
            }
        });
        findViewById(R.id.but2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("2");
            }
        });
        findViewById(R.id.but1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("1");
            }
        });
        findViewById(R.id.but0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("0");
            }
        });
        findViewById(R.id.butPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("+");
            }
        })
        ;
        findViewById(R.id.butMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("-");
            }
        });
        findViewById(R.id.butMul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("*");
            }
        });
        findViewById(R.id.butDiv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("/");
            }
        });
        findViewById(R.id.butDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI(".");
            }
        });
        findViewById(R.id.butClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                updateUI("");
            }
        });
        findViewById(R.id.butErase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    expression = expression.substring(0, expression.length() - 1);
                }
                updateUI("");
            }
        });

        findViewById(R.id.butEq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    expression = evaluate();
                } catch (Exception e) {
                    expression = "";
                }
                updateUI("");
            }
        });
        if (savedInstanceState != null) {
            expression = savedInstanceState.getString(EXPRESSION_MESSAGE);
            updateUI("");
        }
    }

    private void updateUI(String add) {
        expression += add;
        input.setText(expression);
    }

    private int pointer = 0;
    private Token currentToken;
    private boolean neg = false;

    private String evaluate() {
        pointer = 0;
        double res = thirdPriority();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        nf.setGroupingUsed(false);
        return nf.format(res);
    }
    // Parser
    // *****

    private Token getToken() {
        if (pointer == expression.length()) {
            return new Token("");
        } else {
            char input = expression.charAt(pointer);
            pointer++;
            if (Character.isDigit(input)) {
                return new Token(nextNumber());
            } else {
                return new Token(Character.toString(input));
            }
        }
    }

    private double firstPriority() {
        currentToken = getToken();
        switch (currentToken.type) {
            case NUM: {
                double result = currentToken.numberValue;
                currentToken = getToken();
                return result;
            }
            default:
                return 0;
        }
    }

    private double secondPriority() {
        double left = firstPriority();
        while (true) {
            switch (currentToken.type) {
                case MUL:
                    left = left * firstPriority();
                    break;
                case DIV:
                    left = left / firstPriority();
                    break;
                default:
                    return left;
            }
        }
    }

    private double thirdPriority() {
        double left = secondPriority();
        while (true) {
            switch (currentToken.type) {
                case ADD:
                    left = left + secondPriority();
                    break;
                case SUB:
                    left = left - secondPriority();
                    break;
                default:
                    return left;
            }
        }
    }

    private String nextNumber() {
        int begin = pointer - 1;
        while (pointer < expression.length() && (Character.isDigit(expression.charAt(pointer)) || expression.charAt(pointer) == '.')) {
            pointer++;
        }
        if (!neg) {
            return expression.substring(begin, pointer);
        } else {
            neg = false;
            return "-" + expression.substring(begin, pointer);
        }
    }

    private static class Token {
        private double numberValue;
        private TokenType type;

        private enum TokenType {
            NUM, END, ADD, SUB, MUL, DIV
        }

        private Token(String input) {
            if (input.isEmpty()) {
                type = TokenType.END;
            } else {
                char first = input.charAt(0);
                if (Character.isDigit(first)) {
                    numberValue = Double.parseDouble(input);
                    type = TokenType.NUM;
                } else {
                    switch (first) {
                        case '+':
                            type = TokenType.ADD;
                            break;
                        case '-':
                            type = TokenType.SUB;
                            break;
                        case '*':
                            type = TokenType.MUL;
                            break;
                        case '/':
                            type = TokenType.DIV;
                            break;
                    }
                }
            }
        }
    }
    //******

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(EXPRESSION_MESSAGE, expression);
    }

}
