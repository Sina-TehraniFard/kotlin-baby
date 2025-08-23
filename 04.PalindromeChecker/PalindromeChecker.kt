fun main() {
    val input = readLine()
    val result = input?.isPalindrome() ?: false
    if (result) {
        println("Palindrome")
    } else {
        println("Not Palindrome")
    }
}


fun String.isPalindrome(): Boolean {
    val normalized = this.lowercase()
    return normalized == normalized.reversed()
}
