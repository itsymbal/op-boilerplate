package com.orangepenguin.boilerplate;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.orangepenguin.boilerplate.di.Injector;
import com.orangepenguin.boilerplate.di.ViewComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@SuppressLint("Registered")
public abstract class BaseActivity<TypeOfPresenter extends BasePresenter>
        extends AppCompatActivity implements BaseViewInterface {

    @Nullable @BindView(R.id.toolbar) protected Toolbar toolbar; // common toolbar storage
    @Nullable @BindView(R.id.loading_indicator) protected View loadingIndicator; // common loading indicator
    @Nullable @BindView(R.id.contents_container) protected View contentContainer; // common content storage

    protected Unbinder unbinder;
    protected ViewComponent viewComponent;

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
        return viewComponent;
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

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().dropView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        /*    Only call this if Activity is being destroyed forever */
        if (!isChangingConfigurations()) {
            getPresenter().terminate();
        }
    }

    protected TypeOfPresenter getPresenter() {
        return null;
    }

    protected ViewComponent getViewComponent() {
        viewComponent = (ViewComponent) getLastCustomNonConfigurationInstance();
        if (viewComponent == null) {
            viewComponent = Injector.getComponentFactory().getViewComponent();
        }

        return viewComponent;
    }
}
