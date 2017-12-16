package com.orangepenguin.boilerplate.screens.username

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import com.crashlytics.android.Crashlytics
import com.orangepenguin.boilerplate.BaseActivity
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.model.User
import com.orangepenguin.boilerplate.screens.userdetails.UserDetailsActivity
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.username_activity.*
import javax.inject.Inject

/**
 * this simple activity has a single text field, a checkbox, and a submit button. It is just about the simplest setup
 * for an MVP pattern. The Activity plays the role of View. The UsernamePresenter is the Presenter. The View forwards
 * user interactions to the Presenter. The presenter figures out appropriate actions to take (e.g. forward to next
 * activity).
 * The View performs the action (forwarding to next activity or saving / deleting preferences) as those are
 * Android-specific actions.
 */
class UsernameActivity : BaseActivity<UsernamePresenter>(), UsernameView {

    // TODO: validation. Saripaar does not work on Kotlin-Android fields. Perhaps KotterKnife?
    private val etUsername: EditText get() = username_edit_text
    private val cbRemember: CheckBox get() = remember_check_box

    @Inject override lateinit var presenter: UsernamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        viewComponent.inject(this)

        super.onCreate(savedInstanceState)
        // TODO: ponder initializing this in a Presenter?
        // discuss with twitter folks
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.username_activity)

        val button = findViewById(R.id.view_user_button)
        button.setOnClickListener({ _ ->
            presenter.showUserButtonPressed(etUsername.text.toString(), cbRemember.isChecked)
        })
    }

    override fun startDetailsActivity(user: User) {
        startActivity(UserDetailsActivity.makeIntent(this, user))
    }

    override fun setUsername(s: String) {
        etUsername.setText(s)
    }

    override fun checkRememberCheckbox() {
        cbRemember.isChecked = true
    }

    override fun setUsernameError(error: String) {
        etUsername.error = error
    }

    override fun onStart() {
        super.onStart()
        presenter.takeView(this)
    }

}
