package com.zv.geochat.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zv.geochat.Constants;


public class MessageNotifierConfig implements SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = "MessageNotifierConfig";
    private Context mContext;
    private boolean playSound;
    private boolean vibrate;
    private Uri soundUri;
    private String maxMessage;

    public MessageNotifierConfig(Context context) {
        mContext = context;
        loadFromPreferences();
        PreferenceManager.getDefaultSharedPreferences(context)
                .registerOnSharedPreferenceChangeListener(this);
    }

    private void loadFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        playSound = prefs.getBoolean(Constants.PREF_KEY_NOTIFICATIONS_NEW_MESSAGE, false);
        vibrate = prefs.getBoolean(Constants.PREF_KEY_NOTIFICATIONS_NEW_MESSAGE_VIBRATE, false);
        maxMessage = prefs.getString(Constants.PREF_KEY_MAX_MESSAGE, "2 messages");
        if (isPlaySound()) {
            String discoveryRingtone = prefs.getString(
                    Constants.PREF_KEY_NOTIFICATIONS_NEW_MESSAGE_RINGTONE, "");
            soundUri = Uri.parse(discoveryRingtone);
        }
    }


    public boolean isPlaySound() {
        return playSound;
    }

    public boolean isVibrate() {
        return vibrate && isPlaySound();
    }
    public String getMaxMessage() {return maxMessage; }

    @Nullable
    public Uri getSoundUri() {
        return soundUri;
    }

    @Nullable
    public long[] getVibratePattern() {
        long[] vibratePattern = null;
        if (isVibrate()) {
            vibratePattern = new long[]{500L, 500L, 500L};
        }
        return vibratePattern;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v(TAG, "onSharedPreferenceChanged() key=" + key);
        loadFromPreferences();
    }
}
