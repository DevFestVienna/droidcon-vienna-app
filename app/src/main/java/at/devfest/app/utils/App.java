package at.devfest.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import at.devfest.app.BuildConfig;
import at.devfest.app.data.app.model.Session;
import at.devfest.app.data.app.model.Speaker;

public final class App {

    private App() {
        throw new UnsupportedOperationException();
    }

    public static boolean isCompatible(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT >= apiLevel;
    }

    public static String getVersion() {
        return String.format(Locale.US, "%s (#%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    }

    public static void setExactAlarm(AlarmManager alarmManager, long triggerAtMillis, PendingIntent operation) {
        if (isCompatible(Build.VERSION_CODES.KITKAT)) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        }
    }

    @Nullable
    public static String getPhotoUrl(@Nullable Session session) {
        String photoUrl = null;
        if (session != null) {
            photoUrl = session.getPhoto();
            if (photoUrl == null) {
                List<Speaker> speakers = session.getSpeakers();
                if (speakers != null && !speakers.isEmpty()) {
                    photoUrl = speakers.get(0).getPhoto();
                }
            }
        }
        return photoUrl;
    }

    @Nullable
    public static String getThumbnail(@Nullable Session session) {
        String thumbnail = null;
        if (session != null) {
            thumbnail = session.getThumbnail();
            if (thumbnail == null) {
                List<Speaker> speakers = session.getSpeakers();
                if (speakers != null && !speakers.isEmpty()) {
                    thumbnail = speakers.get(0).getThumbnail();
                }
            }
        }
        return thumbnail;
    }
}
