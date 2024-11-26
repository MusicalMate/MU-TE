package com.example.mute.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mute.R
import com.example.mute.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host =
            supportFragmentManager.findFragmentById(R.id.container_main_fragments) as NavHostFragment
        navController = host.navController
        binding.bottomMainNavigation.setupWithNavController(navController)

        setBottomNavVisibility()
    }

    private fun setBottomNavVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomMainNavigation.visibility =
                if (destination.id == R.id.homeFragment || destination.id == R.id.addFileFragment || destination.id == R.id.searchFragment || destination.id == R.id.myPageFragment) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }
}