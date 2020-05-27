package com.drians.android.githubuserapi.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drians.android.githubuserapi.BuildConfig
import com.drians.android.githubuserapi.model.DetailUserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    private val listDetailUser = MutableLiveData<ArrayList<DetailUserItems>>()

    fun setDetailUser(username: String) {
        val listItems = ArrayList<DetailUserItems>()

        val url = BuildConfig.BASE_URL+"users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token "+BuildConfig.TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val detailUserItems = DetailUserItems()
                    detailUserItems.apply {
                        id = responseObject.getInt("id")
                        login = responseObject.getString("login")
                        avatar_url = responseObject.getString("avatar_url")
                        name = responseObject.getString("name")
                        company = responseObject.getString("company")
                        location = responseObject.getString("location")
                        public_repos = responseObject.getString("public_repos")
                        followers = responseObject.getString("followers")
                        following = responseObject.getString("following")
                    }
                    listItems.add(detailUserItems)

                    listDetailUser.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getDetailUser(): LiveData<ArrayList<DetailUserItems>> {
        return listDetailUser
    }
}