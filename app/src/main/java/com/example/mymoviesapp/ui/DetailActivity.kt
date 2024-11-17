package com.example.mymoviesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Movies
import com.example.mymoviesapp.MainActivity
import com.example.mymoviesapp.R
import com.example.mymoviesapp.adapter.CrewAdapter
import com.example.mymoviesapp.adapter.GenresAdapter
import com.example.mymoviesapp.databinding.ActivityDetailBinding
import com.example.mymoviesapp.viewModel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    private val url = "https://image.tmdb.org/t/p/w500"
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var crewAdapter: CrewAdapter
    private var isFavorite: Boolean = false
    private var movies: Movies? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup system bars to be edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        val moviesId = intent.getIntExtra(ID_MOVIES, 0)

        // Observe detail movies
        detailViewModel.detailMovies.observe(this) { movieDetail ->
            movieDetail?.let {
                setupMovieDetail(it)

                // Get favorite status using LiveData
                detailViewModel.getFavoriteUser(moviesId)

                // Observe favorite status
                detailViewModel.favoriteMovie.observe(this) { favoriteMovie ->
                    isFavorite = favoriteMovie?.isFavorite == true
                    updateFavoriteButton(isFavorite)
                }

                // Set onClick for favorite button
                binding.btnLike.setOnClickListener {
                    toggleFavorite(moviesId, movieDetail = movieDetail)
                }
            } ?: showToast("Error loading movie details")
        }

        // Observe crew data
        detailViewModel.crewMovies.observe(this) { crewList ->
            crewList?.let {
                crewAdapter.submitList(it)
            } ?: showToast("Error loading crew")
        }

        // Fetch movie and crew details
        detailViewModel.getDetailMovies(moviesId)
        detailViewModel.getCreditMovies(moviesId)

        // Set up recycler views for genres and credits
        setRecyclerViewGenres()
        setRecyclerViewCredits()
    }

    private fun toggleFavorite(moviesId: Int, movieDetail: MovieDetail) {
        val newFavoriteState = !isFavorite
        movies = movieDetail.posterPath?.let {
            Movies(
                id = movieDetail.id,
                posterPath = it,
                title = movieDetail.title,
                overview = movieDetail.overview,
                isFavorite = newFavoriteState
            )
        }

        if (newFavoriteState) {
            detailViewModel.insertMovies(movies!!)
            showToast("Added to Favorites")
        } else {
            detailViewModel.deleteMovies(moviesId)
            showToast("Removed from Favorites")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        updateFavoriteButton(newFavoriteState)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0)
            binding.btnLike.setBackgroundColor(ContextCompat.getColor(this, R.color.md_theme_error))
        } else {
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_border,
                0,
                0,
                0
            )
            binding.btnLike.setBackgroundColor(ContextCompat.getColor(this, R.color.md_theme_background))
        }
    }

    private fun setupMovieDetail(movieDetail: MovieDetail) {
        val fullImageUrl = url + movieDetail.posterPath
        Glide.with(this).load(fullImageUrl).into(binding.imageBanner)

        genresAdapter.submitList(movieDetail.genres.take(2))
        binding.tvTitle.text = movieDetail.title
        binding.tvReleasePremiere.text = formatReleaseDate(movieDetail.releaseDate)
        binding.tvSummary.text = movieDetail.overview
        binding.tvProductionField.text =
            movieDetail.productionCountries.joinToString(", ") { it.name }
        binding.tvDuration.text = formatRuntime(movieDetail.runtime)
        binding.tvYear.text = formatReleaseYear(movieDetail.releaseDate)
        binding.tvRate.text = formatPopularity(movieDetail.popularity)

        setButtonPlay(movieDetail.homepage)
    }

    private fun setRecyclerViewCredits() {
        val recyclerViewCredits = binding.rvCrewFilm
        recyclerViewCredits.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        crewAdapter = CrewAdapter()
        recyclerViewCredits.adapter = crewAdapter
    }

    private fun setRecyclerViewGenres() {
        val recyclerViewGenres = binding.rvGenres
        recyclerViewGenres.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genresAdapter = GenresAdapter()
        recyclerViewGenres.adapter = genresAdapter
        recyclerViewGenres.isLayoutFrozen = true
    }

    private fun formatReleaseDate(releaseDate: String): String {
        val fullDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = fullDateFormat.parse(releaseDate) ?: Date()
        return targetFormat.format(date)
    }

    @SuppressLint("DefaultLocale")
    private fun formatRuntime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return if (hours > 0) {
            String.format("• %d hour(s) %02d minute(s)", hours, minutes)
        } else {
            String.format("• %02d minute(s)", minutes)
        }
    }

    private fun formatReleaseYear(releaseYear: String): String {
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val fullDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = fullDateFormat.parse(releaseYear) ?: Date()
        return yearFormat.format(date)
    }

    private fun formatPopularity(popularity: Double): String {
        return when {
            popularity >= 1_000_000 -> String.format("%.1f M", popularity / 1_000_000)
            popularity >= 1_000 -> String.format("%.1f K", popularity / 1000)
            else -> popularity.toString()
        }
    }

    private fun setButtonPlay(linkUrl: String) {
        binding.btnPlay.setOnClickListener {
            if (linkUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                startActivity(intent)
            } else {
                showToast("No trailer available")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID_MOVIES = "id_movies"
    }
}
