package com.example.cheat


import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cheat.CameraActivity
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    private val viewModel: MessageViewModel by viewModels()

    var debug = true;   // just for debugging

    lateinit var text_entry: EditText;

    lateinit var button_send: ImageButton;

    lateinit var layout: LinearLayout;

    lateinit var history: ScrollView;

    fun requestCamera(view: View) {
        if(debug) println("requestCamera");

        val i = Intent(getApplicationContext(), CameraActivity::class.java)
        startActivity(i)
    }

    fun openImages(view: View) {
        if(debug) println("openImages");
        val intent = Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    fun loadHistory() {
        if(debug) {
            println("sendMessage");
            var i = 0;
            while(i < 15) {
                val textView = TextView(this);
                textView.text = "Message$i";
                i++;

                textView.setTextSize(25f);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.text_view_received);

                textView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.LEFT
                    bottomMargin = 10;
                    topMargin = 10;
                }

                layout?.addView(textView);
            }
            history.post { history.fullScroll(View.FOCUS_DOWN) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun sendMessage(view: View) {
//         Do something in response to button click
        if(debug) println("sendMessage");

        if(!text_entry.text.isBlank()) { // not sending empty text
            val textView = TextView(this);
            textView.text = text_entry.text;
            text_entry.text = null;

            textView.setTextSize(25f);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.text_view_sent);

            textView.layoutParams= LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.RIGHT
                bottomMargin = 10;
                topMargin = 10;
                rightMargin = 15;
            }

            layout?.addView(textView);
            history.post { history.fullScroll(View.FOCUS_DOWN) }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        btnDebug.setOnClickListener {
            startActivity(Intent(this,StartActivity::class.java))
        }

        if(debug) println("onCreate");

        history = findViewById<ScrollView>(R.id.scrollView);
        layout = findViewById(R.id.history_layout);
        text_entry = findViewById(R.id.text_entry);
        button_send = findViewById(R.id.button_send);
        loadHistory();
    }
}
