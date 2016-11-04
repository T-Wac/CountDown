package com.twac.countdown;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private EditText edit_minute, edit_second;
	private Button btn_confirm, btn_start, btn_stop, btn_pause;
	private TextView text_time_minute, text_time_second;
	private int m = 0;
	private int s = 0;
	private Timer timer = null;
	private TimerTask task = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
	}

	public void initView() {
		edit_minute = (EditText) findViewById(R.id.edit_input_minute);
		edit_second = (EditText) findViewById(R.id.edit_input_second);
		btn_confirm = (Button) findViewById(R.id.button1);
		btn_start = (Button) findViewById(R.id.button_start);
		btn_stop = (Button) findViewById(R.id.button_stop);
		btn_pause = (Button) findViewById(R.id.button_pause);
		text_time_minute = (TextView) findViewById(R.id.text_show_minute);
		text_time_second = (TextView) findViewById(R.id.text_show_second);
		btn_confirm.setOnClickListener(this);
		btn_start.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		btn_stop.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			text_time_minute.setText(edit_minute.getText().toString());
			text_time_second.setText(edit_second.getText().toString());
			m = Integer.parseInt(edit_minute.getText().toString());
			s = Integer.parseInt(edit_second.getText().toString());
			break;
		case R.id.button_start:
			startTime();

			break;
		case R.id.button_pause:
			pauseTime();
			break;
		case R.id.button_stop:
			stopTime();
			break;
		default:
			break;
		}

	}

	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			text_time_minute.setText(msg.arg1 + "");
			text_time_second.setText(msg.arg2 + "");
			startTime();
		};
	};

	public void startTime() {
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				if (m > 0) {
					if (s > 0) {
						s--;
					}
					if (s == 0) {
						m--;
						s = 60;
						s--;
					}
				}
				if (m == 0) {
					if (s > 0) {
						s--;
					}
				}

				Message message = mhandler.obtainMessage();
				message.arg1 = m;
				message.arg2 = s;
				mhandler.sendMessage(message);

			}
		};

		timer.schedule(task, 1000);

	}

	public void pauseTime() {
		timer.cancel();
	}

	public void stopTime() {
		m = 0;
		s = 0;
		text_time_minute.setText(m + "" + m);
		text_time_second.setText(s + "" + s);
	}

}
