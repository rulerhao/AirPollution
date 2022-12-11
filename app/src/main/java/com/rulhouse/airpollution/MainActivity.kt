package com.rulhouse.airpollution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rulhouse.airpollution.databinding.ActivityMainBinding
import com.rulhouse.airpollution.view.MiddleCardAdapter
import com.rulhouse.airpollution.view.TopHorizonCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
            topHorizonCardAdapter = TopHorizonCardAdapter(listOf())
            middleCardAdapter = MiddleCardAdapter(listOf(), this@MainActivity.viewModel)
        }
        setContentView(binding.root)
    }
}