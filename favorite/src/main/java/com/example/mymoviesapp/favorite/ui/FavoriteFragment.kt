package com.example.mymoviesapp.favorite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviesapp.favorite.adapter.FavoriteAdapter
import com.example.mymoviesapp.favorite.databinding.FragmentFavoriteBinding
import com.example.mymoviesapp.favorite.di.favoriteModule
import com.example.mymoviesapp.favorite.viewModel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.setHasFixedSize(true)

        // Set adapter terlebih dahulu
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorite.adapter = favoriteAdapter

        favoriteViewModel.getAllUser()
        // Kemudian observe data

        observeData()

    }

    private fun observeData() {
        favoriteViewModel.getFavoriteMovies.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList != null && movieList.isNotEmpty()) {
                favoriteAdapter.submitList(movieList)
                return@Observer
            }
            favoriteAdapter.submitList(movieList)
        })
    }

    override fun onResume() {
        super.onResume()
        observeData()
        favoriteViewModel.getAllUser()
    }

    override fun onPause() {
        super.onPause()
        observeData()
        favoriteViewModel.getAllUser()
    }
}