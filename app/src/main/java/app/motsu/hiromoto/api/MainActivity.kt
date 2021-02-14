package app.motsu.hiromoto.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.api.load
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        
        val userService: UserService = retrofit.create(UserService::class.java)

        requestButton.setOnClickListener {
            runBlocking {
                kotlin.runCatching {
                    userService.getUser("Motsu0417")
                }
            }.onSuccess {
                avatarImageView.load(it.avatarUrl)
                nameTextView.text = it.name
                userIdTextView.text = it.userId
                followingTextView.text = "Following ${it.following}"
                followerTextView.text = "Followers ${it.followers}"
            }.onFailure {
                Toast.makeText(this,"取得に失敗しました",Toast.LENGTH_SHORT).show()
            }
        }
    }
}