package kg.kunduznbkva.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.newsapp.MainActivity
import kg.kunduznbkva.newsapp.R
import kg.kunduznbkva.newsapp.databinding.FragmentMainBinding
import kg.kunduznbkva.newsapp.ui.NewsViewModel
import kg.kunduznbkva.newsapp.utils.*
import kg.kunduznbkva.newsapp.utils.adapters.NewsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: NewsAdapter
    private val TAG = "MainFragment"
    private val TAG_SEARCH = "SEARCH_NEWS"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecycler()
        initDetailClick()
        initFavoritesPage()
        setupObserver()
        initSearchView()
        setupSearchObserver()
    }

    private fun initFavoritesPage() {
        binding.favoriteIcon.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_main_to_navigation_saved)
        }
    }

    private fun initRecycler() {
        adapter = NewsAdapter()
        binding.mainNewsRv.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
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
                        Log.e(TAG, "An error occured: $it")
                    }
                }

                is Resource.Loading -> showProgressBar()
            }
        }
    }

    private fun initSearchView() {
        var job: Job? = null
        binding.searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean{
                job?.cancel()
                job = MainScope().launch {
                    delay(Constants.searchTimeDelay)
                    query?.let {
                        if(query.isNotEmpty()){
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
                        if(newText.isNotEmpty()){
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

    private fun initDetailClick(){
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_navigation_main_to_navigation_detail,
                bundle
            )
        }
    }


    private fun hideProgressBar() {
        binding.progress.gone()
    }

    private fun showProgressBar() {
        binding.progress.visible()
    }

}