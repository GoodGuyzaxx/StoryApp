package me.zaxx.storyapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import me.zaxx.storyapp.R
import me.zaxx.storyapp.view.adapter.StoryListAdapter
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import me.zaxx.storyapp.databinding.ActivityMainBinding
import me.zaxx.storyapp.view.upload.UploadActivity
import me.zaxx.storyapp.view.ViewModelFactory
import me.zaxx.storyapp.view.adapter.LoadingStateAdapter
import me.zaxx.storyapp.view.login.LoginActivity
import me.zaxx.storyapp.view.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecycleView()

        mainViewModel.getSession().observe(this){user ->
            val getToken = user.token
            if (user.isLogin){
                getDataList(getToken)
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }

        binding.fab.setOnClickListener {
            val intentSendFile = Intent(this, UploadActivity::class.java)
            startActivity(intentSendFile)
        }

//        mainViewModel.listItem.observe(this){
//            getDataList(lifecycle,it)
//        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnLogout -> mainViewModel.logout()
            R.id.btnMaps -> startActivity(Intent(this,MapsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecycleView(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager =layoutManager
        val itemDecoration =DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)
    }

    private fun getDataList(token: String){
        val adapter = StoryListAdapter()
        binding.rvList.adapter =adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        mainViewModel.getStory(token).observe(this,{
            adapter.submitData(lifecycle,it)
        })
    }
}