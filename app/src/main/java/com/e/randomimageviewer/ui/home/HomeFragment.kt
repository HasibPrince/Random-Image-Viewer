package com.e.randomimageviewer.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.e.randomimageviewer.R
import com.e.randomimageviewer.common.AppConnectivityManager
import com.e.randomimageviewer.common.ImageDownloader
import com.e.randomimageviewer.data.Result
import com.e.randomimageviewer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar
import java.io.File

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var snackbar: Snackbar
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

        checkInternetConnectivity(view)

        homeViewModel.imagePathLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingText.visibility = View.INVISIBLE
            if(it.isSuccess()) {
                showSuccessResult(it)
            } else {
                Snackbar.make(requireView(), it.message ?: getString(R.string.unknown_error), Snackbar.LENGTH_LONG).show()
            }
        })

        binding.randomImageButton.setOnClickListener {
            handleRandomImageButtonClick()
        }
    }

    private fun handleRandomImageButtonClick() {
        binding.loadingText.visibility = View.VISIBLE
        ImageDownloader.downloadImageAsBitmap(requireContext()) {
            it?.let { bitmap ->
                homeViewModel.saveImage(bitmap)
            }
        }
    }

    private fun checkInternetConnectivity(view: View) {
        snackbar = Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
        AppConnectivityManager.instance.getInternetConnectivity()
            .observe(viewLifecycleOwner, Observer {
                it.hasIternet?.let { hasInternet ->
                    if (!hasInternet) {
                        snackbar.show()
                    } else {
                        snackbar.dismiss()
                    }
                }
            })
    }

    private fun showSuccessResult(it: Result<File>) {
        Glide.with(this)
            .load(it.data)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.randomImage)
    }


}