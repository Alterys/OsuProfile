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
import com.example.profileonosu.api.token.Token
import com.example.profileonosu.api.userinfo.Score
import com.example.profileonosu.api.userinfo.UserInfo
import com.example.profileonosu.common.Constant.BASE_URL
import com.example.profileonosu.databinding.FragmentEndBinding
import com.example.profileonosu.score.ScoreAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class EndFragment : Fragment() {

    // Это точно все нужно в этой области видимости?
    var token: String? = null
    var username: String? = null
    var performancePoints: String? = null
    var hitAccuracy: String? = null
    var globalRank: String? = null
    var country: String? = null
    var tokenType: String? = null
    var avatarUrl: String? = null
    var userId: Int? = null

    private lateinit var binding: FragmentEndBinding

    private lateinit var adapter: ScoreAdapter

    private fun osuApi(): ApiOsu = // Зачем каждый раз содавать новый api интерфейс?
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
        adapter = ScoreAdapter()
        binding.recyclerView.adapter = adapter
        getToken()
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_endFragment_to_startFragment)
        }
    }
    private fun getToken() {
        osuApi().requestToken(
            GetTokenRequest(
            "18123", // Почему это все лежит в открытом доступе всем желающим?
            "PMVb6QP4BlfCACeuquYJbq1afbCiGY7Jo6rcrO35", // И это тоже
            "client_credentials", // Добавь в константы
            "public" // Добавь в константы
            )
        ).enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e("[err]", t.toString())
            }
            override fun onResponse(
                call: Call<Token>,
                response: Response<Token>
            ) {
                token = response.body()?.accessToken ?: return
                tokenType = response.body()?.tokenType ?: return
                getUserInfo()
            }
        })
    }
    private fun getUserInfo() {
        val nickname = requireArguments().getString("MyArg") // Почему метод получения инфы о пользователе занимается вытаскиванием аргументов?
        osuApi().requestUser(
            "$tokenType $token",
            "application/json", // Добавь в константы
            "$nickname" // зачем форматированная строка?
        ).enqueue(object : Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("[err]", t.toString())
            }
            override fun onResponse(
                call: Call<UserInfo>,
                response: Response<UserInfo>
            ) {
                globalRank = response.body()?.statistics?.globalRank
                performancePoints = response.body()?.statistics?.pp
                hitAccuracy = response.body()?.statistics?.hitAccuracy
                username = response.body()?.username
                country = response.body()?.countryCode
                avatarUrl = response.body()?.avatarUrl
                userId = response.body()?.id
                binding.userName.append(username)
                binding.performance.append(performancePoints + "pp")
                binding.hitAccuracy.append("$hitAccuracy%")
                binding.globalRank.append("#$globalRank")
                binding.country.append(country)
                Picasso.get().load("$avatarUrl").into(binding.avatar) // зачем форматированная строка?
                getBestScore()
            }
        })
    }
    private fun getBestScore() {
        Log.d("getBestScore", "запустилась")
        osuApi().requestScores(
            "$tokenType $token",
            "application/json", // Добавь в константы
            "$userId" // зачем форматированная строка?
        ).enqueue(object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                Log.e("[err]", t.toString())
            }
            override fun onResponse(
                call: Call<List<Score>>,
                response: Response<List<Score>>
            ) {
                response.body()?.let { adapter.scores = it }
            }
        })
    }
}
