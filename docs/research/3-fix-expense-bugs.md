# バグ調査レポート: 経費精算テスト失敗

**Issue**: #3 / **日付**: 2026-07-28

## 発見したバグ

### Bug 1: TRANSPORT の三項演算子が逆

```java
// 修正前（誤）
case TRANSPORT -> item.amount() > TRANSPORT_CAP ? item.amount() : TRANSPORT_CAP;
// 修正後（正）
case TRANSPORT -> item.amount() > TRANSPORT_CAP ? TRANSPORT_CAP : item.amount();
```

上限超えのときに全額が返り、上限以内のときに上限額が返っていた。

### Bug 2: MEAL が四捨五入（切り捨てが正しい）

```java
// 修正前（誤）: Math.round → 四捨五入
case MEAL -> Math.round(item.amount() / 2.0f);
// 修正後（正）: 整数除算 → 切り捨て
case MEAL -> item.amount() / 2;
```

Javadoc の仕様「1円未満は切り捨て」に反して四捨五入していた。

## 結果

`./gradlew test` 9件すべてパス。
