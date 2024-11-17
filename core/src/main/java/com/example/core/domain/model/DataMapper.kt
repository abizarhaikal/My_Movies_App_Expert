package com.example.core.domain.model

import com.example.core.data.ResponseCredits
import com.example.core.data.ResponseDetailMovie
import com.example.core.data.ResponseDetailSeries
import com.example.core.data.ResultsItemActors
import com.example.core.data.ResultsItemNow
import com.example.core.data.ResultsItemSeries
import com.example.core.data.local.MoviesEntity

object DataMapper {

    //    UNTUK MAP KE DOMAIN
    fun mapEntityToDomain(input: MoviesEntity): Movies {
        return Movies(
            id = input.id,
            posterPath = input.posterPath,
            title = input.title,
            overview = input.overview,
            isFavorite = input.isFavorite
        )
    }

    // MAP KE ENTITY
    fun mapDomainToEntity(input: Movies): MoviesEntity {
        return MoviesEntity(
            id = input.id,
            posterPath = input.posterPath,
            title = input.title,
            overview = input.overview,
            isFavorite = input.isFavorite
        )
    }

    //    MAP KE DOMAIN RESPONSE
    fun mapResponseToDomain(input: List<ResultsItemNow>): List<MoviesNow> {
        return input.map {
            MoviesNow(
                id = it.id,
                posterPath = it.posterPath,
            )
        }
    }

    fun mapResponseToDomainSeries(input: List<ResultsItemSeries>): List<SeriesNow> {
        return input.map {
            SeriesNow(
                id = it.id,
                posterPath = it.posterPath,
            )
        }
    }

    fun mapResponseToDomainActorPopular(input: List<ResultsItemActors>): List<ActorFavorite> {
        return input.map {
            ActorFavorite(
                id = it.id,
                name = it.name,
                profilePath = it.profilePath ?: "",
            )
        }
    }

    fun mapResponseToDomainDetail(movie: ResponseDetailMovie): MovieDetail {
        return MovieDetail(
            id = movie.id,
            title = movie.title,
            revenue = movie.revenue,
            genres = movie.genres.map { Genre(it.id, it.name) },
            popularity = movie.popularity,
            productionCountries = movie.productionCountries.map {
                ProductionCountry(
                    it.iso31661,
                    it.name
                )
            },
            overview = movie.overview,
            runtime = movie.runtime,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            homepage = movie.homepage
        )
    }

    fun mapResponseToDomainCast(response: ResponseCredits): List<CastItemModel> {
        return response.cast.map { castItem ->
            CastItemModel(
                id = castItem.id,
                name = castItem.name,
                profilePath = castItem.profilePath ?: "",
                // tambahkan properti lain yang diperlukan
            )
        }
    }


    fun mapResponseToDomainSeries(response: ResponseDetailSeries): SeriesDetailModel {
        return SeriesDetailModel(
            genres = response.genres.map { genre ->
                Genre(
                    id = genre.id,
                    name = genre.name
                )
            },
            popularity = response.popularity,
            productionCountries = response.productionCountries.map { country ->
                ProductionCountry(
                    iso31661 = country.iso31661,
                    name = country.name
                )
            },
            id = response.id,
            firstAirDate = response.firstAirDate,
            overview = response.overview,
            posterPath = response.posterPath,
            name = response.name,
            episodeRunTime = response.episodeRunTime,
            homepage = response.homepage
        )
    }


}

