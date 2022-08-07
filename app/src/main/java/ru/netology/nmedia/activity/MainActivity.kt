package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.viewmodel.PostViewModel

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

        val adapter = PostsAdapter(onShareListener = { post ->
//            println("vvv")
            viewModel.shareById(post.id)
        }, onLikeListener = fun(post: Post) {
            viewModel.likeById(post.id)
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}
