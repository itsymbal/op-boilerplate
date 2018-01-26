package com.orangepenguin.boilerplate.screens.launcher

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orangepenguin.boilerplate.R
import com.orangepenguin.boilerplate.screens.permissions.PermissionsActivity
import com.orangepenguin.boilerplate.screens.username.UsernameActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        button_username_activity.setOnClickListener(
                { startActivity(Intent(this, UsernameActivity::class.java)) })

        button_permissions_activity.setOnClickListener(
                { startActivity(Intent(this, PermissionsActivity::class.java)) })
    }

}
