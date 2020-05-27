package com.drians.android.githubuserapi.ui.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drians.android.githubuserapi.BuildConfig
import com.drians.android.githubuserapi.model.FollowerItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<FollowerItems>>()

    fun setFollowers(username: String) {
        val listItems = ArrayList<FollowerItems>()

        val url = BuildConfig.BASE_URL+"users/$username/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token "+BuildConfig.TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val follower = responseArray.getJSONObject(i)
                        val followerItems = FollowerItems()
                        followerItems.apply {
                            id = follower.getInt("id")
                            login = follower.getString("login")
                            avatar_url = follower.getString("avatar_url")
                            type = follower.getString("type")
                        }
                        listItems.add(followerItems)
                    }

                    listFollowers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<FollowerItems>> {
        return listFollowers
    }
}