package com.zuno.app

import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val SUPABASE_URL =
    "https://exbhmjhttfmgycdhrmpl.supabase.co"

private const val SUPABASE_PUBLISHABLE_KEY =
    "ВСТАВЬ_СЮДА_СВОЙ_PUBLISHABLE_KEY"

private val supabase = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_PUBLISHABLE_KEY
) {
    install(Auth)
}

class MainActivity : AppCompatActivity() {

    private lateinit var root: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showStartScreen()
    }

    private fun showStartScreen() {

        root = createRoot()

        val title = createTitle("Zuno")

        val subtitle = createText(
            "Добро пожаловать в Zuno"
        )

        val loginButton = createButton("Войти")
        val registerButton = createButton("Регистрация")

        loginButton.setOnClickListener {
            showLoginScreen()
        }

        registerButton.setOnClickListener {
            showRegisterScreen()
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(loginButton)
        root.addView(registerButton)

        setContentView(root)
    }

    private fun showLoginScreen() {

        root = createRoot()

        val title = createTitle("Вход")

        val emailInput = createInput(
            "Email",
            false
        )

        val passwordInput = createInput(
            "Пароль",
            true
        )

        val loginButton = createButton("Войти")
        val backButton = createButton("Назад")

        loginButton.setOnClickListener {

            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Заполни email и пароль")
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        backButton.setOnClickListener {
            showStartScreen()
        }

        root.addView(title)
        root.addView(emailInput)
        root.addView(passwordInput)
        root.addView(loginButton)
        root.addView(backButton)

        setContentView(root)
    }

    private fun showRegisterScreen() {

        root = createRoot()

        val title = createTitle("Регистрация")

        val emailInput = createInput(
            "Email",
            false
        )

        val passwordInput = createInput(
            "Пароль",
            true
        )

        val registerButton = createButton("Создать аккаунт")
        val backButton = createButton("Назад")

        registerButton.setOnClickListener {

            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Заполни email и пароль")
                return@setOnClickListener
            }

            if (password.length < 6) {
                toast("Пароль должен содержать минимум 6 символов")
                return@setOnClickListener
            }

            registerUser(email, password)
        }

        backButton.setOnClickListener {
            showStartScreen()
        }

        root.addView(title)
        root.addView(emailInput)
        root.addView(passwordInput)
        root.addView(registerButton)
        root.addView(backButton)

        setContentView(root)
    }

    private fun loginUser(
        email: String,
        password: String
    ) {

        toast("Выполняется вход...")

        CoroutineScope(Dispatchers.Main).launch {

            try {

                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                toast("Вход выполнен")

                showHomeScreen()

            } catch (e: Exception) {

                toast(
                    "Ошибка входа: ${e.message ?: "неизвестная ошибка"}"
                )
            }
        }
    }

    private fun registerUser(
        email: String,
        password: String
    ) {

        toast("Создаём аккаунт...")

        CoroutineScope(Dispatchers.Main).launch {

            try {

                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }

                toast(
                    "Аккаунт создан. Проверь email для подтверждения."
                )

                showStartScreen()

            } catch (e: Exception) {

                toast(
                    "Ошибка регистрации: ${e.message ?: "неизвестная ошибка"}"
                )
            }
        }
    }

    private fun showHomeScreen() {

        root = createRoot()

        val title = createTitle("Zuno")

        val welcome = createText(
            "Вы вошли в аккаунт"
        )

        val userEmail = supabase.auth.currentUserOrNull()?.email

        val account = createText(
            userEmail ?: "Пользователь"
        )

        val postsButton = createButton(
            "Публикации"
        )

        val profileButton = createButton(
            "Профиль"
        )

        val logoutButton = createButton(
            "Выйти"
        )

        postsButton.setOnClickListener {
            toast("Раздел публикаций скоро будет подключён")
        }

        profileButton.setOnClickListener {
            showProfileScreen()
        }

        logoutButton.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {

                try {

                    supabase.auth.signOut()

                    toast("Вы вышли из аккаунта")

                    showStartScreen()

                } catch (e: Exception) {

                    toast(
                        "Ошибка выхода: ${e.message ?: "неизвестная ошибка"}"
                    )
                }
            }
        }

        root.addView(title)
        root.addView(welcome)
        root.addView(account)
        root.addView(postsButton)
        root.addView(profileButton)
        root.addView(logoutButton)

        setContentView(root)
    }

    private fun showProfileScreen() {

        root = createRoot()

        val title = createTitle("Профиль")

        val user = supabase.auth.currentUserOrNull()

        val email = createText(
            "Email: ${user?.email ?: "не указан"}"
        )

        val backButton = createButton("Назад")

        backButton.setOnClickListener {
            showHomeScreen()
        }

        root.addView(title)
        root.addView(email)
        root.addView(backButton)

        setContentView(root)
    }

    private fun createRoot(): LinearLayout {

        val layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL

        layout.gravity = Gravity.CENTER_HORIZONTAL

        layout.setPadding(
            40,
            50,
            40,
            40
        )

        layout.setBackgroundColor(
            Color.rgb(15, 17, 20)
        )

        return layout
    }

    private fun createTitle(
        text: String
    ): TextView {

        val view = TextView(this)

        view.text = text
        view.textSize = 32f
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        view.setPadding(0, 0, 0, 30)

        view.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        return view
    }

    private fun createText(
        text: String
    ): TextView {

        val view = TextView(this)

        view.text = text
        view.textSize = 18f
        view.setTextColor(Color.LTGRAY)
        view.gravity = Gravity.CENTER
        view.setPadding(0, 10, 0, 25)

        view.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        return view
    }

    private fun createInput(
        hint: String,
        password: Boolean
    ): EditText {

        val input = EditText(this)

        input.hint = hint
        input.setTextColor(Color.WHITE)
        input.setHintTextColor(Color.GRAY)
        input.textSize = 17f

        input.setPadding(
            25,
            15,
            25,
            15
        )

        if (password) {
            input.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            input.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        val params =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        params.setMargins(
            0,
            10,
            0,
            10
        )

        input.layoutParams = params

        return input
    }

    private fun createButton(
        text: String
    ): Button {

        val button = Button(this)

        button.text = text
        button.textSize = 16f

        val params =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        params.setMargins(
            0,
            12,
            0,
            12
        )

        button.layoutParams = params

        return button
    }

    private fun toast(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}
