package kg.kunduznbkva.newsapp.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kg.kunduznbkva.newsapp.MainActivity
import kg.kunduznbkva.newsapp.R
import kg.kunduznbkva.newsapp.databinding.FragmentFavoritesBinding
import kg.kunduznbkva.newsapp.ui.NewsViewModel
import kg.kunduznbkva.newsapp.utils.*
import kg.kunduznbkva.newsapp.utils.adapters.NewsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SavedFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: NewsAdapter
    private val TAG_SEARCH = "SEARCH_NEWS"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecycler()
        observeSavedNews()
        initDetailClick()
        initSearchView()
        setupSearchObserver()
        initDeleteArticle()
        initBackIcon()
    }

    private fun initRecycler() {
        adapter = NewsAdapter()
        binding.favoriteNews.adapter = adapter
    }

    private fun observeSavedNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            adapter.differ.submitList(articles)
        }
    }

    private fun initDetailClick() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_navigation_saved_to_navigation_detail,
                bundle
            )
        }
    }

    private fun initBackIcon() {
        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initSearchView() {
        var job: Job? = null
        binding.searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(Constants.searchTimeDelay)
                    query?.let {
                        if (query.isNotEmpty()) {
                            viewModel.searchNews(query)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(Constants.searchTimeDelay)
                    newText?.let {
                        if (newText.isNotEmpty()) {
                            viewModel.searchNews(newText)
                        }
                    }
                }
                return false
            }
        })
    }

    private fun setupSearchObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e(TAG_SEARCH, "An error occured: $it")
                        requireActivity().showToast(getString(R.string.error_message))
                    }
                }

                is Resource.Loading -> showProgressBar()
            }
        }
    }

    private fun initDeleteArticle() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, getString(R.string.deleted) , Snackbar.LENGTH_SHORT).apply {
                        setAction(getString(R.string.undo)) {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.favoriteNews)
        }
    }

    private fun hideProgressBar() {
        binding.progress.gone()
    }

    private fun showProgressBar() {
        binding.progress.visible()
    }
}