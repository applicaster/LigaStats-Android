package com.applicaster.liga.statsscreenplugin.data.api

import com.applicaster.app.CustomApplication
import com.applicaster.liga.statsscreenplugin.data.model.*
import com.applicaster.liga.statsscreenplugin.plugin.PluginDataRepository
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * To implement this class I used as reference this article
 * https://medium.com/@elye.project/kotlin-and-retrofit-2-tutorial-with-working-codes-333a4422a890
 */
interface OptaApiService {

    @GET("standings/{token}")
    fun getGroupCards(@Path("token") token: String,
                      @Header("Referer") referer: String,
                      @Query("_rt") rt: String,
                      @Query("_fmt") format: String,
                      @Query("tmcl") tournamentId: String,
                      @Query("_lcl") localization: String):
            Observable<GroupModel.Group>

    @GET("matchstats/{token}")
    fun getMatches(@Path("token") token: String,
                   @Header("Referer") referer: String,
                   @Query("_rt") rt: String,
                   @Query("_fmt") format: String,
                   @Query("fx") fixtureId: String,
                   @Query("detailed") detailed: String,
                   @Query("_lcl") localization: String):
            Observable<MatchModel.Match>

    @GET("seasonstats/{token}")
    fun getTeamStats(@Path("token") token: String,
                     @Header("Referer") referer: String,
                     @Query("_rt") rt: String,
                     @Query("_fmt") format: String,
                     @Query("tmcl") tournamentId: String,
                     @Query("ctst") contestantId: String,
                     @Query("detailed") detailed: String,
                     @Query("_lcl") localization: String):
            Observable<TeamModel.Team>

    @GET("squads/{token}")
    fun getSquads(@Path("token") token: String,
                  @Header("Referer") referer: String,
                  @Query("_rt") rt: String,
                  @Query("_fmt") format: String,
                  @Query("tmcl") tournamentId: String,
                  @Query("ctst") contestantId: String,
                  @Query("detailed") detailed: String,
                  @Query("_lcl") localization: String):
            Observable<PlayerSquadModel.PlayerSquad>

    @GET("playercareer/{token}")
    fun getPlayerCareer(@Path("token") token: String,
                        @Header("Referer") referer: String,
                        @Query("_rt") rt: String,
                        @Query("_fmt") format: String,
                        @Query("prsn") personId: String,
                        @Query("_lcl") localization: String):
            Observable<PlayerCareerModel.PlayerCareer>

    @GET("tournamentschedule/{token}")
    fun getAllMatches(@Path("token") token: String,
                      @Header("Referer") referer: String,
                      @Query("_rt") rt: String,
                      @Query("_fmt") format: String,
                      @Query("tmcl") tournamentId: String,
                      @Query("_lcl") localization: String):
            Observable<AllMatchesModel.AllMatches>

    @GET("match/{token}")
    fun getMatchDetails(@Path("token") token: String,
                        @Header("Referer") referer: String,
                        @Query("_rt") rt: String,
                        @Query("_fmt") format: String,
                        @Query("fx") fixtureId: String,
                        @Query("_lcl") localization: String):
            Observable<MatchDetailsModel.FullMatchDetails>

    @GET("trophies/{token}")
    fun getTrophies(@Path("token") token: String,
                    @Header("Referer") referer: String,
                    @Query("comp") competitionId: String,
                    @Query("_fmt") format: String,
                    @Query("_rt") fixtureId: String,
                    @Query("_lcl") localization: String):
            Observable<TrophiesModel.Trophies>

    @GET("match/{token}")
    fun getAllMatchesWithDetails(@Path("token") token: String,
                                 @Header("Referer") referer: String,
                                 @Query("_rt") rt: String,
                                 @Query("_fmt") format: String,
                                 @Query("tmcl") tournamentId: String,
                                 @Query("live") live: String,
                                 @Query("_ordSrt") sort: String,
                                 @Query("_pgNm") pageNumber: String,
                                 @Query("_pgSz") pageSize: String,
                                 @Query("_lcl") localization: String):
            Observable<AllMatchesWithDetailsModel.AllMatchesWithDetails>

    companion object {
        private const val BASE_URL = "http://api.performfeeds.com/soccerdata/"
        fun create(): OptaApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            val client: OkHttpClient = OkHttpClient.Builder()
                    .cache(CacheProvider.provideCache(CustomApplication.getAppContext()))
                    .addInterceptor(CacheProvider.ForceCacheInterceptor())
                    .addInterceptor(interceptor)
                    .addInterceptor {
                        val request = it.request()
                                .newBuilder()
                                .addHeader("AppId", PluginDataRepository.INSTANCE.getAppId())
                                .build()
                        return@addInterceptor it.proceed(request)
                    }
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(OptaApiService::class.java)
        }
    }


}