package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // указываем лайаут
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Получаем из intent текст поста
        val postContent = intent.getStringExtra("POST_CONTENT")
        val postId = intent.getLongExtra("POST_ID", 0L)

        println("PostAcitivity получила текст поста: " + postContent)

        // устаналиваем текст поста в TextEdit
        binding.content.setText(postContent)

        binding.save.setOnClickListener{
            val editedPostConent = binding.content.text.toString()
            println("Отредактированный контект поста: " + editedPostConent)

            val result = Intent().putExtra("POST_CONTENT", editedPostConent)
            result.putExtra("POST_ID", postId)

            setResult(RESULT_OK, result)
            finish()

//            if (binding.content.text.isNullOrBlank()) { // проверяем пустой ли текст
//                Toast.makeText(this,
//                    getString(R.string.empty_post_error), Toast.LENGTH_SHORT)
//                    .show() // проверка на ошибку
//                setResult(RESULT_CANCELED) // пустой контент
//            } else{
//                val result = Intent().putExtra(Intent.EXTRA_TEXT, binding.content.text.toString())
//                setResult(RESULT_OK,result)
//            }
//            finish()
        }


        //кнопка отправки сообщения в другое приложение
//        binding.send.setOnClickListener {
//            val intent = Intent()
//                .putExtra(Intent.EXTRA_TEXT, "Test text")
//                .setAction(Intent.ACTION_SEND)
//                .setType("text/plain")
//            val createChooser = Intent.createChooser(intent, "Chooser app")
//            startActivity(createChooser)
//        }
//
//        //принимаем текст
//        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        } ?: run {
//            // если текста нет
//            //умеет двигать кнопки относительно себя
//            Snackbar.make(
//                binding.root,
//                getString(R.string.empty_content_error),
//                Snackbar.LENGTH_SHORT
//            )
////                    добавляем экшен типа кнопки с права, сработает клик лиссеенер
//                .setAction(android.R.string.ok) {
//                    finish()
//                }
//                .show()
//
//        }
    }
}