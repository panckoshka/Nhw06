package ru.netology.nmedia.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostEventListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        // регистрируем котнракт
        val newPostLauncher = registerForActivityResult(NewPostActivityContract(),
            fun(text: String?) {
                text ?: return
                // Создаем пост
                viewModel.editContent(text)
                viewModel.save()
            })

        val editPostLauncher = registerForActivityResult(
            EditPostActivityContract(),
            fun(data: PostActivityData?) {
                data ?: return

                println("Получили от PostActivity ИЗМЕННЫЙ текст поста: " + data.content)
                println("Получили от PostActivity ID поста, который изменяем: " + data.id)

                viewModel.edit(data.id, data.content)
                viewModel.save()
            }
        )

//  проверяем естли нужные нам приложения на устройстве
//         вставить в пост видео
//                Intent (Intent.ACTION_VIEW)
//            .putExtra(Intent.EXTRA_QUICK_VIEW_FEATURES, "text")  // TODO переделать для видео
//            .setType("text/plain")
//
//            if (intent.resolveActivity(packageManager) == null) {
////            listOf("ru.netology.nmedia").find { it == "search" } // проверить по спику если данные
//                Toast.makeText(this, "Install YouTube", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                startActivity(intent)
//            }


//        val repository = PostRepositoryInMemoryImpl()
//        val adapter = PostsAdapter(onShareListener = fun(post: Post) {
//            viewModel.shareById(post.id)
//        }, onLikeListener = fun(it: Post) {
//            viewModel.likeById(it.id)
//        })

//        val adapter = PostsAdapter(onShareListener = { post ->
////            println("vvv")
//            viewModel.shareById(post.id)
//        }, onLikeListener = fun(post: Post) {
//            viewModel.likeById(post.id)
//        }, onRemoveListener = fun(post: Post) {
//            viewModel.removeById(post.id)
//        }
//        )

        val adapter = PostsAdapter(
            object : PostEventListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    println("Нажали на 'Редактировать' в меню")

                    editPostLauncher.launch(PostActivityData(post.id, post.content))
                }
            }
        )

//        viewModel.edited.observe(this){ edited ->
//            if(edited.id == 0L){ // сбрасываемна ркдактирование начальное состояние
//                return@observe
//            }
//            binding.content.setText(edited.content)
//            binding.undoChangesContent.setText(edited.content)
//            binding.groupUndoChanges.visibility = View.VISIBLE
//            binding.content.requestFocus() // фокус на измения поста
//        }

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.create.setOnClickListener {
            newPostLauncher.launch()
        }

//        binding.

//        binding.groupUndoChanges.visibility = View.GONE

//        binding.undoChanges.setOnClickListener {
//          viewModel.undoChanges()
//
//            binding.groupUndoChanges.visibility = View.GONE
//
//            binding.content.clearFocus()
//            AndroidUtils.hideKeyboard(binding.content) // спрятаня клавиатура
//            binding.content.setText("") // текст по умолчанию
//        }

//        binding.save.setOnClickListener {
//            if (binding.content.text.isNullOrBlank()) { // проверяем пустой ли текст
//                Toast.makeText(it.context, getString(R.string.empty_post_error), Toast.LENGTH_SHORT)
//                    .show() // проверка на ошибку
//                return@setOnClickListener
//            }
//            val text = binding.content.text.toString() // если текст существует
//
//
//            viewModel.editContent(text)
//            viewModel.save()
//
//            binding.groupUndoChanges.visibility = View.GONE
//
//            binding.content.clearFocus()
//            AndroidUtils.hideKeyboard(binding.content) // спрятаня клавиатура
//            binding.content.setText("") // текст по умолчанию
//        }
    }
}
