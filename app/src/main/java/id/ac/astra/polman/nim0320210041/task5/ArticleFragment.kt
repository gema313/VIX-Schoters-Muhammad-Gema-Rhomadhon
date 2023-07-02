package id.ac.astra.polman.nim0320210041.task5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArticleFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var apiService: ApiService

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter(emptyList())
        recyclerView.adapter = articleAdapter

        apiService = ApiService()

        // Panggil fungsi untuk mendapatkan data dari API
        apiService.getArticles(
            apiKey = "9586117bb2634054bc621d94d9400ea3",
            onSuccess = { articles ->
                // Update adapter dengan data yang diperoleh
                articleAdapter.articles = articles
                articleAdapter.notifyDataSetChanged()
            },
            onError = { error ->
                // Tampilkan pesan kesalahan jika terjadi masalah
                showToast(error)
            }
        )

        // Atur listener untuk menangani klik item RecyclerView
        articleAdapter.setOnItemClickListener { article ->
            // Buka tampilan detail menggunakan browser
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url)
            startActivity(intent)
        }

        return rootView
    }
}
