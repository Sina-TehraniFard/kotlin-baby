ealed class Shape {
    // Circle = 半径 radius を持つ図形。Shape を継承する
    data class Circle(val radius: Double): Shape()
    // Rectangle = 幅 width と高さ height を持つ図形。Shape を継承する
    data class Rectangle(val width: Double, val height: Double): Shape()
    // Triangle = 底辺 base と高さ height を持つ図形。Shape を継承する
    data class Triangle(val base: Double, val height: Double): Shape()
}

// Shape に対する拡張関数 area() を定義
// when (this) により、実際の型が Circle/Rectangle/Triangle のどれかで分岐する
fun Shape.area(): Double = when (this) {
    // Circle の場合: 半径が正なら円の面積 πr² を返す
    // 半径が0以下なら不正と見なし NaN を返す（計算不能値）
    is Shape.Circle -> if (radius > 0) kotlin.math.PI * radius * radius else Double.NaN
    // Rectangle の場合: 幅と高さが正なら面積を返す、そうでなければ NaN
    is Shape.Rectangle -> if (width > 0 && height > 0) width * height else Double.NaN
    // Triangle の場合: 底辺と高さが正なら三角形の面積 (底辺×高さ)/2 を返す、そうでなければ NaN
    is Shape.Triangle -> if (base > 0 && height > 0) (base * height) / 2 else Double.NaN
}

// Shape に対する拡張関数 describe() を定義
// 各図形を文字列に変換する。プロパティを展開して人間が理解できる形式にする
fun Shape.describe(): String = when (this) {
    // Circle の場合 → "Circle(r=...)" の文字列を返す
    is Shape.Circle -> "Circle(r=$radius)"
    // Rectangle の場合 → "Rectangle(w=...,h=...)" を返す
    is Shape.Rectangle -> "Rectangle(w=$width,h=$height)"
    // Triangle の場合 → "Triangle(b=...,h=...)" を返す
    is Shape.Triangle -> "Triangle(b=$base,h=$height)"
}

// プログラムのエントリーポイント main()
fun main() {
    // 複数の図形インスタンスをリストにまとめる
    // 最後の Circle(-1.0) は不正値としてテスト用
    val shapes = listOf(
        Shape.Circle(3.0),
        Shape.Rectangle(2.0, 5.0),
        Shape.Triangle(3.0, 4.0),
        Shape.Circle(-1.0) // invalid
    )

    // forEach でリストの各要素を処理
    shapes.forEach { shape ->
        // 各図形の面積を計算
        val area = shape.area()
        // 計算結果が NaN なら「Invalid parameters」と表示
        if (area.isNaN()) {
            println("${shape.describe()}: area=Invalid parameters")
        } else {
            // 有効な場合は面積をそのまま出力
            println("${shape.describe()}: area=$area")
        }
    }
}
