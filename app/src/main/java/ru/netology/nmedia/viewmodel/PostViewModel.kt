package ru.netology.nmedia.viewmodel

import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

val empty = Post(
    0,
    "",
    "",
    "",
    false
)

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)


    fun save() {
        edited.value?.let {
            repository.save(it)
            edited.value = empty //вернуться на пустой пост
        }

    }

    fun undoChanges() {
        edited.value = empty
    }

    fun edit(post: Post) { //редактируем пост
        edited.value = post
    }

    fun edit(id: Long, content: String) {
        edited.value = empty.copy(
            id = id,
            content = content,
        )
    }

    fun editContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim() // убираем пробулы
            if (trimmed == "") {        //если строка пустая
                return
            }
            edited.value = it.copy(content = trimmed)
        }
    }
}