package com.codepath.apps.restclienttemplate

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

const val charLimit = 280

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button

    lateinit var client: TwitterClient
    lateinit var tvCharLeft: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        client = TwitterApplication.getRestClient(this)
        tvCharLeft = findViewById<TextView>(R.id.tvCharLeft)

        btnTweet.setOnClickListener {
            val tweetContent = etCompose.text.toString()
            if (tweetContent.isEmpty()) {
                Toast.makeText(this, "Empty tweets not allowed!", Toast.LENGTH_SHORT).show()
            }
            else

                if (tweetContent.length > 280) {
                    Toast.makeText(this, "Tweet is too long! Limit is 280 characters", Toast.LENGTH_SHORT)
                        .show()
                }
                else {
                    client.publishTweet(tweetContent, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                            Log.i(TAG, "Successfully published tweet!")

                            val tweet = Tweet.fromJson(json.jsonObject)

                            val intent = Intent()
                            intent.putExtra("tweet", tweet)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                        override fun onFailure(
                            statusCode: Int,
                            headers: Headers?,
                            response: String?,
                            throwable: Throwable?
                        ) {
                            Log.e(TAG, "Failed to publish Tweet", throwable)
                        }

                    })
                }

        }

        etCompose.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //reset tvCount text color in any case beforehand to prevent problems
                tvCharLeft.setTextColor(Color.GRAY)
            }
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    if(s.length > charLimit) {
                        tvCharLeft.setTextColor(Color.RED)
                    }
                    tvCharLeft.text = "Characters left: ${charLimit - s.length}"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                print("")
            }

        })

    }

    companion object {
        val TAG = "ComposeActivity"
    }
}
