package com.orangepenguin.boilerplate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

@SuppressLint("Registered")
public abstract class BaseActivity<TypeOfPresenter extends BasePresenterInterface>
        extends AppCompatActivity implements BaseViewInterface {

    @Override
    public void showMessage(String format, Object... params) {
        Toast.makeText(this, String.format(format, params), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return getPresenter();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter((TypeOfPresenter) getLastCustomNonConfigurationInstance());
    }

    @Override
    public void savePreference(String key, String value) {
        getPreferences(MODE_PRIVATE).edit().putString(key, value).apply();
    }

    @Override
    public void savePreference(String key, Boolean value) {
        getPreferences(MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    @Override
    public void clearPreference(String key) {
        getPreferences(MODE_PRIVATE).edit().remove(key).apply();
    }

    @Override
    public String getPreference(String key, String defaultValue) {
        return getPreferences(MODE_PRIVATE).getString(key, defaultValue);
    }

    protected abstract TypeOfPresenter getPresenter();
    protected abstract void setPresenter(TypeOfPresenter presenter);
}
