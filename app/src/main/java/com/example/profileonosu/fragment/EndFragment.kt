package com.example.profileonosu.fragment

import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.profileonosu.R
import com.example.profileonosu.api.ApiOsu
import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.api.token.GetUserRequest
import com.example.profileonosu.api.token.Token
import com.example.profileonosu.common.Constant.BASE_URL
import com.example.profileonosu.databinding.FragmentEndBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class EndFragment : Fragment() {

        private lateinit var binding: FragmentEndBinding

        private fun osuApi(): ApiOsu =
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




            GetUserRequest(
                "$nickname"
            )

        }

    suspend fun nigger(){
        osuApi().requestToken(GetTokenRequest(
            "18123",
            "PMVb6QP4BlfCACeuquYJbq1afbCiGY7Jo6rcrO35",
            "client_credentials",
            "public"
        )).enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e("[err]", t.toString())
            }

            override fun onResponse(
                call: Call<Token>,
                response: Response<Token>
            ) {
                response.body()?.let {
                    Log.d("Osu Token",
                        it.accessToken
                    )
                }
            }
        })
    }
}




