package com.example.bancom

import android.content.Intent
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bancom.feature.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
open class NavigationTest : BaseTest() {

    @get:Rule
    val rule = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setUp() {
        if (Build.VERSION.SDK_INT < 21) sleep(TIMEOUT_ASYNC)
    }

    @After
    fun finish() {
        if (Build.VERSION.SDK_INT < 21) sleep(TIMEOUT_ASYNC)
    }

    @Test
    fun testNavigationLogin() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), MainActivity::class.java
        )
        rule.launch(intent)

        sleep(TIMEOUT_ASYNC)
        matchVisible(R.id.activity_container)

        matchVisible(R.id.edtEmail)
        setValue(R.id.edtEmail, "andre-gnr@hotmail.com")

        matchVisible(R.id.edtPassword)
        setValue(R.id.edtPassword, "12345")

        sleep(TIMEOUT_ASYNC)
        navigationTo(R.id.btnLogin)
    }

    @Test
    fun testNavigationHome() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        rule.launch(intent)

        sleep(TIMEOUT_ASYNC)
        matchVisible(R.id.activity_container)

        matchVisible(R.id.edtEmail)
        setValue(R.id.edtEmail, "andre-gnr@hotmail.com")

        matchVisible(R.id.edtPassword)
        setValue(R.id.edtPassword, "12345")

        sleep(TIMEOUT_ASYNC)
        navigationTo(R.id.btnLogin)

        //

        sleep(TIMEOUT_ASYNC)
        goToItemList(0)
        sleep(TIMEOUT_ASYNC)
    }

    @Test
    fun testNavigationDetail() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        rule.launch(intent)

        sleep(TIMEOUT_ASYNC)
        matchVisible(R.id.activity_container)

        matchVisible(R.id.edtEmail)
        setValue(R.id.edtEmail, "andre-gnr@hotmail.com")

        matchVisible(R.id.edtPassword)
        setValue(R.id.edtPassword, "12345")

        sleep(TIMEOUT_ASYNC)
        navigationTo(R.id.btnLogin)

        //

        sleep(TIMEOUT_ASYNC)
        goToItemList(0)
        sleep(TIMEOUT_ASYNC)

        //

        matchVisible(R.id.edtTitle)
        setValue(R.id.edtTitle, "Test")

        matchVisible(R.id.edtDescription)
        setValue(R.id.edtDescription, "Test")

        sleep(TIMEOUT_ASYNC)
        navigationTo(R.id.btnGuardar)
        sleep(TIMEOUT_ASYNC)
    }
}