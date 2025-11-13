package com.example.utsprojek

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.Calendar

class BiodataFragment : Fragment() {

    private lateinit var etNama: EditText
    private lateinit var etNrp: EditText  // Tambahan
    private lateinit var etEmail: EditText
    private lateinit var etTelepon: EditText
    private lateinit var etAlamat: EditText
    private lateinit var tvTanggalLahir: TextView

    private lateinit var spinnerjurusan: Spinner
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var checkboxHobi: Array<CheckBox>
    private lateinit var btnSimpan: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.biodata_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupSpinner()
        setupDatePicker()
        setupSaveButton()
        setupBottomNavigation(view)
        setupKeyboardDismiss(view)
    }

    private fun initializeViews(view: View) {
        etNama = view.findViewById(R.id.et_nama)
        etNrp = view.findViewById(R.id.et_nrp)  // Tambahan
        etEmail = view.findViewById(R.id.et_email)
        etTelepon = view.findViewById(R.id.et_telepon)
        etAlamat = view.findViewById(R.id.et_alamat)
        tvTanggalLahir = view.findViewById(R.id.tv_tanggal_lahir)
        spinnerjurusan = view.findViewById(R.id.spinner_jurusan)
        radioGroupGender = view.findViewById(R.id.radio_group_gender)
        btnSimpan = view.findViewById(R.id.btn_simpan)

        checkboxHobi = arrayOf(
            view.findViewById(R.id.cb_membaca),
            view.findViewById(R.id.cb_olahraga),
            view.findViewById(R.id.cb_musik),
            view.findViewById(R.id.cb_traveling),
            view.findViewById(R.id.cb_memasak),
            view.findViewById(R.id.cb_gaming),
            view.findViewById(R.id.cb_fotografi),
            view.findViewById(R.id.cb_menulis),
            view.findViewById(R.id.cb_melukis),
            view.findViewById(R.id.cb_berkebun),
            view.findViewById(R.id.cb_memancing),
            view.findViewById(R.id.cb_menonton_film)
        )
    }

    private fun setupSpinner() {
        val jurusanList = arrayOf(
            "Pilih Jurusan",
            "Teknik Elektro",
            "Teknik Mesin",
            "Teknik Industri",
            "Teknik Kimia",
            "Informatika",
            "Sistem Informasi",
            "Teknik Sipil",
            "Teknik Geodesi",
            "Perencanaan Wilayah dan Kota",
            "Teknik Lingkungan",
            "Arsitektur",
            "Desain Interior",
            "Desain Produk",
            "Desain Komunikasi Visual"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            jurusanList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerjurusan.adapter = adapter
    }

    private fun setupDatePicker() {
        tvTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val tanggal = String.format(
                        "%02d/%02d/%d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    tvTanggalLahir.text = tanggal
                    tvTanggalLahir.setTextColor(
                        ContextCompat.getColor(requireContext(), android.R.color.white)
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun setupSaveButton() {
        btnSimpan.setOnClickListener {
            if (validateInput()) {
                showDataSummary()
            }
        }
    }

    private fun validateInput(): Boolean {
        val nama = etNama.text.toString().trim()
        val nrp = etNrp.text.toString().trim()  // Tambahan
        val email = etEmail.text.toString().trim()
        val telepon = etTelepon.text.toString().trim()
        val tanggalLahir = tvTanggalLahir.text.toString()
        val jurusan = spinnerjurusan.selectedItemPosition
        val gender = radioGroupGender.checkedRadioButtonId

        when {
            nama.isEmpty() -> {
                Toast.makeText(context, "Nama harus diisi", Toast.LENGTH_SHORT).show()
                return false
            }
            nrp.isEmpty() -> {
                Toast.makeText(context, "NRP harus diisi", Toast.LENGTH_SHORT).show()
                return false
            }
            nrp.length < 8 -> {
                Toast.makeText(context, "NRP minimal 8 digit", Toast.LENGTH_SHORT).show()
                return false
            }
            email.isEmpty() -> {
                Toast.makeText(context, "Email harus diisi", Toast.LENGTH_SHORT).show()
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return false
            }
            telepon.isEmpty() -> {
                Toast.makeText(context, "Nomor telepon harus diisi", Toast.LENGTH_SHORT).show()
                return false
            }
            tanggalLahir == "Pilih Tanggal Lahir" -> {
                Toast.makeText(context, "Tanggal lahir harus dipilih", Toast.LENGTH_SHORT).show()
                return false
            }
            jurusan == 0 -> {
                Toast.makeText(context, "jurusan harus dipilih", Toast.LENGTH_SHORT).show()
                return false
            }
            gender == -1 -> {
                Toast.makeText(context, "Jenis kelamin harus dipilih", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun showDataSummary() {
        val nama = etNama.text.toString()
        val nrp = etNrp.text.toString()  // Tambahan
        val email = etEmail.text.toString()
        val telepon = etTelepon.text.toString()
        val alamat = etAlamat.text.toString().ifEmpty { "-" }
        val tanggalLahir = tvTanggalLahir.text.toString()
        val jurusan = spinnerjurusan.selectedItem.toString()

        val genderButton = view?.findViewById<RadioButton>(radioGroupGender.checkedRadioButtonId)
        val gender = genderButton?.text.toString()

        val hobiList = mutableListOf<String>()
        checkboxHobi.forEach { checkbox ->
            if (checkbox.isChecked) {
                hobiList.add(checkbox.text.toString())
            }
        }
        val hobi = if (hobiList.isEmpty()) "Tidak ada" else hobiList.joinToString(", ")

        val message = """
            Data Biodata:
            
            Nama: $nama
            NRP: $nrp
            Email: $email
            Telepon: $telepon
            Alamat: $alamat
            Tanggal Lahir: $tanggalLahir
            Jenis Kelamin: $gender
            Jurusan: $jurusan
            Hobi: $hobi
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("Ringkasan Biodata")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun setupBottomNavigation(view: View) {
        view.findViewById<View>(R.id.nav_contact)?.setOnClickListener {
            navigateToFragment(ContactFragment())
        }
        view.findViewById<View>(R.id.nav_news)?.setOnClickListener {
            navigateToFragment(NewsFragment())
        }
        view.findViewById<View>(R.id.nav_cuaca)?.setOnClickListener {
            navigateToFragment(CuacaFragment())
        }
        view.findViewById<View>(R.id.nav_calculator)?.setOnClickListener {
            navigateToFragment(CalculatorFragment())
        }
        highlightCurrentNav(view, R.id.nav_biodata)
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun highlightCurrentNav(view: View, currentNavId: Int) {
        val navIds = listOf(
            R.id.nav_contact,
            R.id.nav_news,
            R.id.nav_biodata,
            R.id.nav_cuaca,
            R.id.nav_calculator
        )
        navIds.forEach { navId ->
            view.findViewById<View>(navId)?.alpha = if (navId == currentNavId) 1.0f else 0.6f
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupKeyboardDismiss(view: View) {
        view.findViewById<View>(R.id.navbar)?.setOnClickListener {
            hideKeyboard(it)
        }

        etNama.setOnEditorActionListener { v, _, _ ->
            hideKeyboard(v)
            false
        }

        etNrp.setOnEditorActionListener { v, _, _ ->
            hideKeyboard(v)
            false
        }

        etEmail.setOnEditorActionListener { v, _, _ ->
            hideKeyboard(v)
            false
        }

        etTelepon.setOnEditorActionListener { v, _, _ ->
            hideKeyboard(v)
            false
        }

        etAlamat.setOnEditorActionListener { v, _, _ ->
            hideKeyboard(v)
            false
        }
    }
}