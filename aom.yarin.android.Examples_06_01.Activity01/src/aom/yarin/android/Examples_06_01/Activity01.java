package aom.yarin.android.Examples_06_01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import aom.yarin.android.Examples_06_01.R;

public class Activity01 extends Activity {

	private MIDIPlayer mMIDIPlayer = null;
	private boolean mbMusic = false;
	private TextView mTextView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView = (TextView) this.findViewById(R.id.TextView01);

		mMIDIPlayer = new MIDIPlayer(this);

		/* 装载数据 */
		// 取得活动的preferences对象.
		// Tom Xue: Java source - public static final int MODE_PRIVATE = 0x0000;
		SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);

		// 取得值.
		// Tom Xue: bmusic is used first here, so return "true"
		mbMusic = settings.getBoolean("bmusic", true);

		// initialization
		if (mbMusic) {
			mTextView.setText("当前音乐状态：开");
			mbMusic = true;
			mMIDIPlayer.PlayMusic();
		} else {
			mTextView.setText("当前音乐状态：关");
		}

	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			mTextView.setText("当前音乐状态：开");
			mbMusic = true;
			mMIDIPlayer.PlayMusic();
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			mTextView.setText("当前音乐状态：关");
			mbMusic = false;
			mMIDIPlayer.FreeMusic();
			break;
		}
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // this means the "back key"
			/* 这里我们在推出应用程序时保存数据 */
			// 取得活动的preferences对象.
			// Activity.MODE_PRIVATE = 0
			SharedPreferences uiState = getPreferences(0);

			// 取得编辑对象
			SharedPreferences.Editor editor = uiState.edit();

			// 添加值.
			// Tom Xue: But seems no meaning here, just to show data operation
			editor.putBoolean("bmusic", mbMusic);
			// Tom Xue: added to show the status of "mbMusic"
			Toast.makeText(this,
					"The key bmusic now is: " + Boolean.toString(mbMusic),
					Toast.LENGTH_LONG).show();

			// 提交保存
			editor.commit();
			if (mbMusic) {
				mMIDIPlayer.FreeMusic();
			}
			// Tom Xue: to confirm the keycode KEYCODE_BACK is the code of
			// the "back key"
//			Toast.makeText(this, "Exit the music player...", Toast.LENGTH_LONG)
//					.show();
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
