package kg.kunduznbkva.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kg.kunduznbkva.newsapp.data.NewsRepository
import kg.kunduznbkva.newsapp.data.local.NewsDatabase
import kg.kunduznbkva.newsapp.databinding.ActivityMainBinding
import kg.kunduznbkva.newsapp.ui.NewsViewModel
import kg.kunduznbkva.newsapp.ui.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        val repository= NewsDatabase.getInstance(this)?.let { NewsRepository() }
        val viewModelProviderFactory = repository?.let { NewsViewModelProviderFactory(it) }
        viewModel = ViewModelProvider(this,viewModelProviderFactory!!)[NewsViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }
}