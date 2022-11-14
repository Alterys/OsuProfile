package com.example.profileonosu.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.profileonosu.R
import com.example.profileonosu.common.Constant.ERROR_01
import com.example.profileonosu.common.Constant.ERROR_O2
import com.example.profileonosu.common.Constant.KEY_NICKNAME
import com.example.profileonosu.databinding.FragmentStartBinding

open class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)
        val internetConnect = checkForInternet()

        binding.search.setOnClickListener {
            if (!internetConnect) {
                Toast.makeText(context, ERROR_01, Toast.LENGTH_SHORT).show()
            } else {
                val bundle = Bundle()
                val name = binding.textName.text
                bundle.putString(KEY_NICKNAME, name.toString())
                if (name.isEmpty())
                    Toast.makeText(context, ERROR_O2, Toast.LENGTH_SHORT).show()
                else
                    findNavController().navigate(R.id.action_startFragment_to_endFragment, bundle)
            }
        }
    }

    private fun checkForInternet(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
