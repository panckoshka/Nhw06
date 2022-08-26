package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class NewPostActivityContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        val intent = Intent(context, PostActivity::class.java)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        intent?.getStringExtra("POST_CONTENT")
}