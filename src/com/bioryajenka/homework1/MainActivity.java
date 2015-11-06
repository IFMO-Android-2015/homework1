package com.bioryajenka.homework1;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView;
	private boolean wasOperator;
	private boolean lastIsDigit;
	private boolean lastIsZero;
	private boolean wasPoint;
	private boolean lastActionIsResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.textView);

		clear();
		lastActionIsResult = wasOperator = wasPoint = false;
		lastIsDigit = lastIsZero = true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("textView", "" + textView.getText());
	    savedInstanceState.putBoolean("wasOperator", wasOperator);
	    savedInstanceState.putBoolean("lastIsDigit", lastIsDigit);
	    savedInstanceState.putBoolean("lastIsZero", lastIsZero);
	    savedInstanceState.putBoolean("wasPoint", wasPoint);
	    savedInstanceState.putBoolean("lastActionIsResult", lastActionIsResult);
	    
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    textView.setText(savedInstanceState.getString("textView"));
	    wasOperator = savedInstanceState.getBoolean("wasOperator");
	    lastIsDigit = savedInstanceState.getBoolean("lastIsDigit");
	    lastIsZero = savedInstanceState.getBoolean("lastIsZero");
	    wasPoint = savedInstanceState.getBoolean("wasPoint");
	    lastActionIsResult = savedInstanceState.getBoolean("lastActionIsResult");
	}

	private void tryExecute() {
		if (!wasOperator) {
			return;
		}
		if (!lastIsDigit && !wasPoint) {
			return;
		}
		if (!lastIsDigit) {
			append('0');
		}
		execute();
		wasOperator = false;
		lastIsDigit = true;
		wasPoint = false;
		lastActionIsResult = true;
	}

	private void execute() {
		String text = "" + textView.getText();
		char op = ' ';
		if (text.contains("+")) {
			op = '+';
		}
		if (text.contains("*")) {
			op = '*';
		}
		if (text.contains("/")) {
			op = '/';
		}

		String ar[] = null;
		if (op == ' ') {
			int cnt = 0, last = 0;
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) == '-') {
					cnt++;
					last = i;
				}
			}
			if (cnt == 2 || (cnt == 1 && !text.startsWith("-"))) {
				op = '-';
				ar = new String[2];
				ar[0] = text.substring(0, last);
				ar[1] = text.substring(last + 1, text.length());
			}
		} else {
			ar = text.split("\\" + op);
		}
		if (op == ' ') {
			throw new RuntimeException("For some reason execute() method executed "
					+ "while viewText had had no operators");
		}

		BigDecimal a = new BigDecimal(ar[0]);
		BigDecimal b = new BigDecimal(ar[1]);
		BigDecimal c = null;

		switch (op) {
		case '+':
			c = a.add(b);
			break;
		case '-':
			c = a.subtract(b);
			break;
		case '*':
			c = a.multiply(b);
			break;
		case '/':
			c = a.divide(b, 15, BigDecimal.ROUND_CEILING);
			break;

		default:
			break;
		}

		String res = c.toString();
		while (res.endsWith("0")) {
			res = res.substring(0, res.length() - 1);
		}
		if (res.endsWith(".")) {
			res += "0";
		}
		textView.setText(res);
		updateSize();
	}

	private void clear() {
		textView.setText("0");
		updateSize();
	}

	/**
	 * Дописывает {@literal c}.
	 * 
	 * @param c
	 *            - {@literal c}
	 */
	private void append(char c) {
		String text = "" + textView.getText();
		textView.setText(text + c);
		updateSize();
	}

	/**
	 * Меняет последний символ на {@literal c}.
	 * 
	 * @param c
	 *            - {@literal c}
	 */
	private void rewrite(char c) {
		String text = "" + textView.getText();
		textView.setText(text.substring(0, text.length() - 1) + c);
		updateSize();
	}
	
	private void setDefaultFontSize() {
		float kg = getResources().getDimension(R.dimen.textview_font_size);
		DisplayMetrics m = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(m);
		kg /= m.density;
		textView.setTextSize(kg);
	}

	private void updateSize() {
		setDefaultFontSize();
		if (countLines() >= 3) {
			for (int i = 6; i < 100; i++) {
				textView.setTextSize(i);
				if (countLines() >= 3) {
					textView.setTextSize(i - 2);
					break;
				}
			}
		}
	}

	private int countLines() {
		int chrs = (int) textView.getPaint().measureText("" + textView.getText());
		int rhs = textView.getMeasuredWidth();
		if (rhs == 0) {
			return 0;
		}
		int rows = chrs / rhs;
		return rows;
	}

	public void onClick(View view) {
		if (textView == null) {
			return;
		}
		Button b = (Button) view;
		char c = b.getText().charAt(0);
		switch (c) {
		case '=':
			tryExecute();
			String text = "" + textView.getText();
			lastIsZero = text.equals("0");
			break;
		case 'C':
			clear();
			wasOperator = false;
			lastIsDigit = true;
			wasPoint = false;
			lastActionIsResult = false;
			lastIsZero = true;
			break;
		case '+':
		case '-':
		case '*':
		case '/':
			tryExecute();
			if (!lastIsDigit) {
				if (wasPoint) {
					append('0');
				} else {
					rewrite(c);
					break;
				}
			}
			append(c);
			wasOperator = true;
			wasPoint = false;
			lastIsDigit = false;
			lastActionIsResult = false;
			lastIsZero = false;
			break;
		case '.':
			if (wasPoint) {
				break;
			}
			if (!lastIsDigit) {
				append('0');
			}
			append('.');
			wasPoint = true;
			lastIsDigit = false;
			lastActionIsResult = false;
			lastIsZero = false;
			break;
		case '0':
			if (lastIsZero) {
				break;
			}
			append(c);
			lastIsZero = !lastIsDigit;
			lastIsDigit = true;
			lastActionIsResult = false;
			break;
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			if (lastActionIsResult) {
				lastActionIsResult = false;
				clear();
				rewrite(c);
				break;
			}
			if (lastIsZero) {
				rewrite(c);
			} else {
				append(c);
			}
			lastIsZero = false;
			lastIsDigit = true;
			lastActionIsResult = false;
			break;

		default:
			throw new RuntimeException("Layout has invalid button names");
		}
	}
}