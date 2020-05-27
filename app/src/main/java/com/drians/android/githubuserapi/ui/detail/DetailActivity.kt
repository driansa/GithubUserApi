package com.drians.android.githubuserapi.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drians.android.githubuserapi.R
import com.drians.android.githubuserapi.adapter.DetailAdapter
import com.drians.android.githubuserapi.adapter.TabsPagerAdapter
import com.drians.android.githubuserapi.model.UserItems
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_tabs.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var adapter: DetailAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setTitle(R.string.detail_label)
        supportActionBar?.elevation = 0F

        showTabs()

        showDetailUser()

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        val username = intent?.getParcelableExtra(EXTRA_USER) as UserItems

        detailViewModel.setDetailUser(username.login.toString())

        detailViewModel.getDetailUser().observe(this, Observer { detailUserItems ->
            if (detailUserItems != null) {
                adapter.setData(detailUserItems)
            }
        })
    }

    private fun showDetailUser() {
        adapter = DetailAdapter()
        adapter.notifyDataSetChanged()

        recyclerviewDetailUser.layoutManager = LinearLayoutManager(this)
        recyclerviewDetailUser.adapter = adapter
    }

    private fun showTabs() {
        val sectionsPagerAdapter = TabsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

}