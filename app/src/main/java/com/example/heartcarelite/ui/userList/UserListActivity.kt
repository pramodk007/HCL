package com.example.heartcarelite.ui.userList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.heartcarelite.R
import com.example.heartcarelite.adapter.UserAdapter
import com.example.heartcarelite.databinding.ActivityCvdRiskBinding
import com.example.heartcarelite.databinding.ActivityUserListBinding
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.ui.cvdRisk.CvdRiskActivity
import com.example.heartcarelite.ui.userDetails.UserDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private lateinit var adapter: UserAdapter
    private val userListViewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Saved records"

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        initRecyclerView()
    }

    private fun displaySubscribersList(){
        userListViewModel.userList.observe(this, Observer {
            Log.i("MyTag", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

    }

    private fun initRecyclerView(){
        binding.rvList.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter({selectedItem:User->listItemClicked(selectedItem)})
        binding.rvList.adapter = adapter
        displaySubscribersList()
    }

    private fun listItemClicked(user: User){
        val intent = Intent(applicationContext, UserDetailsActivity::class.java)
        intent.putExtra("userDetails", user)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

}