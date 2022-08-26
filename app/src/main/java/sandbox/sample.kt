package sandbox

class PostActivity {
    constructor() {
        println("ACITIVITY STARTED")
    }
}

fun main() {
    println("test")

    val postActivity = PostActivity::class.java

    startActivity(postActivity)
}

fun startActivity(postActivity: Class<PostActivity>) {
    postActivity.newInstance()
}
