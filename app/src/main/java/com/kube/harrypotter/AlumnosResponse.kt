package com.kube.harrypotter

import com.google.gson.annotations.SerializedName

data class AlumnosResponse(
    @SerializedName("listaAlumnos") var listaAlumnos: ArrayList<Alumno>
)
