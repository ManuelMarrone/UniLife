package com.example.exampleinstrumentedtest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unilife.R
import com.example.unilife.View.Activity.AccessoActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class CreazioneGruppoInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<AccessoActivity>
            = ActivityScenarioRule(AccessoActivity::class.java)


    @Test
    fun gruppoTest() {

        // Inizializza i dati
        val email = "test2@gmail.com"
        val password = "dhdh34"

        Thread.sleep(2000)
        //compilo i campi
        onView(withId(R.id.editTextEmailLogin))
            .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.editTextPasswordLogin))
            .perform(typeText(password), closeSoftKeyboard())

        //clicco su accedi
        onView(withId(R.id.accediButton)).perform(click())
        Thread.sleep(2000)

        //clicco per accedere alla pagina di creazione del gruppo
        onView(withId(R.id.bottom_add)).perform(click())
        Thread.sleep(2000)

        //crea gruppo
        onView(withId(R.id.creaBtn)).perform(click())
        Thread.sleep(2000)

        //verifica che il layout di Home di un utente partecipante al gruppo sia visibile
        onView(withId(R.id.homeLayout))
            .check(matches(isDisplayed()))

        Thread.sleep(10000)
        //verifica che il gruppo esista
        runBlocking {
            val firestore = FirebaseFirestore.getInstance()
            val querySnapshot = firestore.collection("utenti").whereEqualTo("email", email)
                .get()
                .await()

            if (querySnapshot != null && !querySnapshot.isEmpty) {
                val userDocument = querySnapshot.documents[0]

                val idGruppo = userDocument.get("id_gruppo")
                if (idGruppo != null){
                    Assert.assertTrue("gruppo esistente", true)
            } else {
                Assert.fail("Gruppo non trovato")
            }
        } else {
            Assert.fail("Utente non trovato")
        }
        }

    }
}