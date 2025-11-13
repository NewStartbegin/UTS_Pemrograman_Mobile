package com.example.utsprojek

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

class NewsFragment : Fragment(){
    data class NewsItem(
        val title: String,
        val description: String,
        val category: String,
        val date: String,
        val source: String,
        val imageRes: Int = 0
    )
    private lateinit var newsContainer: LinearLayout
    private lateinit var searchInput: EditText
    private lateinit var newsScrollView: ScrollView
    private var isNewsEmpty = false
    private var allNews = mutableListOf<NewsItem>()
    private var filteredNews = mutableListOf<NewsItem>()
    private var currentCategory = "Semua"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsContainer = view.findViewById(R.id.news_container)
        searchInput = view.findViewById(R.id.search_input)
        newsScrollView = view.findViewById(R.id.news_scroll)

        // Setup scroll prevention when empty
        setupScrollBehavior()

        loadNewsData()
        setupSearch()
        setupCategories(view)
        setupBottomNavigation(view)
        displayNews(allNews)
    }

    private fun setupScrollBehavior() {
        newsScrollView.setOnTouchListener { _, _ ->
            isNewsEmpty
        }
    }

    private fun loadNewsData() {
        allNews = mutableListOf(
            NewsItem(
                title = "Perkembangan AI Mengubah Industri Teknologi",
                description = "Artificial Intelligence terus berkembang pesat dan mengubah cara kerja berbagai industri di seluruh dunia.",
                category = "Teknologi",
                date = "6 Nov 2025",
                source = "TechCrunch Indonesia",
                imageRes = R.raw.b1
            ),
            NewsItem(
                title = "Ekonomi Digital Indonesia Tumbuh 15%",
                description = "Pertumbuhan ekonomi digital Indonesia mencapai 15% tahun ini, didorong oleh e-commerce dan fintech.",
                category = "Ekonomi",
                date = "6 Nov 2025",
                source = "Kompas",
                imageRes = R.raw.b2
            ),
            NewsItem(
                title = "Tim Nasional Raih Medali Emas SEA Games",
                description = "Prestasi gemilang diraih tim nasional Indonesia di ajang SEA Games 2025.",
                category = "Olahraga",
                date = "5 Nov 2025",
                source = "Detik Sport",
                imageRes = R.raw.b3
            ),
            NewsItem(
                title = "Kebijakan Baru Pemerintah untuk Pendidikan",
                description = "Pemerintah mengumumkan kebijakan baru untuk meningkatkan kualitas pendidikan melalui digitalisasi.",
                category = "Politik",
                date = "5 Nov 2025",
                source = "CNN Indonesia",
                imageRes = R.raw.b4
            ),
            NewsItem(
                title = "Film Indonesia Raih Penghargaan Internasional",
                description = "Karya sineas Indonesia meraih penghargaan di festival film internasional bergengsi.",
                category = "Hiburan",
                date = "4 Nov 2025",
                source = "Kapanlagi",
                imageRes = R.raw.b5
            ),
            NewsItem(
                title = "Startup Indonesia Raih Pendanaan Besar",
                description = "Startup teknologi Indonesia mendapat pendanaan besar dari investor asing untuk ekspansi bisnis.",
                category = "Ekonomi",
                date = "4 Nov 2025",
                source = "Tech in Asia",
                imageRes = R.raw.b6
            ),
            NewsItem(
                title = "Inovasi Energi Terbarukan di Indonesia",
                description = "Indonesia mengembangkan teknologi energi terbarukan untuk mengurangi ketergantungan pada fosil.",
                category = "Teknologi",
                date = "3 Nov 2025",
                source = "Tempo",
                imageRes = R.raw.b7
            ),
            NewsItem(
                title = "Konser Musik Terbesar Tahun Ini",
                description = "Konser musik dengan lineup artis internasional akan digelar di Jakarta bulan depan.",
                category = "Hiburan",
                date = "3 Nov 2025",
                source = "Rolling Stone Indonesia",
                imageRes = R.raw.b8
            )
        )
        filteredNews.addAll(allNews)
    }

    private fun setupSearch() {
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterNews(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterNews(query: String) {
        filteredNews.clear()
        val searchQuery = query.lowercase().trim()
        filteredNews.addAll(
            allNews.filter { news ->
                val matchesCategory = currentCategory == "Semua" || news.category == currentCategory
                val matchesSearch = searchQuery.isEmpty() ||
                        news.title.lowercase().contains(searchQuery) ||
                        news.description.lowercase().contains(searchQuery) ||
                        news.category.lowercase().contains(searchQuery)
                matchesCategory && matchesSearch
            }
        )
        displayNews(filteredNews)
    }

    private fun setupCategories(view: View) {
        val categories = mapOf(
            R.id.category_all to "Semua",
            R.id.category_politik to "Politik",
            R.id.category_ekonomi to "Ekonomi",
            R.id.category_teknologi to "Teknologi",
            R.id.category_olahraga to "Olahraga",
            R.id.category_hiburan to "Hiburan"
        )
        categories.forEach { (categoryId, categoryName) ->
            view.findViewById<CardView>(categoryId)?.setOnClickListener {
                currentCategory = categoryName
                updateCategoryUI(view, categoryId)
                filterNews(searchInput.text.toString())
            }
        }
    }

    private fun updateCategoryUI(view: View, selectedId: Int) {
        val categoryIds = listOf(
            R.id.category_all,
            R.id.category_politik,
            R.id.category_ekonomi,
            R.id.category_teknologi,
            R.id.category_olahraga,
            R.id.category_hiburan
        )
        categoryIds.forEach { categoryId ->
            val cardView = view.findViewById<CardView>(categoryId)
            val textView = cardView.getChildAt(0) as TextView
            if (categoryId == selectedId) {
                cardView.setCardBackgroundColor(Color.parseColor("#A8756F"))
                textView.setTextColor(Color.parseColor("#FFF4EA"))
            } else {
                cardView.setCardBackgroundColor(Color.parseColor("#6B5548"))
                textView.setTextColor(Color.parseColor("#C8AA91"))
            }
        }
    }

    private fun displayNews(newsList: List<NewsItem>) {
        newsContainer.removeAllViews()

        if (newsList.isEmpty()) {
            isNewsEmpty = true  // Set flag to disable scroll
            val emptyView = LayoutInflater.from(context)
                .inflate(R.layout.news_kosong, newsContainer, false)
            newsContainer.addView(emptyView)
            return
        }

        isNewsEmpty = false  // Reset flag to enable scroll
        newsList.forEach { news ->
            newsContainer.addView(createNewsCard(news))
        }
    }

    private fun createNewsCard(news: NewsItem): View {
        val cardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(16)
            }
            radius = dpToPx(16).toFloat()
            cardElevation = dpToPx(4).toFloat()
            setCardBackgroundColor(Color.parseColor("#AA182730"))
        }
        val mainLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
        }
        val imageView = ImageView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(180)
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            setBackgroundColor(Color.parseColor("#E5E7EB"))
            if (news.imageRes != 0) setImageResource(news.imageRes)
        }
        mainLayout.addView(imageView)
        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        }
        val categoryText = TextView(requireContext()).apply {
            text = news.category.uppercase()
            setTextColor(Color.parseColor("#C4937E"))
            textSize = 12f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(8) }
        }
        contentLayout.addView(categoryText)
        val titleText = TextView(requireContext()).apply {
            text = news.title
            setTextColor(Color.parseColor("#E0DAD4"))
            textSize = 18f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            maxLines = 2
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(8) }
        }
        contentLayout.addView(titleText)
        val descText = TextView(requireContext()).apply {
            text = news.description
            setTextColor(Color.parseColor("#E0DAD4"))
            textSize = 14f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            maxLines = 3
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(12) }
        }
        contentLayout.addView(descText)
        val footerLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        val dateText = TextView(requireContext()).apply {
            text = news.date
            setTextColor(Color.parseColor("#E0DAD4"))
            textSize = 12f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        footerLayout.addView(dateText)
        val sourceText = TextView(requireContext()).apply {
            text = news.source
            setTextColor(Color.parseColor("#E0DAD4"))
            textSize = 12f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.libre)
        }
        footerLayout.addView(sourceText)
        contentLayout.addView(footerLayout)
        mainLayout.addView(contentLayout)
        cardView.addView(mainLayout)
        return cardView
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun setupBottomNavigation(view: View) {
        view.findViewById<View>(R.id.nav_contact)?.setOnClickListener {
            navigateToFragment(ContactFragment())
        }
        view.findViewById<View>(R.id.nav_calculator)?.setOnClickListener {
            navigateToFragment(CalculatorFragment())
        }
        view.findViewById<View>(R.id.nav_cuaca)?.setOnClickListener {
            navigateToFragment(CuacaFragment())
        }
        view.findViewById<View>(R.id.nav_biodata)?.setOnClickListener {
            navigateToFragment(BiodataFragment())
        }
        highlightCurrentNav(view, R.id.nav_news)
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