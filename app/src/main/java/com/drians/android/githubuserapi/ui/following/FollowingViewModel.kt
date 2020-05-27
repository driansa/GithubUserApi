package com.drians.android.githubuserapi.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drians.android.githubuserapi.BuildConfig
import com.drians.android.githubuserapi.model.FollowingItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray


class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<FollowingItems>>()

    fun setFollowing(username: String) {
        val listItems = ArrayList<FollowingItems>()

        val url = BuildConfig.BASE_URL+"users/$username/following"

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
                        val following = responseArray.getJSONObject(i)
                        val followingItems = FollowingItems()
                        followingItems.apply {
                            id = following.getInt("id")
                            login = following.getString("login")
                            avatar_url = following.getString("avatar_url")
                            type = following.getString("type")
                        }
                        listItems.add(followingItems)
                    }

                    listFollowing.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<FollowingItems>> {
        return listFollowing
    }
}