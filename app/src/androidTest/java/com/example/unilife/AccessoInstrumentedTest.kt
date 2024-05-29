package com.example.exampleinstrumentedtest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unilife.R
import com.example.unilife.View.Activity.AccessoActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class AccessoInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<AccessoActivity>
            = ActivityScenarioRule(AccessoActivity::class.java)


    @Test
    fun accessoTest() {

        // Inizializza i dati
        val email = "manuma.marrone@gmail.com"
        val password = "12345678"

        Thread.sleep(2000)
        //compilo i campi
        onView(withId(R.id.editTextEmailLogin))
            .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.editTextPasswordLogin))
            .perform(typeText(password), closeSoftKeyboard())

        //clicco su accedi
        onView(withId(R.id.accediButton)).perform(click())
        Thread.sleep(2000)

        // Verifica che il main layout sia visibile
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }
}