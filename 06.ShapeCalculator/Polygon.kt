import kotlin.math.hypot

// --- 型定義（ネスト） ---
sealed class Polygon {
    data class Square(val side: Double) : Polygon()
    data class Rectangle(val width: Double, val height: Double) : Polygon()
    data class RightTriangle(val base: Double, val height: Double) : Polygon()
}


fun Polygon.area(): Double = when (this) {
    is Polygon.Square -> {
        require(side > 0) { "Side must be > 0" }
        side * side
    }
    is Polygon.Rectangle -> {
        require(width > 0 && height > 0) { "Width/Height must be > 0" }
        width * height
    }
    is Polygon.RightTriangle -> {
        require(base > 0 && height > 0) { "Base/Height must be > 0" }
        0.5 * base * height
    }
}

fun Polygon.perimeter(): Double = when (this) {
    is Polygon.Square -> {
        require(side > 0) { "Side must be > 0" }
        4 * side
    }
    is Polygon.Rectangle -> {
        require(width > 0 && height > 0) { "Width/Height must be > 0" }
        2 * (width + height)
    }
    is Polygon.RightTriangle -> {
        require(base > 0 && height > 0) { "Base/Height must be > 0" }
        base + height + hypot(base, height)
    }
}

fun Polygon.describe(): String = when (this) {
    is Polygon.Square        -> "Square(side=$side)"
    is Polygon.Rectangle     -> "Rectangle(w=$width, h=$height)"
    is Polygon.RightTriangle -> "RightTriangle(b=$base, h=$height)"
}

fun Polygon.scale(factor: Double): Polygon {
    require(factor > 0) { "Scale factor must be > 0" }
    return when (this) {
        is Polygon.Square ->
            copy(side = side * factor)
        is Polygon.Rectangle ->
            copy(width = width * factor, height = height * factor)
        is Polygon.RightTriangle ->
            copy(base = base * factor, height = height * factor)
    }
}

// --- 動作確認 ---
fun main() {
    val s: Polygon = Polygon.Square(2.0)
    println("${s.describe()} | area=${s.area()} | perimeter=${s.perimeter()}")

    val scaled = s.scale(2.0)
    println("${scaled.describe()} | area=${scaled.area()} | perimeter=${scaled.perimeter()}")

    // 例外検証（任意）
    try {
        val bad: Polygon = Polygon.RightTriangle(0.0, 4.0)
        println(bad.area())
    } catch (e: IllegalArgumentException) {
        println("[ERROR] ${e.message}")
    }
}