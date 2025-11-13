package com.example.utsprojek

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

class ContactFragment : Fragment() {

    data class Contact(
        val name: String,
        val phone: String,
        val imageRes: Int = R.drawable.person
    )

    private lateinit var contactContainer: LinearLayout
    private lateinit var searchInput: EditText
    private var allContacts = mutableListOf<Contact>()
    private var filteredContacts = mutableListOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactContainer = view.findViewById(R.id.contact_container)
        searchInput = view.findViewById(R.id.search_contact)

        loadContacts()
        setupSearch()
        setupBottomNavigation(view)
        displayContacts(filteredContacts)
    }

    private fun loadContacts() {
        allContacts = mutableListOf(
            Contact("Dimas Kanjeng", "0812-1321-4251", R.raw.dimas),
            Contact("Hesti Suryaningsih", "0812-5563-3122", R.raw.hesti),
            Contact("Suparman taman", "0812-3357-0643", R.raw.suparman),
            Contact("Juminten tenten", "0812-1021-0124", R.raw.juminten),
            Contact("eko Prasetyo", "0812-6301-0355", R.raw.eko),
            Contact("Saritem item", "0812-7093-6306", R.raw.saritem),
            Contact("Bambang Wicaksono", "0812-0439-2317", R.raw.bambang),
            Contact("Siti jubaedah", "0812-7530-2438", R.raw.siti),
            Contact("Paijo parjo", "0812-1134-6589", R.raw.paijo),
            Contact("Sumiati", "0812-0130-3010", R.raw.sumiati),
            Contact("Jamal Kamal", "0812-0012-6011", R.raw.jamal),
            Contact("Markonah Markinah", "0812-0754-0012", R.raw.markonah),
            Contact("Tumijo Sarjojo", "0812-7743-6013", R.raw.tumijo),
            Contact("Maya Muya", "0812-0104-6014", R.raw.maya),
            Contact("Nando Nandi", "0812-0011-0015", R.raw.nando)
        )

        // Sort by name
        allContacts.sortBy { it.name }
        filteredContacts.addAll(allContacts)
    }

    private fun setupSearch() {
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContacts(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterContacts(query: String) {
        filteredContacts.clear()
        val searchQuery = query.lowercase().trim()

        if (searchQuery.isEmpty()) {
            filteredContacts.addAll(allContacts)
        } else {
            filteredContacts.addAll(
                allContacts.filter { contact ->
                    contact.name.lowercase().contains(searchQuery) ||
                            contact.phone.contains(searchQuery)
                }
            )
        }

        displayContacts(filteredContacts)
    }

    private fun displayContacts(contacts: List<Contact>) {
        contactContainer.removeAllViews()

        if (contacts.isEmpty()) {
            val emptyView = createEmptyView()
            contactContainer.addView(emptyView)
            return
        }

        var currentLetter = ""

        contacts.forEach { contact ->
            val firstLetter = contact.name.first().uppercaseChar().toString()

            // Add alphabet separator if new letter
            if (firstLetter != currentLetter) {
                currentLetter = firstLetter
                contactContainer.addView(createAlphabetSeparator(firstLetter))
            }

            contactContainer.addView(createContactCard(contact))
        }
    }

    private fun createAlphabetSeparator(letter: String): View {
        val textView = TextView(requireContext()).apply {
            text = letter
            setTextColor(Color.parseColor("#E4C580"))
            textSize = 24f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(8))
        }
        return textView
    }

    private fun createEmptyView(): View {
        val linearLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            gravity = android.view.Gravity.CENTER
            setPadding(dpToPx(32), dpToPx(64), dpToPx(32), dpToPx(64))
        }

        val textView = TextView(requireContext()).apply {
            text = "Tidak ada kontak ditemukan"
            setTextColor(Color.parseColor("#697881"))
            textSize = 16f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
        }

        linearLayout.addView(textView)
        return linearLayout
    }

    private fun createContactCard(contact: Contact): View {
        val cardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
            radius = dpToPx(16).toFloat()
            cardElevation = dpToPx(4).toFloat()
            setCardBackgroundColor(Color.parseColor("#CC3D3955"))
        }

        val mainLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        }

        // Profile Image
        val imageCard = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dpToPx(60), dpToPx(60))
            radius = dpToPx(30).toFloat()
            cardElevation = 0f
        }

        val profileImage = ImageView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setImageResource(contact.imageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        imageCard.addView(profileImage)
        mainLayout.addView(imageCard)

        // Contact Info
        val infoLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                marginStart = dpToPx(16)
            }
        }

        val nameText = TextView(requireContext()).apply {
            text = contact.name
            setTextColor(Color.parseColor("#E9E6F2"))
            textSize = 18f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
        infoLayout.addView(nameText)

        val phoneText = TextView(requireContext()).apply {
            text = contact.phone
            setTextColor(Color.parseColor("#BEBAD0"))
            textSize = 14f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.bitcoin)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = dpToPx(4)
            }
        }
        infoLayout.addView(phoneText)
        mainLayout.addView(infoLayout)

        // Action Buttons (Static - No Click Function)
        val actionLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
        }

        val chatButton = ImageButton(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dpToPx(40), dpToPx(40))
            setBackgroundResource(android.R.color.transparent)
            setImageResource(R.drawable.chat)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
            setColorFilter(Color.parseColor("#BCAFD9"))
            isEnabled = false
        }
        actionLayout.addView(chatButton)

        val callButton = ImageButton(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dpToPx(40), dpToPx(40)).apply {
                marginStart = dpToPx(8)
            }
            setBackgroundResource(android.R.color.transparent)
            setImageResource(R.drawable.call)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
            setColorFilter(Color.parseColor("#D7C7E8"))
            isEnabled = false
        }
        actionLayout.addView(callButton)

        mainLayout.addView(actionLayout)
        cardView.addView(mainLayout)

        return cardView
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun setupBottomNavigation(view: View) {
        view.findViewById<View>(R.id.nav_news)?.setOnClickListener {
            navigateToFragment(NewsFragment())
        }
        view.findViewById<View>(R.id.nav_calculator)?.setOnClickListener {
            navigateToFragment(CalculatorFragment())
        }
        view.findViewById<View>(R.id.nav_biodata)?.setOnClickListener {
            navigateToFragment(BiodataFragment())
        }
        view.findViewById<View>(R.id.nav_cuaca)?.setOnClickListener {
            navigateToFragment(CuacaFragment())
        }
        highlightCurrentNav(view, R.id.nav_contact)
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
}