package kg.kunduznbkva.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kg.kunduznbkva.newsapp.MainActivity
import kg.kunduznbkva.newsapp.databinding.FragmentMainBinding
import kg.kunduznbkva.newsapp.ui.NewsViewModel
import kg.kunduznbkva.newsapp.utils.Resource
import kg.kunduznbkva.newsapp.utils.adapters.NewsAdapter
import kg.kunduznbkva.newsapp.utils.gone
import kg.kunduznbkva.newsapp.utils.visible

class MainFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: NewsAdapter
    private val TAG = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecycler()
        setupObserver()
    }

    private fun initRecycler() {
        adapter = NewsAdapter()
        binding.mainNewsRv.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.breakingNews.observe( viewLifecycleOwner) { response ->
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

    private fun hideProgressBar() {
        binding.progress.gone()
    }

    private fun showProgressBar() {
        binding.progress.visible( )
    }

}