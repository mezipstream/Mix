package me.zipstream.mix.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import me.zipstream.mix.R;
import me.zipstream.mix.base.Constants;

public class JokeUtil {

    public static void copy(Activity activity, String copyText) {
        ClipboardManager clip = (ClipboardManager)
                activity.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setPrimaryClip(ClipData.newPlainText(null, copyText));
        Toast.makeText(activity, Constants.COPY_SUCCESS, Toast.LENGTH_SHORT)
                .show();
    }

    public static void share(Activity activity, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent,
                activity.getResources().getString(R.string.app_name)));
    }
}
