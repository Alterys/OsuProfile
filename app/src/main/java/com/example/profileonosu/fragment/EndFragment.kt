package com.example.profileonosu.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.profileonosu.R
import com.example.profileonosu.api.ApiOsu
import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.common.Constant.BASE_URL
import com.example.profileonosu.databinding.FragmentEndBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class EndFragment : Fragment() {

        private lateinit var binding: FragmentEndBinding

        fun OsuApiUse(): ApiOsu =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiOsu::class.java)

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_end, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding = FragmentEndBinding.bind(view)
            binding.back.setOnClickListener {
                findNavController().navigate(R.id.action_endFragment_to_startFragment)
            }

            val nickname = arguments?.getString("MyArg")

            GetTokenRequest(
                "18123",
                "PMVb6QP4BlfCACeuquYJbq1afbCiGY7Jo6rcrO35",
                "client_credentials",
                "public"
            )


            Log.d(
                "Osu Token",
                GetTokenRequest(
                    "18123",
                    "PMVb6QP4BlfCACeuquYJbq1afbCiGY7Jo6rcrO35",
                    "client_credentials",
                    "public"
                ).toString()

            )
        }
    }



