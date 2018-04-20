package com.orangepenguin.boilerplate

import android.app.Activity
import android.os.Bundle

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_test_activity)
    }
}
