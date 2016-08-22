package com.orangepenguin.boilerplate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@SuppressLint("Registered")
public abstract class BaseActivity<TypeOfPresenter extends BasePresenterInterface>
        extends AppCompatActivity implements BaseViewInterface {

    @Nullable @BindView(R.id.toolbar) protected Toolbar toolbar; // common toolbar storage
    @Nullable @BindView(R.id.loading_indicator) protected View loadingIndicator; // common loading indicator
    @Nullable @BindView(R.id.contents_container) protected View contentContainer; // common content storage

    @Override
    public void showMessage(String format, Object... params) {
        Toast.makeText(this, String.format(format, params), Toast.LENGTH_SHORT).show();
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

    @Override
    public void showLoadingIndicator() {
        if (contentContainer == null || loadingIndicator == null) {
            Timber.e("Called showLoadingIndicator, but no such thing present");
            return;
        }
        contentContainer.setVisibility(INVISIBLE);
        loadingIndicator.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        if (contentContainer == null || loadingIndicator == null) {
            Timber.e("Called hideLoadingIndicator, but no such thing present");
            return;
        }
        contentContainer.setVisibility(VISIBLE);
        loadingIndicator.setVisibility(INVISIBLE);
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            getPresenter().onDestroy();
        }
    }

    protected abstract TypeOfPresenter getPresenter();

    protected abstract void setPresenter(TypeOfPresenter presenter);
}
