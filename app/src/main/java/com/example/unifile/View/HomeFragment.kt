package com.example.unifile.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.unifile.R
import com.example.unifile.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.accountButton.setOnClickListener(goToAccount())
        viewBinding.archivioButton.setOnClickListener(goToArchivio())
    }

    private fun goToAccount(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(AccountFragment.newInstance())
        }
    }

    private fun goToArchivio(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(ArchivioFragment.newInstance())
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        // Ottieni il FragmentManager della tua Activity
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        // Inizia una transazione per sostituire il fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)

        // Esegui la transazione
        fragmentTransaction.commit()

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance() = HomeFragment()

    }
}