package com.example.bancom

import android.app.Activity
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.example.bancom.feature.home.HomeUserAdapter
import org.hamcrest.core.AllOf
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    open fun nextOpenActivityIs(clazz: Class<*>) {
        Intents.intended(IntentMatchers.hasComponent(clazz.name))
    }

    open fun isActivity(clazz: Class<*>): Boolean {
        val activity: Activity? = getActivityInstance()
        return clazz.isInstance(activity)
    }

    open fun click(@IdRes viewId: Int): ViewInteraction? {
        return onView(withId(viewId)).perform(ViewActions.click())
    }

    open fun click(@IdRes viewId: Int, value: String?): ViewInteraction? {
        return onView(AllOf.allOf(withId(viewId), withText(value))).perform(ViewActions.click())
    }

    open fun longClick(@IdRes viewId: Int): ViewInteraction? {
        return onView(withId(viewId)).perform(ViewActions.longClick())
    }

    open fun setValue(@IdRes viewId: Int, value: String?): ViewInteraction? {
        return onView(withId(viewId)).perform(
            ViewActions.replaceText(value), ViewActions.pressImeActionButton()
        )
    }

    open fun matchText(@IdRes viewId: Int, @StringRes stringResource: Int): ViewInteraction? {
        return onView(withId(viewId)).check(ViewAssertions.matches(withText(stringResource)))
    }

    open fun matchText(@IdRes viewId: Int, value: String?): ViewInteraction? {
        return onView(withId(viewId)).check(ViewAssertions.matches(withText(value)))
    }

    open fun matchVisible(@IdRes viewId: Int): ViewInteraction? {
        return onView(withId(viewId)).check(
            ViewAssertions.matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    open fun matchVisibleAndText(@StringRes stringResource: Int): ViewInteraction? {
        return onView(withText(stringResource)).check(
            ViewAssertions.matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    open fun matchVisibleAndText(value: String?): ViewInteraction? {
        return onView(withText(value)).check(
            ViewAssertions.matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    open fun goToItemList(pos: Int) {
        val item = onView(AllOf.allOf(withId(R.id.recyclerUser), isDisplayed()))
        item.perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeUserAdapter.Viewholder>(
                pos, ViewActions.click()
            )
        )
    }

    open fun navigationTo(@IdRes viewId: Int): ViewInteraction? {
        return onView(withId(viewId)).perform(ViewActions.click())
    }

    open fun goToBack() {
        Espresso.pressBack()
    }

    companion object {
        const val TIMEOUT_ASYNC = 3500L
    }

    private fun getActivityInstance(): Activity? {
        val activity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities: Collection<*> =
                ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                val currentActivity = resumedActivities.iterator().next() as Activity
                activity[0] = currentActivity
            }
        }
        return activity[0]
    }
}