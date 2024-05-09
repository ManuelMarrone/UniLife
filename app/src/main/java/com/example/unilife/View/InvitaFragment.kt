package com.example.unilife.View

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.unilife.R
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.example.unilife.ViewModel.InvitaViewModel
import com.example.unilife.ViewModel.RegistrazioneViewModel
import com.example.unilife.databinding.ActivityRegistrazioneBinding
import com.example.unilife.databinding.FragmentHomeBinding
import com.example.unilife.databinding.FragmentInvitaBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InvitaFragment : Fragment() {

    private lateinit var binding: FragmentInvitaBinding
    private val viewModel: InvitaViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.invitaBtn.setOnClickListener{onClickInvita()}
    }



    companion object {

        fun newInstance() = InvitaFragment()
    }

    private fun onClickInvita() {
//        // Chiamare la funzione per ottenere il gruppo dal ViewModel
//        val idGruppo = viewModel.getGruppo()
//
//        // Osserva i cambiamenti nell'ID del gruppo
//        viewModel.getIdGruppoLiveData().observe(viewLifecycleOwner, Observer { idGruppo ->
//            // Aggiorna l'UI con il nuovo valore dell'ID del gruppo
//            if (idGruppo != null) {
//                //invita(idGruppo)
//                Log.d("MyActivity", "invita $idGruppo")
//            } else {
//                viewModel.creaGruppo()
//                Log.d("MyActivity", "ID del gruppo non disponibile")
//            }
//        })
        viewModel.getIdGruppo { idGruppo ->
            if (idGruppo != null) {
                invita(idGruppo)
                Log.d("MyActivity", "ID del gruppo: $idGruppo")
            } else {
                viewModel.creaGruppo{ idGruppo->
                    if (idGruppo != null) {
                        invita(idGruppo)
                    }
                }
                Log.d("MyActivity", "ID del gruppo non disponibile")
            }

        }

    }
    private fun invita(idGruppo:String)
    {

        val destinatario = binding.destEmail.text.toString()
        Log.d("MyActivity", "entra in invita ${destinatario}")
        val soggetto = "Invito al gruppo di coinquilini"
        val corpo = "Sei stato invitato al gruppo di coinquilini, registrati all'app se non l'hai ancora fatto e inserisci il codice ${idGruppo}"

        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, soggetto)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, corpo)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Scegli l'app per mandare l'email"))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Log.w(ContentValues.TAG, "Error mail ${e}")
        }
    }
}