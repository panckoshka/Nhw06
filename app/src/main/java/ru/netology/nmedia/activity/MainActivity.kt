package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostEventListener as PostEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = PostRepositoryInMemoryImpl()
        val viewModel: PostViewModel = PostViewModel(repository)

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
                    viewModel.edit(post)
                }
            }
        )

        viewModel.edited.observe(this){ edited ->
            if(edited.id == 0L){ // сбрасываемна ркдактирование начальное состояние
                return@observe
            }
            binding.content.setText(edited.content)
            binding.undoChangesContent.setText(edited.content)
            binding.groupUndoChanges.visibility = View.VISIBLE
            binding.content.requestFocus() // фокус на измения поста
        }

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.groupUndoChanges.visibility = View.GONE

        binding.undoChanges.setOnClickListener {
          viewModel.undoChanges()

            binding.groupUndoChanges.visibility = View.GONE

            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content) // спрятаня клавиатура
            binding.content.setText("") // текст по умолчанию
        }

        binding.save.setOnClickListener {
            if (binding.content.text.isNullOrBlank()) { // проверяем пустой ли текст
                Toast.makeText(it.context, getString(R.string.empty_post_error), Toast.LENGTH_SHORT)
                    .show() // проверка на ошибку
                return@setOnClickListener
            }
            val text = binding.content.text.toString() // если текст существует


            viewModel.editContent(text)
            viewModel.save()

            binding.groupUndoChanges.visibility = View.GONE

            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content) // спрятаня клавиатура
            binding.content.setText("") // текст по умолчанию
        }
    }
}
