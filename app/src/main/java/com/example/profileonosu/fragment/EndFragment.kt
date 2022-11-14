package com.example.profileonosu.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.profileonosu.R
import com.example.profileonosu.api.ApiOsu
import com.example.profileonosu.api.token.GetTokenRequest
import com.example.profileonosu.api.token.Token
import com.example.profileonosu.api.userinfo.Score
import com.example.profileonosu.api.userinfo.UserInfo
import com.example.profileonosu.common.Constant.ACCEPT
import com.example.profileonosu.common.Constant.BASE_URL
import com.example.profileonosu.common.Constant.ERROR_03
import com.example.profileonosu.common.Constant.GRANT_TYPE
import com.example.profileonosu.common.Constant.KEY_NICKNAME
import com.example.profileonosu.common.Constant.LIMIT
import com.example.profileonosu.common.Constant.SCOPE
import com.example.profileonosu.common.Secret.CLIENT_ID
import com.example.profileonosu.common.Secret.CLIENT_SECRET
import com.example.profileonosu.databinding.FragmentEndBinding
import com.example.profileonosu.score.ScoreAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class EndFragment : Fragment() {

    var token: String? = null
    var tokenType: String? = null
    var userId: Int? = null

    private lateinit var binding: FragmentEndBinding

    private lateinit var adapter: ScoreAdapter

    private val osuApi: ApiOsu =
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
        osuApi.requestToken(
            GetTokenRequest(
                CLIENT_ID,
                CLIENT_SECRET,
                GRANT_TYPE,
                SCOPE
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
        val nickname = requireArguments().getString(KEY_NICKNAME)
        osuApi.requestUser(
            "$tokenType $token",
            ACCEPT,
            "$nickname"
        ).enqueue(object : Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("[err]", t.toString())
            }
            override fun onResponse(
                call: Call<UserInfo>,
                response: Response<UserInfo>
            ) {
                if (response.body() == null){
                    Toast.makeText(context, ERROR_03, Toast.LENGTH_SHORT).show()
                    binding.userNameView.text = ""
                    binding.countryView.text = ""
                    binding.GlobalRankView.text = ""
                    binding.performancePointView.text = ""
                    binding.accuracyView.text = ""
                    binding.userName.text = ""
                    binding.performance.text = ""
                    binding.globalRank.text = ""
                    binding.country.text = ""
                    binding.hitAccuracy.text = ""
                } else {
                    val globalRank = response.body()?.statistics?.globalRank
                    val performancePoints = response.body()?.statistics?.pp
                    val hitAccuracy = response.body()?.statistics?.hitAccuracy
                    val username = response.body()?.username
                    val country = response.body()?.countryCode
                    val avatarUrl = response.body()?.avatarUrl
                    userId = response.body()?.id
                    binding.userName.text = username
                    binding.performance.text = getString(
                        R.string.performance,
                        performancePoints.toString()
                    )
                    binding.hitAccuracy.text = getString(
                        R.string.hitAccuracy,
                        hitAccuracy.toString()
                    )
                    binding.globalRank.text = getString(
                        R.string.globalRank,
                        globalRank.toString()
                    )
                    binding.country.text = country
                    Picasso.get().load(avatarUrl).into(binding.avatar)
                    getBestScore()}
            }
        })
    }

    private fun getBestScore() {
        osuApi.requestScores(
            "$tokenType $token",
            ACCEPT,
            userId,
            LIMIT
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
