package com.zuno.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var feed: LinearLayout

    private val posts = mutableListOf(
        "Добро пожаловать в Zuno! 🚀",
        "Это первая публикация нашей социальной сети."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun showHome() {
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(Color.rgb(15, 17, 20))

        val header = TextView(this)
        header.text = "Zuno"
        header.textSize = 28f
        header.setTextColor(Color.WHITE)
        header.gravity = Gravity.CENTER
        header.setPadding(20, 30, 20, 25)

        root.addView(header)

        val scroll = ScrollView(this)

        feed = LinearLayout(this)
        feed.orientation = LinearLayout.VERTICAL
        feed.setPadding(20, 10, 20, 20)

        scroll.addView(feed)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        val bottom = LinearLayout(this)
        bottom.orientation = LinearLayout.HORIZONTAL
        bottom.setBackgroundColor(Color.rgb(25, 28, 34))

        addNavigationButton(bottom, "Главная", true)
        addNavigationButton(bottom, "Поиск", false)
        addNavigationButton(bottom, "+", false)
        addNavigationButton(bottom, "Сообщения", false)
        addNavigationButton(bottom, "Профиль", false)

        root.addView(
            bottom,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                80
            )
        )

        setContentView(root)

        refreshFeed()
    }

    private fun refreshFeed() {
        feed.removeAllViews()

        for (post in posts.asReversed()) {
            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setBackgroundColor(Color.rgb(28, 32, 38))
            card.setPadding(20, 20, 20, 20)

            val username = TextView(this)
            username.text = "●  Пользователь Zuno"
            username.textSize = 16f
            username.setTextColor(Color.WHITE)

            val text = TextView(this)
            text.text = post
            text.textSize = 18f
            text.setTextColor(Color.WHITE)
            text.setPadding(0, 20, 0, 20)

            val actions = TextView(this)
            actions.text = "♡ 0        💬 0        ↗ Поделиться"
            actions.textSize = 15f
            actions.setTextColor(Color.LTGRAY)

            card.addView(username)
            card.addView(text)
            card.addView(actions)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, 18)

            feed.addView(card, params)
        }
    }

    private fun addNavigationButton(
        parent: LinearLayout,
        title: String,
        selected: Boolean
    ) {
        val button = Button(this)
        button.text = title
        button.textSize = 11f

        if (selected) {
            button.setTextColor(Color.rgb(80, 150, 255))
        } else {
            button.setTextColor(Color.WHITE)
        }

        button.setOnClickListener {
            when (title) {
                "+" -> showCreatePost()

                "Поиск" -> showInfo(
                    "Поиск",
                    "Поиск пользователей и публикаций появится здесь."
                )

                "Сообщения" -> showInfo(
                    "Сообщения",
                    "Личные сообщения появятся здесь."
                )

                "Профиль" -> showInfo(
                    "Профиль",
                    "Профиль пользователя появится здесь."
                )

                "Главная" -> showHome()
            }
        }

        parent.addView(
            button,
            LinearLayout.LayoutParams(0, 80, 1f)
        )
    }

    private fun showCreatePost() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(35, 10, 35, 10)

        val input = EditText(this)
        input.hint = "Что нового?"
        input.minLines = 5
        input.gravity = Gravity.TOP

        layout.addView(
            input,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                250
            )
        )

        AlertDialog.Builder(this)
            .setTitle("Создать публикацию")
            .setView(layout)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Опубликовать") { _, _ ->

                val text = input.text.toString().trim()

                if (text.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Напиши текст публикации",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    posts.add(text)

                    Toast.makeText(
                        this,
                        "Публикация создана 🚀",
                        Toast.LENGTH_SHORT
                    ).show()

                    showHome()
                }
            }
            .show()
    }

    private fun showInfo(
        title: String,
        message: String
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
