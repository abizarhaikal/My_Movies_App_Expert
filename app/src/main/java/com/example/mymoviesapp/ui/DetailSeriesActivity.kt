package com.example.mymoviesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.domain.model.SeriesDetailModel
import com.example.mymoviesapp.R
import com.example.mymoviesapp.adapter.CastSeriesAdapter
import com.example.mymoviesapp.databinding.ActivityDetailSeriesBinding
import com.example.mymoviesapp.viewModel.DetailSeriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailSeriesActivity : AppCompatActivity() {

    private val detailSeriesViewModel: DetailSeriesViewModel by viewModel()
    private lateinit var binding: ActivityDetailSeriesBinding
    private val url = "https://image.tmdb.org/t/p/w500"
    private lateinit var crewAdapter: CastSeriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        val seriesId = intent.getIntExtra(ID_SERIES, 0)
        detailSeriesViewModel.getDetailSeries(seriesId)
        detailSeriesViewModel.getCreditSeries(seriesId)

        setRecyclerViewCredits()
        observeViewModel()
    }

    private fun observeViewModel() {
        detailSeriesViewModel.detailSeries.observe(this) { seriesDetail ->
            seriesDetail?.let { updateUI(it) }
        }

        detailSeriesViewModel.crewSeries.observe(this) { crewList ->
            crewList?.let {
                crewAdapter.submitList(it)
            }
        }

        detailSeriesViewModel.loading.observe(this) { isLoading ->
            if (isLoading) showToast("Loading")
        }

        detailSeriesViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Log.e("Detail Activity", "Error: $it")
                showToast("Error: $it")
            }
        }
    }

    private fun updateUI(seriesDetail: SeriesDetailModel) {
        val fullImageUrl = url + seriesDetail.posterPath
        Glide.with(this)
            .load(fullImageUrl)
            .into(binding.imageBanner)

        binding.tvTitle.text = seriesDetail.name
        binding.tvReleasePremiere.text = formatReleaseDate(seriesDetail.firstAirDate)
        binding.tvSummary.text = seriesDetail.overview
        binding.tvProductionField.text =
            seriesDetail.productionCountries.joinToString(", ") { it.name }
        binding.tvDuration.text =
            seriesDetail.episodeRunTime.joinToString(", ") { formatRuntime(it) }
        binding.tvYear.text = formatReleaseYear(seriesDetail.firstAirDate)
        binding.tvRate.text = formatPopularity(seriesDetail.popularity)

        setButtonPlay(seriesDetail.homepage)
    }

    private fun setRecyclerViewCredits() {
        val recyclerViewCredits = binding.rvCrewFilm
        recyclerViewCredits.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        crewAdapter = CastSeriesAdapter()
        recyclerViewCredits.adapter = crewAdapter
    }

    private fun setButtonPlay(linkUrl: String) {
        binding.btnPlay.setOnClickListener {
            if (linkUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                startActivity(intent)
            } else {
                showToast("Link URL is empty")
            }
        }
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
        val minute = runtime % 60
        return if (hours > 0) {
            String.format("•%d hour(s) %02d minute(s)", hours, minute)
        } else {
            String.format("•%02d minute(s)", minute)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID_SERIES = "id_series"
    }
}
