package com.example.mymoviesapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviesapp.R
import com.example.mymoviesapp.adapter.ActorsAdapter
import com.example.mymoviesapp.adapter.BannerAdapter
import com.example.mymoviesapp.adapter.CenterZoomLayoutManager
import com.example.mymoviesapp.adapter.MoviesAdapter
import com.example.mymoviesapp.adapter.SeriesAdapter
import com.example.mymoviesapp.databinding.FragmentHomeBinding
import com.example.mymoviesapp.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var bannerAdapter: BannerAdapter? = null
    private var moviesAdapter: MoviesAdapter? = null
    private val homeViewModel: HomeViewModel by viewModel()
    private var seriesAdapter: SeriesAdapter? = null
    private var actorsAdapter: ActorsAdapter? = null

    private var updateRunnable: Runnable? = null

    private var handler: Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupRecyclerViewMovies()
        setupRecyclerViewSeries()
        setupRecyclerViewActors()

        observeViewModelData()

        homeViewModel.getTrendingMovies()
        homeViewModel.getTrendingSeries()
        homeViewModel.getTrendingActors()
    }

    private fun setupViewPager() {
        val banners = listOf(R.drawable.banner_satu, R.drawable.banner_dua, R.drawable.banner_tiga)
        bannerAdapter = BannerAdapter(banners)
        binding.viewPager.adapter = bannerAdapter

        handler = Handler(Looper.getMainLooper())
        updateRunnable = object : Runnable {
            var currentPage = 0
            override fun run() {
                if (!isAdded) return // Pastikan fragment masih ditambahkan
                if (currentPage == banners.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage++, true)
                handler?.postDelayed(this, 3000)
            }
        }
        handler?.post(updateRunnable!!)
    }

    private fun setupRecyclerViewActors() {
        val recyclerViewActors = binding.rvTopActors
        recyclerViewActors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        actorsAdapter = ActorsAdapter()
        recyclerViewActors.adapter = actorsAdapter
        PagerSnapHelper().attachToRecyclerView(recyclerViewActors)
    }

    private fun setupRecyclerViewSeries() {
        val recyclerViewSeries = binding.viewPagerTv
        recyclerViewSeries.layoutManager = CenterZoomLayoutManager(requireContext())
        seriesAdapter = SeriesAdapter()
        recyclerViewSeries.adapter = seriesAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewSeries)

        recyclerViewSeries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snapView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (snapView != null) {
                        val centerPosition = recyclerView.getChildAdapterPosition(snapView)
                        if (centerPosition != RecyclerView.NO_POSITION) {
                            seriesAdapter?.updateCenterPosition(centerPosition)
                        }
                    }
                }
            }
        })
    }

    private fun setupRecyclerViewMovies() {
        val recyclerViewMovies = binding.viewPagerMovies
        recyclerViewMovies.layoutManager = CenterZoomLayoutManager(requireContext())
        moviesAdapter = MoviesAdapter()
        recyclerViewMovies.adapter = moviesAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewMovies)

        recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snapView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (snapView != null) {
                        val centerPosition = recyclerView.getChildAdapterPosition(snapView)
                        if (centerPosition != RecyclerView.NO_POSITION) {
                            moviesAdapter?.updateCenterPosition(centerPosition)
                        }
                    }
                }
            }
        })
    }

    private fun observeViewModelData() {
        homeViewModel.trendingMovies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter?.submitList(movies)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.tvTopActors.visibility = View.GONE
                binding.tvSeriesTrending.visibility = View.GONE
                binding.moviesTrending.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.tvTopActors.visibility = View.VISIBLE
                binding.moviesTrending.visibility = View.VISIBLE
                binding.tvSeriesTrending.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
        homeViewModel.trendingSeries.observe(viewLifecycleOwner) { series ->
            seriesAdapter?.submitList(series)
        }

        homeViewModel.trendingActors.observe(viewLifecycleOwner) { actors ->
            actorsAdapter?.submitList(actors)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacks(updateRunnable!!)
        updateRunnable = null
        // Pastikan binding tidak null sebelum mengaksesnya
        binding.rvTopActors.adapter = null
        binding.viewPagerMovies.adapter = null
        binding.viewPagerTv.adapter = null
        binding.viewPager.adapter = null
        actorsAdapter = null
        moviesAdapter = null
        seriesAdapter = null
        bannerAdapter = null

        _binding = null

    }


}
