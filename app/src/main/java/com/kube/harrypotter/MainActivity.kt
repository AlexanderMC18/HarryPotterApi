package com.kube.harrypotter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kube.harrypotter.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AlumnoAdapter.OnItemClicked {

    lateinit var binding: ActivityMainBinding

    lateinit var adatador: AlumnoAdapter

    var listaAlumnos = arrayListOf<Alumno>()


    var alumno = Alumno(-1, "","", "", "")

    var isEditando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvAlumno.layoutManager = LinearLayoutManager(this)
        setupRecyclerView()

        obtenerAlumno()

        binding.btnAddUpdate.setOnClickListener {
            var isValido = validarCampos()
            if (isValido) {
                if (!isEditando) {
                    agregarAlumno()
                } else {
                    actualizarAlumno()
                }
            } else {
                Toast.makeText(this, "Se deben llenar los campos", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun setupRecyclerView() {
        adatador = AlumnoAdapter(this, listaAlumnos)
        adatador.setOnClick(this@MainActivity)
        binding.rvAlumno.adapter = adatador

    }

    fun validarCampos(): Boolean {
        return !(binding.etNombre.text.isNullOrEmpty() || binding.etCasa.text.isNullOrEmpty() || binding.etVarita.text.isNullOrEmpty() || binding.etDescripcion.text.isNullOrEmpty())
    }

    fun obtenerAlumno() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.obtenerAlumno()
            runOnUiThread {
                if (call.isSuccessful) {
                    listaAlumnos = call.body()!!.listaAlumnos
                    setupRecyclerView()
                } else {
                    Toast.makeText(this@MainActivity, "ERROR CONSULTAR TODOS", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun agregarAlumno() {

        this.alumno.idalumno = -1
        this.alumno.nombre = binding.etNombre.text.toString()
        this.alumno.casa = binding.etCasa.text.toString()
        this.alumno.varita = binding.etVarita.text.toString()
        this.alumno.descripcion = binding.etDescripcion.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.agregarAlumno(alumno)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MainActivity, call.body().toString(), Toast.LENGTH_LONG).show()
                    obtenerAlumno()
                    limpiarCampos()
                    limpiarObjeto()

                } else {
                    Toast.makeText(this@MainActivity, "ERROR ADD", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun actualizarAlumno() {

        this.alumno.nombre = binding.etNombre.text.toString()
        this.alumno.casa = binding.etCasa.text.toString()
        this.alumno.varita = binding.etVarita.text.toString()
        this.alumno.descripcion = binding.etDescripcion.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.actualizarAlumno(alumno.idalumno, alumno)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MainActivity, call.body().toString(), Toast.LENGTH_LONG).show()
                    obtenerAlumno()
                    limpiarCampos()
                    limpiarObjeto()

                    binding.btnAddUpdate.setText("Agregar Alumno")
                    binding.btnAddUpdate.backgroundTintList = resources.getColorStateList(R.color.green)
                    isEditando = false
                }
            }
        }
    }

    fun limpiarCampos() {
        binding.etNombre.setText("")
        binding.etCasa.setText("")
        binding.etVarita.setText("")
        binding.etDescripcion.setText("")
    }

    fun limpiarObjeto() {
        this.alumno.idalumno = -1
        this.alumno.nombre = ""
        this.alumno.casa = ""
        this.alumno.varita = ""
        this.alumno.descripcion = ""
    }

    override fun editarAlumno(alumno: Alumno) {
        binding.etNombre.setText(alumno.nombre)
        binding.etCasa.setText(alumno.casa)
        binding.etVarita.setText(alumno.varita)
        binding.etDescripcion.setText(alumno.descripcion)
        binding.btnAddUpdate.setText("Actualizar Alumno")
        binding.btnAddUpdate.backgroundTintList = resources.getColorStateList(R.color.purple_500)
        this.alumno = alumno
        isEditando = true
    }

    override fun borrarAlumno(idAlumno: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.borrarAlumno(idAlumno)
            runOnUiThread {
                if (call.isSuccessful) {
                    Toast.makeText(this@MainActivity, call.body().toString(), Toast.LENGTH_LONG).show()
                    obtenerAlumno()
                }
            }
        }
    }
}