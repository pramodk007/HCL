package com.example.heartcarelite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ListItemBinding
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo

class UserAdapter(private val clickListener:(User)->Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val userList = ArrayList<User>()

    fun setList(user: List<User>){
        userList.clear()
        userList.addAll(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }

    override fun getItemCount() = userList.size


}

class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(user: User, clickListener:(User)->Unit){
        binding.tvLastName.text = user.userLastName
        binding.tvFirstName.text = user.userFirstName
        binding.tvScore.text = user.cvdScore
        binding.tvAge.text = user.userAge
        binding.tvSex.text = user.userSex
        binding.tvPatientId.text = user.userPatientId
        binding.parentView.setOnClickListener {
            clickListener(user)
        }
    }
}