package com.orangepenguin.boilerplate

import android.annotation.SuppressLint
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.orangepenguin.boilerplate.di.Injector
import com.orangepenguin.boilerplate.di.ViewComponent
import timber.log.Timber

@SuppressLint("Registered")
abstract class BaseActivity<TypeOfPresenter : BasePresenter> : AppCompatActivity(), BaseViewInterface {

    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null // common toolbar storage
    @BindView(R.id.loading_indicator)
    var loadingIndicator: View? = null // common loading indicator
    @BindView(R.id.contents_container)
    var contentContainer: View? = null // common content storage

    protected var unbinder: Unbinder? = null

    // making this property lazy so we don't have to create a new viewComponent on object creation, if it does not
    // require dependency injection
    protected val viewComponent: ViewComponent by lazy {
        lastCustomNonConfigurationInstance as ViewComponent? ?: Injector.getComponentFactory().viewComponent
    }

    protected open lateinit var presenter: TypeOfPresenter

    override fun showLoadingIndicator() {
        if (contentContainer == null || loadingIndicator == null) {
            Timber.e("Called showLoadingIndicator, but no such thing present")
            return
        }
        contentContainer!!.visibility = INVISIBLE
        loadingIndicator!!.visibility = VISIBLE
    }

    override fun hideLoadingIndicator() {
        if (contentContainer == null || loadingIndicator == null) {
            Timber.e("Called hideLoadingIndicator, but no such thing present")
            return
        }
        contentContainer!!.visibility = VISIBLE
        loadingIndicator!!.visibility = INVISIBLE
    }

    override fun onRetainCustomNonConfigurationInstance() = viewComponent

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this) // use Butterknife to bind the activity views
        if (toolbar != null) { // if layout defines a Toolbar with id R.id.toolbar
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // call this with 'false' to disable Up
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        presenter.takeView(this)
//    }

    override fun onStop() {
        super.onStop()
        presenter.dropView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
        /*    Only call this if Activity is being destroyed forever */
        if (!isChangingConfigurations) {
            presenter.terminate()
        }
    }
}
