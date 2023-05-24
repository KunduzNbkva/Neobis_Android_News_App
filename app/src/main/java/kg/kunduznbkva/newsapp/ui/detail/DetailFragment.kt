package kg.kunduznbkva.newsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kg.kunduznbkva.newsapp.MainActivity
import kg.kunduznbkva.newsapp.R
import kg.kunduznbkva.newsapp.databinding.FragmentDetailBinding
import kg.kunduznbkva.newsapp.model.Article
import kg.kunduznbkva.newsapp.ui.NewsViewModel

class DetailFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        if (article.saved) binding.likeButton.isPressed = true
        initWebView(article)
        initBackIcon()
        initFab(article)
    }

    private fun initWebView(article: Article) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }

    private fun initBackIcon() {
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initFab(article: Article) {
        binding.likeButton.setOnClickListener {
            binding.likeButton.setImageDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.favorite_full))
            article.saved = true
            viewModel.saveArticle(article)
            view?.let { it1 -> Snackbar.make(it1,getString(R.string.snackbar_message),Snackbar.LENGTH_SHORT).show() }
        }
    }
}