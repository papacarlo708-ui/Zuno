package com.zuno.app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var productContainer: LinearLayout

    private val products = mutableListOf(
        Product("Premium аккаунт", "Доступ на 30 дней", 499),
        Product("Игровой ключ", "Цифровой товар", 299),
        Product("VPN на 30 дней", "Быстрый VPN", 199)
    )

    data class Product(
        val name: String,
        val description: String,
        val price: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createInterface()
    }

    private fun createInterface() {

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(Color.rgb(15, 17, 20))

        // Заголовок
        val header = TextView(this)
        header.text = "ZUNO"
        header.textSize = 28f
        header.setTextColor(Color.WHITE)
        header.gravity = Gravity.CENTER
        header.setPadding(20, 35, 20, 35)

        root.addView(
            header,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        // Кнопка добавления товара
        val addButton = Button(this)
        addButton.text = "+ Добавить товар"
        addButton.setOnClickListener {
            showAddProductDialog()
        }

        root.addView(
            addButton,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(20, 5, 20, 10)
            }
        )

        // Заголовок каталога
        val catalogTitle = TextView(this)
        catalogTitle.text = "Каталог товаров"
        catalogTitle.textSize = 22f
        catalogTitle.setTextColor(Color.WHITE)
        catalogTitle.setPadding(20, 15, 20, 15)

        root.addView(catalogTitle)

        // Прокручиваемый каталог
        val scrollView = ScrollView(this)

        productContainer = LinearLayout(this)
        productContainer.orientation = LinearLayout.VERTICAL
        productContainer.setPadding(15, 5, 15, 20)

        scrollView.addView(productContainer)

        root.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        // Нижняя панель
        val bottomBar = LinearLayout(this)
        bottomBar.orientation = LinearLayout.HORIZONTAL
        bottomBar.gravity = Gravity.CENTER
        bottomBar.setPadding(5, 10, 5, 10)

        val catalogButton = Button(this)
        catalogButton.text = "Каталог"

        val purchasesButton = Button(this)
        purchasesButton.text = "Мои покупки"

        bottomBar.addView(
            catalogButton,
            LinearLayout.LayoutParams(0, 60, 1f)
        )

        bottomBar.addView(
            purchasesButton,
            LinearLayout.LayoutParams(0, 60, 1f)
        )

        root.addView(bottomBar)

        setContentView(root)

        refreshProducts()
    }

    private fun refreshProducts() {

        productContainer.removeAllViews()

        if (products.isEmpty()) {

            val emptyText = TextView(this)
            emptyText.text = "Товаров пока нет"
            emptyText.textSize = 18f
            emptyText.setTextColor(Color.LTGRAY)
            emptyText.gravity = Gravity.CENTER
            emptyText.setPadding(20, 50, 20, 50)

            productContainer.addView(emptyText)

            return
        }

        products.forEachIndexed { index, product ->

            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setPadding(25, 20, 25, 20)
            card.setBackgroundColor(Color.rgb(30, 34, 40))

            val name = TextView(this)
            name.text = product.name
            name.textSize = 20f
            name.setTextColor(Color.WHITE)

            val description = TextView(this)
            description.text = product.description
            description.textSize = 15f
            description.setTextColor(Color.LTGRAY)
            description.setPadding(0, 8, 0, 8)

            val price = TextView(this)
            price.text = "${product.price} ₽"
            price.textSize = 19f
            price.setTextColor(Color.rgb(80, 170, 255))

            val buyButton = Button(this)
            buyButton.text = "Купить"

            buyButton.setOnClickListener {
                Toast.makeText(
                    this,
                    "Покупка: ${product.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val deleteButton = Button(this)
            deleteButton.text = "Удалить товар"

            deleteButton.setOnClickListener {
                showDeleteDialog(index)
            }

            card.addView(name)
            card.addView(description)
            card.addView(price)
            card.addView(buyButton)
            card.addView(deleteButton)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, 20)

            productContainer.addView(card, params)
        }
    }

    private fun showAddProductDialog() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 10, 40, 10)

        val nameInput = EditText(this)
        nameInput.hint = "Название товара"

        val descriptionInput = EditText(this)
        descriptionInput.hint = "Описание товара"

        val priceInput = EditText(this)
        priceInput.hint = "Цена"
        priceInput.inputType = 2

        layout.addView(nameInput)
        layout.addView(descriptionInput)
        layout.addView(priceInput)

        AlertDialog.Builder(this)
            .setTitle("Добавить товар")
            .setView(layout)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Добавить") { _, _ ->

                val name = nameInput.text.toString().trim()
                val description = descriptionInput.text.toString().trim()
                val price = priceInput.text.toString().toIntOrNull()

                if (name.isEmpty() || description.isEmpty() || price == null) {

                    Toast.makeText(
                        this,
                        "Заполни все поля",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setPositiveButton
                }

                products.add(
                    Product(
                        name,
                        description,
                        price
                    )
                )

                refreshProducts()

                Toast.makeText(
                    this,
                    "Товар добавлен",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun showDeleteDialog(index: Int) {

        AlertDialog.Builder(this)
            .setTitle("Удалить товар?")
            .setMessage(products[index].name)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Удалить") { _, _ ->

                products.removeAt(index)
                refreshProducts()

                Toast.makeText(
                    this,
                    "Товар удалён",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }
}
Шаг 7
После вставки:
Нажми Save changes / Сохранить изменения.
В поле сообщения коммита напиши: Создан каталог Zuno.
Выбери Commit changes.
Подожди, пока GitHub сохранит файл.
Напиши мне «сохранил».
Потом перейдём к сборке APK, чтобы установить Zuno на телефон.
