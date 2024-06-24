package vn.edu.hcmuaf.fit.travie.core.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hcmuaf.fit.travie.R;

public class LocalStorage {
    private final SharedPreferences prefs;

    public LocalStorage(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return prefs.getString(key, null);
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

    public void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    public void saveInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    public void saveBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public void saveLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return prefs.getLong(key, 0);
    }

    public void saveFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key) {
        return prefs.getFloat(key, 0);
    }

    public boolean contains(String key) {
        return prefs.contains(key);
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void saveStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    public Set<String> getStringSet(String key) {
        return prefs.getStringSet(key, null);
    }

    public void saveDouble(String key, double value) {
        prefs.edit().putLong(key, Double.doubleToRawLongBits(value)).apply();
    }

    public double getDouble(String key) {
        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }

    public void saveStringArray(String key, String[] value) {
        saveStringSet(key, Set.of(value));
    }

    public String[] getStringArray(String key) {
        Set<String> set = getStringSet(key);
        return set == null ? null : set.toArray(new String[0]);
    }

    public void saveIntArray(String key, int[] value) {
        Set<String> set = Set.of(Arrays.toString(value));
        saveStringSet(key, set);
    }

    public int[] getIntArray(String key) {
        Set<String> set = getStringSet(key);
        if (set == null) {
            return null;
        }
        return set.stream().mapToInt(Integer::parseInt).toArray();
    }
}
