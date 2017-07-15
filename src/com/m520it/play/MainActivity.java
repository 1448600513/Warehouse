package com.m520it.play;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnTouchListener,
		OnSeekBarChangeListener {
	private MediaPlayer mPlay;
	private SurfaceView mSurf;
	private SeekBar mSkb;
	private Timer mSkbTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton = (Button) findViewById(R.id.button);
		mSurf = (SurfaceView) findViewById(R.id.surf);
		// ��SurfaceView����һ�������ļ�����

		mSurf.setOnTouchListener(this);
		mSkb = (SeekBar) findViewById(R.id.vedio_skb);

		// ��SeekBar����һ���϶��¼��ļ�����
		mSkb.setOnSeekBarChangeListener(this);
		// ����2��� �������Զ�����
		SkbAfter2Seconds();
		// SurfaceHolder������沥�ŵ�һ��������
		SurfaceHolder holder = mSurf.getHolder();

		// ��holder����һ��SurfaceView׼�����ݵļ�����

		holder.addCallback(new Callback() {

			private Timer mTimer;

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			// mSurf ׼����ϵ�ʱ����õ�
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					mPlay = new MediaPlayer();
					// ����������Ƶ����
					Uri uri = Uri.parse("http://192.168.14.130:8080/oppo.3gp");
					mPlay.setDataSource(MainActivity.this, uri);
					mPlay.prepare();
					mPlay.start();
					// ������Ƶ�����
					mPlay.setDisplay(holder);
					// ���ý������ܽ���
					mSkb.setMax(mPlay.getDuration());
					mTimer = new Timer();
					mTimer.schedule(new TimerTask() {

						@Override
						public void run() {

						}
					}, 500, 500);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}
		});

	}

	public void SkbAfter2Seconds() {
		mSkbTimer = new Timer();
		mSkbTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mSkb.setVisibility(View.GONE);
					}
				});
			}
		}, 2 * 1000);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// ������mSkb��ʾ����
		mSkb.setVisibility(View.VISIBLE);
		SkbAfter2Seconds();
		return false;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	// ���϶���ɺ� ���Ȼ�ȡSeekBar��ǰ���� �޸���Ƶ�Ĳ��Ž���
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		mPlay.seekTo(progress);
	}

	boolean x = true;
	private Button mButton;

	public void stopVido(View v) {
		if (x == true) {
			mPlay.pause();
			mButton.setText("��ʼ");
			x = false;
		} else if (x == false) {
			mPlay.start();
			mButton.setText("��ͣ");
			x = true;
		}
	}

}
