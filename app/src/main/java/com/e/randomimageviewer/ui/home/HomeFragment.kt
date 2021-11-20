package com.e.randomimageviewer.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.e.randomimageviewer.common.Config
import com.e.randomimageviewer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.randomImageButton.setOnClickListener {
            Glide.with(this)
                .load(Config.RANDOM_IMAGE_URL)
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .into(binding.randomImage)
            downloadImage()
        }
    }

    private fun downloadImage() {
        Glide.with(this)
            .asBitmap()
            .load(Config.RANDOM_IMAGE_URL)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.d(
                        this.javaClass.simpleName,
                        "===> bitmap info: ${resource.width}x${resource.height}"
                    )

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.e("HomeFragment", "Image downloading error")
                }
            })
    }
}