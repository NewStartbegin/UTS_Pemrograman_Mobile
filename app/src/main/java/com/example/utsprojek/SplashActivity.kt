package com.example.utsprojek

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        val judul = findViewById<TextView>(R.id.judul_projek)
        val gambar = findViewById<ImageView>(R.id.gambar_murid)
        val nama = findViewById<TextView>(R.id.name_murid)
        val nrp = findViewById<TextView>(R.id.nrp_murid)
        val kelas = findViewById<TextView>(R.id.kelas_murid)

        judul.text = "All In One"
        gambar.setImageResource(R.raw.back_splash)
        nama.text = "Arsyad Effendi"
        nrp.text = "152023107"
        kelas.text = "AA"

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}