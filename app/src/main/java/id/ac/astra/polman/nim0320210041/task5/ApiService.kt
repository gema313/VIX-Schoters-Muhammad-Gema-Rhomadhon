package id.ac.astra.polman.nim0320210041.task5

import android.os.Handler
import android.os.Looper
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ApiService {
    private val client = OkHttpClient()
    private val mainHandler = Handler(Looper.getMainLooper())

    fun getArticles(
        apiKey: String,
        onSuccess: (List<Article>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "https://newsapi.org/v2/everything?q=tesla&from=2023-06-01&sortBy=publishedAt&apiKey=$apiKey"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mainHandler.post {
                    onError(e.message ?: "Request failed")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val articles = parseResponse(responseBody)
                mainHandler.post {
                    onSuccess(articles)
                }
            }
        })
    }

    private fun parseResponse(responseBody: String?): List<Article> {
        val articles = mutableListOf<Article>()
        responseBody?.let {
            val jsonObject = JSONObject(it)
            val jsonArray = jsonObject.getJSONArray("articles")

            for (i in 0 until jsonArray.length()) {
                val articleJson = jsonArray.getJSONObject(i)
                val title = articleJson.getString("title")
                val description = articleJson.getString("description")
                val url = articleJson.getString("url")
                val image = articleJson.getString("urlToImage")
                val author = articleJson.getString("author")
                val publishedAt = articleJson.getString("publishedAt")
                val article = Article(title, description, url, image, author, publishedAt)
                articles.add(article)
            }
        }
        return articles
    }
}
