package com.sarrawi.myimgflowk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sarrawi.myimgflowk.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val retrofitService = ApiService.provideRetrofitInstance()
    private val retrofitService = RetrofitFactory.makeRetrofitService()
    private val mainRepository by lazy { ImgRepository(retrofitService, this.application) }
    private val imgsViewModel: Imgs_ViewModel by viewModels {
        ViewModelFactory(this, mainRepository)
    }
    private val imgAdapter by lazy { ImgAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
       setUpRv()

    }

    fun setUpRv() {

        lifecycleScope.launch {
            imgsViewModel.response.collect { imgs ->
                // الكود الحالي هنا
                imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
                imgAdapter.img_list = imgs

                if (binding.rvImgCont.adapter == null) {
                    binding.rvImgCont.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.rvImgCont.adapter = imgAdapter
                } else {
                    imgAdapter.notifyDataSetChanged()
                }
            }
        }

//        imgsViewModel.getAllImgsViewModel().observe(this) { imgs ->
//            // الكود الحالي هنا
//            imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//            imgAdapter.img_list = imgs
//
//            if (binding.rvImgCont.adapter == null) {
//                binding.rvImgCont.layoutManager = GridLayoutManager(this, 2)
//                binding.rvImgCont.adapter = imgAdapter
//            } else {
//                imgAdapter.notifyDataSetChanged()
//            }
//        }

    }




}

