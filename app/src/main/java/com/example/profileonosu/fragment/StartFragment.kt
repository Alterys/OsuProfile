package com.example.profileonosu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.profileonosu.R
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
        binding.search.setOnClickListener {
            val bundle = Bundle()
            val name = binding.textName.text
            bundle.putString("MyArg", name.toString())
            findNavController().navigate(R.id.action_startFragment_to_endFragment, bundle)
        }
    }
}
