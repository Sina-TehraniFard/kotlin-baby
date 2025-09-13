data class UserProfile(
    val userId: UserId,
    val posts: List<Post>,
    val comments: List<Comment>,
)
