package com.example.unilife

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unilife.View.Activity.AccessoActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrazioneInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<AccessoActivity> =
        ActivityScenarioRule(AccessoActivity::class.java)

    @Test
    fun registrazioneTest() {

        //simula il clic sul pulsante che apre RegistrazioneActivity
        onView(ViewMatchers.withId(R.id.creaButton))
            .perform(ViewActions.click())

        // Attendi il cambio di attività
        Thread.sleep(2000)

        //inizializza i dati
        val username = "test2"
        val email = "test2@gmail.com"
        val password = "dhdh34"

        //compila i campi
        onView(ViewMatchers.withId(R.id.editTextUsername))
            .perform(ViewActions.typeText(username), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.editTextRegistrazioneEmail))
            .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.editTextRegistrazionePassword))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())

        //clicca su Registrati
        onView(ViewMatchers.withId(R.id.registrazioneButton)).perform(ViewActions.click())
        Thread.sleep(2000)

        //verifica che il layout di Accesso sia visibile
        onView(ViewMatchers.withId(R.id.accediLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(10000)
        //verifica che l'utente sia stato creato in Firestore
        runBlocking {
            val firestore = FirebaseFirestore.getInstance()
            val querySnapshot = firestore.collection("utenti").whereEqualTo("email", email)
                .get()
                .await()


            if (querySnapshot != null && !querySnapshot.isEmpty) {
                val userDocument = querySnapshot.documents[0]
                Assert.assertTrue("utente trovato", userDocument.exists())
                Assert.assertEquals("confronto email", email, userDocument.getString("email"))
            } else {
                Assert.fail("Utente non trovato")
            }
        }
    }
}
