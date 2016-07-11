package com.orangepenguin.boilerplate.mvp.username;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.orangepenguin.boilerplate.BaseActivity;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.di.Injector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * this simple activity has a single text field, a checkbox, and a submit button. It is just about the simplest setup
 * for an MVP pattern. The Activity plays the role of View. The UsernamePresenter is the Presenter. The View forwards
 * user interactions to the Presenter. The presenter figures out appropriate actions to take (e.g. forward to next
 * activity).
 * The View performs the action (forwarding to next activity or saving / deleting preferences) as those are
 * Android-specific actions.
 */
public class UsernameActivity extends BaseActivity<UsernameContract.Presenter> implements UsernameContract.View {

    @BindView(R.id.username_edit_text) EditText usernameEditText;
    @BindView(R.id.remember_check_box) CheckBox rememberCheckBox;

    @Inject UsernameContract.Presenter presenter;

    @Override
    public void startDetailsActivity() {
        // TODO: start new Detail activity
    }

    @Override
    public void setUsername(String s) {
        usernameEditText.setText(s);
    }

    @Override
    public void checkRememberCheckbox() {
        rememberCheckBox.setChecked(true);
    }

    @Override
    public UsernameContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(UsernameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_activity);
        ButterKnife.bind(this);

        // injection only happens if presenter is null. That means the only thing that can be @Inject'ed into this
        // view is the Presenter. That in turn can have anything necessary @Inject'ed, and then set in the View on
        // setView()
        if (presenter == null) {
            Injector.getUsernameComponent().inject(this);
        }

        presenter.setView(this);
    }

    @OnClick(R.id.view_user_button)
    void userButtonClick() {
        presenter.showUserButtonPressed(usernameEditText.getText().toString(), rememberCheckBox.isChecked());
    }
}
