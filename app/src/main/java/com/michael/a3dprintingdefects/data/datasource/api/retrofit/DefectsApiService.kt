package com.michael.a3dprintingdefects.data.datasource.api.retrofit

import com.michael.a3dprintingdefects.data.model.DefectRepositoryItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DefectsApiService {

    @GET("/api/client/defects")
    fun getAllDefects(): Observable<List<DefectRepositoryItem>>

    @GET("/api/client/defects/{id}")
    fun getDefect(@Path("id") id: Int): Observable<DefectRepositoryItem>

    @GET("/api/client/defects/picture/{id}")
    fun getImage(@Path("id") id: Int): Observable<String>
}