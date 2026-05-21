# 食事バランスガイド README

## 1. アプリ概要

このアプリは、1日の食事内容を「主食」「副菜」「主菜」「牛乳・乳製品」「果物」の5項目で記録し、食事バランスの達成度を点数として確認できるWebアプリケーションです。

ユーザーは会員登録・ログインを行い、食事内容を登録します。登録した内容は一覧画面で確認でき、必要に応じて更新できます。

---

## 2. 主な機能

| No | 機能 | 内容 |
|---|---|---|
| 1 | 新規会員登録 | メールアドレス、名前、パスワード、年齢、性別を登録する |
| 2 | ログイン | メールアドレスとパスワードでログインする |
| 3 | ログアウト | セッション情報を破棄してログイン画面へ戻る |
| 4 | 食事記録登録 | 主食・副菜・主菜・乳製品・果物の摂取数を登録する |
| 5 | 食事メモ登録 | 食事内容の詳細メモを登録する |
| 6 | 食事記録一覧表示 | 登録済みの食事記録を一覧表示する |
| 7 | 食事記録更新 | 登録済みの食事記録を編集する |
| 8 | 達成度計算 | 各項目の摂取数をもとに達成度を点数化する |

---

## 3. 使用技術

| 分類 | 技術 |
|---|---|
| 言語 | Java 21 |
| フレームワーク | Spring Boot |
| テンプレートエンジン | Thymeleaf |
| ORM | Spring Data JPA |
| データベース | PostgreSQL |
| ビルドツール | Gradle |
| フロントエンド | HTML / CSS |
| セッション管理 | HttpSession |

---

## 4. プロジェクト構成

```text
personal_dev_11619_yamamiya_ayu
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.demo
    │   │       ├── PersonalDev11619YamamiyaAyuApplication.java
    │   │       ├── controller
    │   │       │   ├── DishController.java
    │   │       │   └── UserController.java
    │   │       ├── entity
    │   │       │   ├── Dish.java
    │   │       │   ├── Result.java
    │   │       │   └── User.java
    │   │       ├── model
    │   │       │   └── Account.java
    │   │       └── repository
    │   │           ├── DishRepository.java
    │   │           ├── ResultRepository.java
    │   │           └── UserRepository.java
    │   └── resources
    │       ├── application.properties
    │       ├── schema.sql
    │       ├── data.sql
    │       ├── static
    │       │   └── css
    │       │       └── style.css
    │       └── templates
    │           ├── login.html
    │           ├── user.html
    │           ├── dishes-result.html
    │           ├── dishes-add.html
    │           ├── dishes-note.html
    │           └── dishes-edit.html
    └── test
```

---

## 5. 画面一覧

| 画面 | ファイル | URL | 内容 |
|---|---|---|---|
| ログイン画面 | `login.html` | `/login` | メールアドレスとパスワードでログインする |
| 新規会員登録画面 | `user.html` | `/users/add` | ユーザー情報を登録する |
| 食事記録一覧画面 | `dishes-result.html` | `/dishes/result` | 登録済みの食事記録を一覧表示する |
| 食事記録登録画面 | `dishes-add.html` | `/dishes/add` | 食事バランスの数値を登録する |
| 食事メモ登録画面 | `dishes-note.html` | `/dishes/note` | 食事内容の詳細メモを入力する |
| 食事記録更新画面 | `dishes-edit.html` | `/dishes/{id}/edit` | 登録済み記録を更新する |

---

## 6. URL一覧

### UserController

| HTTPメソッド | URL | 処理内容 | 戻り先 |
|---|---|---|---|
| GET | `/` | ログイン画面表示 | `login.html` |
| GET | `/login` | ログイン画面表示 | `login.html` |
| GET | `/logout` | セッション破棄・ログイン画面表示 | `login.html` |
| POST | `/login` | ログイン処理 | 成功時 `/dishes/result` |
| GET | `/users/add` | 新規会員登録画面表示 | `user.html` |
| POST | `/users/add` | 新規会員登録処理 | 成功時 `/login` |

### DishController

| HTTPメソッド | URL | 処理内容 | 戻り先 |
|---|---|---|---|
| GET | `/dishes/result` | 食事記録一覧表示 | `dishes-result.html` |
| GET | `/dishes/add` | 食事記録登録画面表示 | `dishes-add.html` |
| POST | `/dishes/add` | 食事記録登録処理 | 成功時 `/dishes/result` |
| GET | `/dishes/note` | 食事メモ入力画面表示 | `dishes-note.html` |
| GET | `/dishes/{id}/edit` | 食事記録更新画面表示 | `dishes-edit.html` |
| POST | `/dishes/{id}/edit` | 食事記録更新処理 | 成功時 `/dishes/result` |

---

## 7. データベース設計

### users テーブル

ユーザー情報を管理するテーブルです。

```sql
CREATE TABLE users
(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    age INTEGER,
    gender INTEGER
);
```

| カラム名 | 型 | 制約 | 内容 |
|---|---|---|---|
| user_id | SERIAL | PRIMARY KEY | ユーザーID |
| email | VARCHAR(255) | NOT NULL UNIQUE | メールアドレス |
| name | VARCHAR(20) | NOT NULL | 名前 |
| password | VARCHAR(255) | NOT NULL | パスワード |
| age | INTEGER | なし | 年齢 |
| gender | INTEGER | なし | 性別 |

---

### dishes テーブル

食事カテゴリを管理するテーブルです。

```sql
CREATE TABLE dishes
(
    dish_id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);
```

| カラム名 | 型 | 制約 | 内容 |
|---|---|---|---|
| dish_id | SERIAL | PRIMARY KEY | 食事カテゴリID |
| name | VARCHAR(20) | NOT NULL | 食事カテゴリ名 |

初期データは以下です。

```sql
INSERT INTO dishes(dish_id,name) VALUES (1,'主食');
INSERT INTO dishes(dish_id,name) VALUES (2,'副菜');
INSERT INTO dishes(dish_id,name) VALUES (3,'主菜');
INSERT INTO dishes(dish_id,name) VALUES (4,'牛乳・乳製品');
INSERT INTO dishes(dish_id,name) VALUES (5,'果物');
```

---

### result テーブル

ユーザーが登録した食事記録を管理するテーブルです。

```sql
CREATE TABLE result
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    record_date DATE NOT NULL,
    staple_food INTEGER NOT NULL DEFAULT 0,
    side_dish INTEGER NOT NULL DEFAULT 0,
    main_dish INTEGER NOT NULL DEFAULT 0,
    milk_dish INTEGER NOT NULL DEFAULT 0,
    fruit INTEGER NOT NULL DEFAULT 0,
    detail_memo TEXT,
    achievement INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

| カラム名 | 型 | 制約 | 内容 |
|---|---|---|---|
| id | SERIAL | PRIMARY KEY | 食事記録ID |
| user_id | INTEGER | NOT NULL / FOREIGN KEY | ユーザーID |
| record_date | DATE | NOT NULL | 記録日 |
| staple_food | INTEGER | NOT NULL DEFAULT 0 | 主食の摂取数 |
| side_dish | INTEGER | NOT NULL DEFAULT 0 | 副菜の摂取数 |
| main_dish | INTEGER | NOT NULL DEFAULT 0 | 主菜の摂取数 |
| milk_dish | INTEGER | NOT NULL DEFAULT 0 | 牛乳・乳製品の摂取数 |
| fruit | INTEGER | NOT NULL DEFAULT 0 | 果物の摂取数 |
| detail_memo | TEXT | なし | 詳細メモ |
| achievement | INTEGER | なし | 達成度 |

---

## 8. Entity一覧

| Entity | 対応テーブル | 役割 |
|---|---|---|
| `User.java` | `users` | ユーザー情報を扱う |
| `Dish.java` | `dishes` | 食事カテゴリ情報を扱う |
| `Result.java` | `result` | 食事記録情報を扱う |

---

## 9. Repository一覧

| Repository | 対応Entity | 主な役割 |
|---|---|---|
| `UserRepository.java` | `User` | ユーザー検索・保存 |
| `DishRepository.java` | `Dish` | 食事カテゴリ検索・保存 |
| `ResultRepository.java` | `Result` | 食事記録検索・保存 |

### UserRepository

```java
List<User> findByEmailAndPassword(String email, String password);
```

メールアドレスとパスワードが一致するユーザーを検索します。

### ResultRepository

```java
List<Result> findByUserId(Integer userId);
```

ユーザーIDに紐づく食事記録を検索します。

---

## 10. 処理の流れ

### 10-1. ログイン処理

```text
ログイン画面を表示
  ↓
メールアドレスとパスワードを入力
  ↓
UserRepositoryでユーザー検索
  ↓
一致するユーザーが存在するか確認
  ↓
存在する場合、userIdをセッションに保存
  ↓
食事記録一覧画面へ遷移
```

### 10-2. 新規会員登録処理

```text
新規会員登録画面を表示
  ↓
メールアドレス、名前、パスワード、年齢、性別を入力
  ↓
入力チェック
  ↓
問題がなければusersテーブルに保存
  ↓
ログイン画面へ戻る
```

### 10-3. 食事記録登録処理

```text
食事記録登録画面を表示
  ↓
主食、副菜、主菜、乳製品、果物の数を入力
  ↓
必要に応じて詳細メモを入力
  ↓
入力チェック
  ↓
達成度を計算
  ↓
resultテーブルに保存
  ↓
食事記録一覧画面へ遷移
```

### 10-4. 食事記録更新処理

```text
一覧画面で更新ボタンを押す
  ↓
対象IDの食事記録を取得
  ↓
更新画面に現在の値を表示
  ↓
値を修正して更新
  ↓
resultテーブルを更新
  ↓
食事記録一覧画面へ戻る
```

---

## 11. 達成度計算ロジック

`DishController.java` の `sumAchievement()` メソッドで達成度を計算しています。

初期値は `88` 点です。

各項目の目標範囲から外れると、点数が減点されます。

| 項目 | 目標範囲 | 減点条件 |
|---|---|---|
| 主食 | 5〜7 | 範囲外の場合、差分に応じて減点 |
| 副菜 | 5〜6 | 範囲外の場合、差分に応じて減点 |
| 主菜 | 3〜5 | 範囲外の場合、差分に応じて減点 |
| 牛乳・乳製品 | 2 | 1または3なら軽減点、それ以外は大きく減点 |
| 果物 | 2 | 1または3なら軽減点、それ以外は大きく減点 |

---

## 12. セッション管理

ログイン成功時に、以下のように `userId` をセッションへ保存しています。

```java
session.setAttribute("userId", userList.get(0).getUserId());
```

食事記録登録時には、セッションから `userId` を取得し、`result` テーブルへ保存しています。

```java
Integer userId = (Integer) session.getAttribute("userId");
result.setUserId(userId);
```

---

## 13. application.properties

現在のDB接続設定は以下です。

```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql:personal_dev_11619_yamamiya_ayu
spring.datasource.username=student
spring.datasource.password=himitu
spring.jpa.show-sql=true
spring.sql.init.mode=never
```

### 注意点

`spring.sql.init.mode=never` のため、`schema.sql` と `data.sql` は自動実行されません。

初回セットアップ時は、PostgreSQLに手動でSQLを実行するか、開発初期のみ以下に変更して実行します。

```properties
spring.sql.init.mode=always
```

ただし、`always` にすると起動時にSQLが再実行されるため、既存データを消したくない場合は注意してください。

---

## 14. セットアップ手順

### 14-1. データベース作成

PostgreSQLで以下のデータベースを作成します。

```sql
CREATE DATABASE personal_dev_11619_yamamiya_ayu;
```

### 14-2. application.properties確認

以下の設定が自分の環境と一致しているか確認します。

```properties
spring.datasource.url=jdbc:postgresql:personal_dev_11619_yamamiya_ayu
spring.datasource.username=student
spring.datasource.password=himitu
```

### 14-3. テーブル作成

`schema.sql` を実行して、以下のテーブルを作成します。

- `users`
- `dishes`
- `result`

### 14-4. 初期データ登録

`data.sql` を実行して、`dishes` テーブルにカテゴリデータを登録します。

### 14-5. アプリ起動

Mac / Linuxの場合は以下を実行します。

```bash
./gradlew bootRun
```

Windowsの場合は以下を実行します。

```bat
gradlew.bat bootRun
```

### 14-6. ブラウザで確認

以下にアクセスします。

```text
http://localhost:8080/login
```

---

## 15. 現在確認できる注意点・改善候補

現状コードから確認できる注意点は以下です。

| No | 内容 | 対応方針 |
|---|---|---|
| 1 | `/dishes/result` で全ユーザーの記録を表示している | `findByUserId(userId)` を使い、ログインユーザーの記録だけ表示する |
| 2 | 未ログインでも `/dishes/result` にアクセスできる可能性がある | セッションの `userId` がない場合は `/login` に戻す |
| 3 | `recordDate` を受け取っているが保存時に `LocalDate.now()` を使用している | 入力日を使うなら `recordDate` を保存する |
| 4 | 更新処理で達成度が再計算されていない | 更新時も `sumAchievement()` を呼び出す |
| 5 | `DishRepository` をControllerで受け取っているが現在ほぼ使われていない | 不要なら削除、使うならカテゴリ表示に利用する |
| 6 | `Account.java` が存在するが現在の主要処理では使われていない | 不要なら削除、使うならログインユーザー表示に利用する |
| 7 | パスワード入力欄が `type="text"` になっている | `type="password"` に変更する |
| 8 | `schema.sql` の `DROP TABLE users CASCADE;` に `IF EXISTS` がない | `DROP TABLE IF EXISTS users CASCADE;` に変更する |

---

## 16. 優先して修正した方がよい内容

マスト機能を安定させるため、以下の順番で修正すると安全です。

| 優先度 | 修正内容 |
|---|---|
| 高 | `/dishes/result` をログインユーザーの記録だけ表示する |
| 高 | 未ログイン時に各画面へ直接アクセスできないようにする |
| 高 | 更新時に達成度を再計算する |
| 中 | 登録時に入力された `recordDate` を保存する |
| 中 | パスワード入力欄を `type="password"` にする |
| 中 | 未使用の `Account.java`、`DishRepository` の扱いを整理する |
| 低 | CSSと画面文言を整える |

---

## 17. マスト機能の達成状況

| 機能 | 現在の状態 | 備考 |
|---|---|---|
| 新規会員登録 | 実装済み | 入力チェックあり |
| ログイン | 実装済み | メールアドレスとパスワードで認証 |
| ログアウト | 実装済み | セッション破棄あり |
| 食事記録登録 | 実装済み | 達成度計算あり |
| 食事メモ登録 | 実装済み | hiddenで値を引き継ぐ構成 |
| 食事記録一覧 | 実装済み | ただし全ユーザー分を表示している可能性あり |
| 食事記録更新 | 実装済み | ただし達成度再計算は改善余地あり |
| 食事記録削除 | 未実装 | 必要なら追加する |
| ログイン制御 | 一部未実装 | 直接URLアクセス対策が必要 |

---

## 18. 今後追加するとよい機能

- 食事記録の削除機能
- ログインユーザーごとの記録一覧表示
- 日付別検索
- 月別一覧表示
- 達成度の平均表示
- グラフ表示
- パスワードのハッシュ化
- 入力日の指定保存
- 管理者画面
- スマートフォン対応デザイン

---

## 19. 最終まとめ

このアプリは、食事バランスを日々記録し、主食・副菜・主菜・牛乳・乳製品・果物の摂取状況を点数化するWebアプリケーションです。

基本機能として、会員登録、ログイン、食事記録登録、メモ登録、一覧表示、更新処理は実装されています。

一方で、ログインユーザーごとの記録表示、未ログイン時のアクセス制御、更新時の達成度再計算、削除機能などは改善余地があります。

まずは、ログイン中のユーザーIDを使って `result` テーブルを絞り込む処理を追加し、ユーザーごとの記録管理が正しくできる状態にすることが重要です。
