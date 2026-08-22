package com.zuno.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val bgColor = Color.rgb(12, 14, 18)
    private val cardColor = Color.rgb(24, 27, 33)
    private val white = Color.WHITE
    private val gray = Color.rgb(155, 160, 170)
    private val blue = Color.rgb(80, 150, 255)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showHome()
    }

    private fun showHome() {

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(bgColor)

        // Верхняя панель
        val header = LinearLayout(this)
        header.orientation = LinearLayout.HORIZONTAL
        header.gravity = Gravity.CENTER_VERTICAL
        header.setPadding(24, 30, 24, 20)

        val logo = TextView(this)
        logo.text = "Z"
        logo.textSize = 28f
        logo.setTextColor(blue)
        logo.gravity = Gravity.CENTER

        val title = TextView(this)
        title.text = "Zuno"
        title.textSize = 25f
        title.setTextColor(white)
        title.setPadding(18, 0, 0, 0)

        val search = TextView(this)
        search.text = "⌕"
        search.textSize = 34f
        search.setTextColor(white)
        search.gravity = Gravity.CENTER

        header.addView(
            logo,
            LinearLayout.LayoutParams(55, 55)
        )

        header.addView(
            title,
            LinearLayout.LayoutParams(0, 70, 1f)
        )

        header.addView(
            search,
            LinearLayout.LayoutParams(55, 55)
        )

        root.addView(header)

        // Лента
        val feed = LinearLayout(this)
        feed.orientation = LinearLayout.VERTICAL
        feed.setPadding(20, 10, 20, 10)

        val welcome = TextView(this)
        welcome.text = "Добро пожаловать в Zuno 👋"
        welcome.textSize = 25f
        welcome.setTextColor(white)
        welcome.setPadding(5, 20, 5, 25)

        feed.addView(welcome)

        addPost(
            feed,
            "papacarlo708-ui",
            "Добро пожаловать в Zuno! 🚀",
            "Это первая версия нашей социальной сети."
        )

        addPost(
            feed,
            "Zuno Team",
            "Что нового?",
            "Здесь появятся публикации, фотографии, комментарии и лайки."
        )

        val scroll = android.widget.ScrollView(this)
        scroll.addView(feed)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(0, 0, 1f)
        )

        // Нижнее меню
        val bottom = LinearLayout(this)
        bottom.orientation = LinearLayout.HORIZONTAL
        bottom.gravity = Gravity.CENTER
        bottom.setBackgroundColor(Color.rgb(18, 20, 25))
        bottom.setPadding(5, 10, 5, 10)

        addNavButton(bottom, "⌂", "Главная", true)
        addNavButton(bottom, "⌕", "Поиск", false)
        addNavButton(bottom, "+", "Создать", false)
        addNavButton(bottom, "♡", "Сообщения", false)
        addNavButton(bottom, "●", "Профиль", false)

        root.addView(
            bottom,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                85
            )
        )

        setContentView(root)
    }

    private fun addPost(
        parent: LinearLayout,
        username: String,
        title: String,
        text: String
    ) {

        val card = LinearLayout(this)
        card.orientation = LinearLayout.VERTICAL
        card.setBackgroundColor(cardColor)
        card.setPadding(22, 20, 22, 20)

        val user = TextView(this)
        user.text = "●  $username"
        user.textSize = 17f
        user.setTextColor(white)

        val postTitle = TextView(this)
        postTitle.text = title
        postTitle.textSize = 21f
        postTitle.setTextColor(white)
        postTitle.setPadding(0, 20, 0, 8)

        val postText = TextView(this)
        postText.text = text
        postText.textSize = 16f
        postText.setTextColor(gray)

        val actions = TextView(this)
        actions.text = "♡ 0        💬 0        ↗ Поделиться"
        actions.textSize = 15f
        actions.setTextColor(gray)
        actions.setPadding(0, 25, 0, 0)

        card.addView(user)
        card.addView(postTitle)
        card.addView(postText)
        card.addView(actions)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 18)

        parent.addView(card, params)
    }

    private fun addNavButton(
        parent: LinearLayout,
        icon: String,
        text: String,
        selected: Boolean
    ) {

        val item = LinearLayout(this)
        item.orientation = LinearLayout.VERTICAL
        item.gravity = Gravity.CENTER

        val iconView = TextView(this)
        iconView.text = icon
        iconView.textSize = 26f
        iconView.gravity = Gravity.CENTER
        iconView.setTextColor(if (selected) blue else gray)

        val textView = TextView(this)
        textView.text = text
        textView.textSize = 11f
        textView.gravity = Gravity.CENTER
        textView.setTextColor(if (selected) blue else gray)

        item.addView(
            iconView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                40
            )
        )

        item.addView(
            textView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                25
            )
        )

        item.setOnClickListener {
            when (text) {
                "Главная" -> showHome()

                "Поиск" -> showMessage("Поиск", "Здесь будет поиск пользователей и публикаций 🔍")

                "Создать" -> showMessage("Создать", "Здесь можно будет создать публикацию ➕")

                "Сообщения" -> showMessage("Сообщения", "Здесь будут ваши чаты 💬")

                "Профиль" -> showMessage("Профиль", "Здесь будет ваш профиль 👤")
            }
        }

        parent.addView(
            item,
            LinearLayout.LayoutParams(0, 75, 1f)
        )
    }

    private fun showMessage(title: String, message: String) {

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.gravity = Gravity.CENTER
        root.setBackgroundColor(bgColor)
        root.setPadding(40, 40, 40, 40)

        val titleView = TextView(this)
        titleView.text = title
        titleView.textSize = 30f
        titleView.setTextColor(white)
        titleView.gravity = Gravity.CENTER

        val messageView = TextView(this)
        messageView.text = message
        messageView.textSize = 18f
        messageView.setTextColor(gray)
        messageView.gravity = Gravity.CENTER
        messageView.setPadding(0, 20, 0, 30)

        val back = TextView(this)
        back.text = "← На главную"
        back.textSize = 18f
        back.setTextColor(blue)
        back.gravity = Gravity.CENTER

        back.setOnClickListener {
            showHome()
        }

        root.addView(titleView)
        root.addView(messageView)
        root.addView(back)

        setContentView(root)
    }
}
