package com.orangepenguin.boilerplate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this); // use Butterknife to bind the activity views
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            if (getPresenter() != null) {
                getPresenter().onDestroy();
            }
        }
    }

    @NonNull
    protected abstract TypeOfPresenter getPresenter();

    protected abstract void setPresenter(TypeOfPresenter presenter);
}
