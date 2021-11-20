package com.e.randomimageviewer.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.e.randomimageviewer.common.ImageDownloader
import com.e.randomimageviewer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar

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

        homeViewModel.imagePath.observe(viewLifecycleOwner, Observer {
            if(it.isSuccess()) {
                Glide.with(this)
                    .load(it.data)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.randomImage)
            } else {
                Snackbar.make(requireView(), it.message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
            }
        })

        binding.randomImageButton.setOnClickListener {
            ImageDownloader.downloadImageAsBitmap(requireContext()) {
                it?.let { bitmap ->
                    homeViewModel.saveImage(bitmap)
                }
            }
        }
    }


}