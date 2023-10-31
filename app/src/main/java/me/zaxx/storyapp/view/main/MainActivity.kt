package me.zaxx.storyapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import me.zaxx.storyapp.R
import me.zaxx.storyapp.adapter.StoryListAdapter
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.databinding.ActivityMainBinding
import me.zaxx.storyapp.view.Upload.UploadActivity
import me.zaxx.storyapp.view.ViewModelFactory
import me.zaxx.storyapp.view.login.LoginActivity

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
            mainViewModel.getStory(user.token)
            if (!user.isLogin){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.fab.setOnClickListener {
            val intentSendFile = Intent(this, UploadActivity::class.java)
            startActivity(intentSendFile)
        }

        mainViewModel.listItem.observe(this){
            getDataList(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnLogout -> mainViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecycleView(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager =layoutManager
        val itemDecoration =DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)
    }

    private fun getDataList(list: List<ListStoryItem>){
        val adapter = StoryListAdapter()
        adapter.submitList(list)
        binding.rvList.adapter =adapter
    }
}