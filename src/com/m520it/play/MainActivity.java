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
		// 给SurfaceView设置一个触摸的监听器

		mSurf.setOnTouchListener(this);
		mSkb = (SeekBar) findViewById(R.id.vedio_skb);

		// 给SeekBar设置一个拖动事件的监听器
		mSkb.setOnSeekBarChangeListener(this);
		// 过了2秒后 进度条自动隐藏
		SkbAfter2Seconds();
		// SurfaceHolder管理界面播放的一个控制器
		SurfaceHolder holder = mSurf.getHolder();

		// 给holder设置一个SurfaceView准备数据的监听器

		holder.addCallback(new Callback() {

			private Timer mTimer;

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			// mSurf 准备完毕的时候调用的
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					mPlay = new MediaPlayer();
					// 设置网络视频数据
					Uri uri = Uri.parse("http://192.168.14.130:8080/oppo.3gp");
					mPlay.setDataSource(MainActivity.this, uri);
					mPlay.prepare();
					mPlay.start();
					// 关联音频与界面
					mPlay.setDisplay(holder);
					// 设置进度条总进度
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
		// 首先让mSkb显示出来
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

	// 在拖动完成后 首先获取SeekBar当前进度 修改视频的播放进度
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
			mButton.setText("开始");
			x = false;
		} else if (x == false) {
			mPlay.start();
			mButton.setText("暂停");
			x = true;
		}
	}

}
