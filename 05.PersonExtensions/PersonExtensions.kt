data class Parson(
    val name: String,
    val age: Int
)

fun Parson.isAdult(): Boolean = age >= 20

val Parson.initial: Char
    get() = this.name.first()

fun main() {
    val parson = Parson("Tehrani", 25)
    // 定義にfunを使用しているものは呼び出す時必ず丸括弧（）を使用する
    print("Name: ${parson.name}, Age: ${parson.age} -> Adult: ${parson.isAdult()}, Initial: ${parson.initial}")
}
    
