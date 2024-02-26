package com.kube.harrypotter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AlumnoAdapter(
    var context: Context,
    var listaalumnos: ArrayList<Alumno>
): RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    private var onClick: OnItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_alumno, parent, false)
        return AlumnoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = listaalumnos.get(position)

        holder.tvIdAlumno.text = alumno.idalumno.toString()
        holder.tvNombre.text = alumno.nombre
        holder.tvCasa.text = alumno.casa
        holder.tvVarita.text = alumno.varita
        holder.tvDescripcion.text = alumno.descripcion

        holder.btnEditar.setOnClickListener {
            onClick?.editarAlumno(alumno)
        }

        holder.btnBorrar.setOnClickListener {
            onClick?.borrarAlumno(alumno.idalumno)
        }
    }

    override fun getItemCount(): Int {
        return listaalumnos.size
    }

    inner class AlumnoViewHolder(itemView: View): ViewHolder(itemView) {
        val tvIdAlumno = itemView.findViewById(R.id.tvIdAlumno) as TextView
        val tvNombre = itemView.findViewById(R.id.tvNombre) as TextView
        val tvCasa = itemView.findViewById(R.id.tvCasa) as TextView
        val tvVarita = itemView.findViewById(R.id.tvVarita) as TextView
        val tvDescripcion = itemView.findViewById(R.id.tvDescripcion) as TextView
        val btnEditar = itemView.findViewById(R.id.btnEditar) as Button
        val btnBorrar = itemView.findViewById(R.id.btnBorrar) as Button
    }

    interface OnItemClicked {
        fun editarAlumno(alumno: Alumno)
        fun borrarAlumno(idAlumno: Int)
    }

    fun setOnClick(onClick: OnItemClicked?) {
        this.onClick = onClick
    }

}