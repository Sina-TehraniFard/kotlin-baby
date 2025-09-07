公式ドキュメント https://kotlinlang.org/docs/sealed-classes.html を翻訳したものになる

# Sealed classes と Sealed interfaces

Sealed classes と Sealed interfaces は、クラス階層における 継承の範囲を制御する仕組みです。  
sealed class が定義されているモジュールやパッケージの外では、新しいサブクラスを作れないようになっています。  
そのため、sealed class の直接のサブクラスは、コンパイル時点ですべて把握されています。

> [!NOTE]
> **Direct subclasses** とは、親クラスを直接継承するクラスを指します。  
> **Indirect subclasses** とは、親クラスからさらに1段階以上下で継承されるクラスを指します。

sealed interface とその実装についても同じ考え方が当てはまります。  
sealed interface を含むモジュールが一度コンパイルされると、そのあとで新しい実装を追加することはできません。

sealed class や sealed interface を when 式と組み合わせると、すべてのサブクラスの挙動を網羅的に扱えるようになり、新しいサブクラスが追加されてコードに悪影響を与える心配がなくなります。

sealed class が役立つ典型的な場面は次のとおりです:

- **クラス継承を限定したいとき**: サブクラスの集合をあらかじめ定義し、それ以外はコンパイル時に存在しないと保証したい場合。
- **型安全な設計が必要なとき**: 状態管理や複雑な条件分岐を安全に扱いたい場合。when 式との組み合わせが有効です。
- **閉じた API を提供したいとき**: 外部の利用者が意図通りに API を使えるようにし、頑健でメンテナンスしやすいライブラリを作りたい場合。


## ユースケース

sealed class は、アプリケーションの状態やイベントなど、限られた種類のオブジェクトを安全に表現したいときによく使われます。ここでは、実際のユースケースの一例を紹介します。

### UI アプリケーションでの状態管理

たとえば、UI の状態（読み込み中、成功、エラーなど）を sealed class で表現すると、状態ごとの処理を安全に記述できます。

```kotlin
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}

fun render(state: UiState) {
    when (state) {
        is UiState.Loading -> println("読み込み中です")
        is UiState.Success -> println("データ: ${state.data}")
        is UiState.Error -> println("エラー: ${state.message}")
    }
}
```

このように sealed class を使うことで、状態の種類が追加・変更された場合にも when 式で網羅性が保証され、バグを防ぎやすくなります。

### 決済手段の扱い

決済手段の種類が限られている場合に、sealed class を使って安全に管理できます。たとえば、クレジットカード、PayPal、銀行振込といった決済方法を表現し、それぞれの処理を分けて実装できます。

```kotlin
sealed class PaymentMethod {
    object CreditCard : PaymentMethod()
    object PayPal : PaymentMethod()
    data class BankTransfer(val accountNumber: String) : PaymentMethod()
}

fun processPayment(method: PaymentMethod) {
    when (method) {
        is PaymentMethod.CreditCard -> println("クレジットカードで支払い処理")
        is PaymentMethod.PayPal -> println("PayPalで支払い処理")
        is PaymentMethod.BankTransfer -> println("銀行振込で支払い処理（口座番号: ${method.accountNumber}）")
    }
}
```

このように sealed class を使うことで、新しい決済手段が追加された場合に when 式での処理漏れを防げます。

### API リクエストとレスポンス処理

API のリクエストやレスポンスを sealed class で表現することで、成功や失敗の状態を安全に扱えます。これにより、レスポンスの種類ごとに適切な処理を実装しやすくなります。

```kotlin
sealed class ApiResponse {
    data class Success(val data: String) : ApiResponse()
    data class Error(val errorMessage: String) : ApiResponse()
    object Loading : ApiResponse()
}

fun handleResponse(response: ApiResponse) {
    when (response) {
        is ApiResponse.Success -> println("成功: ${response.data}")
        is ApiResponse.Error -> println("エラー: ${response.errorMessage}")
        ApiResponse.Loading -> println("読み込み中...")
    }
}
```

この設計により、API の状態を網羅的に管理でき、状態に応じた処理を漏れなく実装できます。
