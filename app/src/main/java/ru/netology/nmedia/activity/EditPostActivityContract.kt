package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostActivityContract : ActivityResultContract<PostActivityData, PostActivityData?>() {
    override fun createIntent(context: Context, input: PostActivityData): Intent {
        // Создаем "намерение" запустить новую активити
        // первый параметр - текущая активити, второй - активити, которую мы открываем
        val intent = Intent(context, PostActivity::class.java)

        intent.putExtra("POST_CONTENT", input.content)
        intent.putExtra("POST_ID", input.id)

        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PostActivityData? {
        val postId = intent?.getLongExtra("POST_ID", 0L)
        val postContent = intent?.getStringExtra("POST_CONTENT")

        postId ?: return null
        postContent ?: return null

        return PostActivityData(id = postId, content = postContent)
    }
}

class PostActivityData(val id: Long, val content: String) {

}
