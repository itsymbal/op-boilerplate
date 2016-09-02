package com.orangepenguin.boilerplate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.orangepenguin.boilerplate.di.Injector;

import java.lang.reflect.ParameterizedType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@SuppressLint("Registered")
public abstract class BaseActivity<TypeOfPresenterInterface extends BasePresenterInterface>
        extends AppCompatActivity implements BaseViewInterface {

    @Nullable @BindView(R.id.toolbar) protected Toolbar toolbar; // common toolbar storage
    @Nullable @BindView(R.id.loading_indicator) protected View loadingIndicator; // common loading indicator
    @Nullable @BindView(R.id.contents_container) protected View contentContainer; // common content storage
    protected TypeOfPresenterInterface presenter;

    // TODO: have a list of Unbinders
    protected Unbinder unbinder;

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
        return presenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = (TypeOfPresenterInterface) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = (TypeOfPresenterInterface) Injector.getPresenter(getPresenterInterfaceClassFromType());
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this); // use Butterknife to bind the activity views
        if (toolbar != null) { // if layout defines a Toolbar with id R.id.toolbar
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // call this with 'false' to disable Up
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * only call this is Activity is being destroyed forever
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            presenter.onDestroy();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private Class getPresenterInterfaceClassFromType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[0];
    }
}
