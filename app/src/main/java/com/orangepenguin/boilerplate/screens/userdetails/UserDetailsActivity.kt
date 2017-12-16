package com.orangepenguin.boilerplate.screens.userdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.orangepenguin.boilerplate.BaseActivity
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.model.User
import com.orangepenguin.boilerplate.util.ImageFetcher
import kotterknife.bindView
import javax.inject.Inject
import javax.inject.Named

class UserDetailsActivity : BaseActivity<UserDetailsPresenter>(), UserDetailsView {
    @Inject
    @field:Named("fitCenterCropWhitePlaceholder")
    internal lateinit var imageFetcher: ImageFetcher<String, ImageView>
    @Inject override lateinit var presenter: UserDetailsPresenter

    //TODO: look at Kotlin synthetic properties
    private val ivAvatar: ImageView by bindView(R.id.avatar_imageview)
    private val tvUsername: TextView by bindView(R.id.username_textview)
    private val tvName: TextView  by bindView(R.id.name_textview)
    private val tvCompany: TextView by bindView(R.id.company_textview)
    private val tvRepos: TextView by bindView(R.id.repos_textview)
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // super.onCreate() has to be called *after* retrieving component. Android tries to reinitialize any Fragments
        // during super.onCreate() and those would rely on viewComponent already having been created.
        viewComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_details)

        user = intent.getParcelableExtra<User>(USER_INTENT_PARAM)
        presenter.initialize(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
        presenter.takeView(this)
        presenter.user = user
    }

    override fun setUsername(username: String) {
        tvUsername.text = username
    }

    override fun setCompany(company: String) {
        tvCompany.text = company
    }

    override fun setName(name: String) {
        tvName.text = name
    }

    override fun setAvatarUrl(avatarUrl: String) {
        imageFetcher.fetchAndSetImage(avatarUrl, ivAvatar)
    }

    companion object {
        private val USER_INTENT_PARAM = "USER_INTENT_PARAM"

        fun makeIntent(context: Context, user: User): Intent {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra(USER_INTENT_PARAM, user)
            return intent
        }
    }
}
