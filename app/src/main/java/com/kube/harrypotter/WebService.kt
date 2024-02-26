package com.kube.harrypotter

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object AppConstantes {
    const val BASE_URL = "http://192.168.189.222:3000"
}

interface WebService {

    @GET("/Alumno")
    suspend fun obtenerAlumno(): Response<AlumnosResponse>

    @GET("/Alumno/{idalumno}")
    suspend fun obtenerAlumno(
        @Path("idalumno") idalumno: Int
    ): Response<Alumno>

    @POST("/Alumno/add")
    suspend fun agregarAlumno(
        @Body alumno: Alumno
    ): Response<String>

    @PUT("/Alumno/update/{idalumno}")
    suspend fun actualizarAlumno(
        @Path("idalumno") idalumno: Int,
        @Body alumno: Alumno
    ): Response<String>

    @DELETE("/Alumno/delete/{idalumno}")
    suspend fun borrarAlumno(
        @Path("idalumno") idalumno: Int
    ): Response<String>
}

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}