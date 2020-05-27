package com.drians.android.githubuserapi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drians.android.githubuserapi.BuildConfig
import com.drians.android.githubuserapi.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<UserItems>>()

    fun setUser(username: String) {
        val listItems = ArrayList<UserItems>()

        val url = BuildConfig.BASE_URL+"search/users?q=$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token "+BuildConfig.TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userItems = UserItems()
                        with(userItems) {
                            id = user.getInt("id")
                            login = user.getString("login")
                            avatar_url = user.getString("avatar_url")
                            type = user.getString("type")
                        }
                        listItems.add(userItems)
                    }

                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserItems>> {
        return listUsers
    }
}