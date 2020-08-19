package com.richardo.animeku.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.richardo.animeku.R
import com.richardo.animeku.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val args : ProfileFragmentArgs by navArgs()

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(args.id, args.profile)
    }

    private lateinit var voiceAdapter : VoiceRoleAdapter
    private lateinit var charaAdapter : CharacterRoleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)
            .apply {
                viewModel = profileViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (args.profile == "Character") {
            setCharacterRv()
            profileViewModel.character.observe(viewLifecycleOwner, Observer { character ->
                character?.media?.edges?.let {list ->
                    charaAdapter.submitList(list)
                }
            })
        }else {
            setVoiceActorRv()
            profileViewModel.voiceActor.observe(viewLifecycleOwner, Observer {voiceActor ->
                voiceActor?.characters?.edges?.let {list ->
                    voiceAdapter.submitList(list)

                }
            })
        }

        // Livedata untuk mengamati perubahan pada data voice actor
        profileViewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
            // Mengecek apakah data voice actor bernilai null atau tidak
            if(profile != null){
                pb_voice_actor.visibility = View.GONE
                group_voice_actor.visibility  = View.VISIBLE
            }


        })
    }

    private fun setCharacterRv(){
        charaAdapter = CharacterRoleAdapter()

        rv_voice_role.apply {
            adapter = charaAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setVoiceActorRv() {
        voiceAdapter = VoiceRoleAdapter()

        rv_voice_role.apply {
            adapter = voiceAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}