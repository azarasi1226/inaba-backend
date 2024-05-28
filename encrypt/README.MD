# Inaba Encrypt Service
## Encrypt Serviceでやりたいこと
ユーザーの機密情報などを取り扱うに当たってAxon Frameworkとの相性の悪さを解消することを目的としたサービス  
Axon Frameworkは過去のイベントをすべて記録するため、機密情報が含まれたレコードが記録された場合であっても削除することができない問題がある  
Encrypt Serviceで機密情報を暗号化することで機密情報をイベントに記録することができるようになる  
また、AWS KMSを利用したエンベローブ暗号化を行うことで暗号化キーの管理を容易にし、安全かつメンテナンスしやすいシステムの構築を目指すことができる

## ルートキー処理シーケンス
```mermaid
sequenceDiagram
    autonumber
    participant identity_service as Identity Service
    participant encrypt_service as Encrypt Service
    participant aws_kms as AWS KMS
    participant db as Database
    identity_service->>encrypt_service: サービス起動時にサービスIDを送信する
    Note right of identity_service: サービスID
    encrypt_service->>db: ルートキーへのアクセス情報をリクエスト
    db->>encrypt_service: ルートキーへのアクセス情報を返す
    alt ルートキーが未発行の場合
        encrypt_service->>aws_kms: AWSコンソールでルートキーの生成をリクエスト
        aws_kms->>aws_kms: ルートーキーを生成する
        aws_kms->>encrypt_service: アクセスキーとシークレットキーを返す
        Note left of aws_kms: アクセスキー・シークレットキー
        encrypt_service->>db: ルートキーへのアクセス情報を記録する
        Note right of encrypt_service: アクセスキー・シークレットキー
    end
    encrypt_service->>identity_service: OKレスポンス
```

## データ暗号化処理シーケンス
```mermaid
sequenceDiagram
    autonumber
    participant identity_service as Identity Service
    participant encrypt_service as Encrypt Service
    participant aws_kms as AWS KMS
    participant db as Database
    identity_service->>encrypt_service: 機密情報暗号化キーの生成をリクエスト
    Note right of identity_service: サービスID・データID
    encrypt_service->>aws_kms: ルートキーをリクエストする
    Note right of encrypt_service: アクセスキー・シークレットキー
    aws_kms->>encrypt_service: ルートキーを返す
    Note left of aws_kms: ルートキー
    encrypt_service->>encrypt_service: データキーを生成する
    encrypt_service->>encrypt_service: ルートキーを利用してデータキーを暗号化する
    encrypt_service->>db: 暗号化データキーを記録する
    Note right of encrypt_service: サービスID・データID・暗号化データキー
    encrypt_service->>identity_service: 機密情報暗号化キーを返す
    Note left of encrypt_service: データキー
```

## データ復号化処理シーケンス
```mermaid
sequenceDiagram
    autonumber
    participant identity_service as Identity Service
    participant encrypt_service as Encrypt Service
    participant aws_kms as AWS KMS
    participant db as Database
    identity_service->>encrypt_service: 機密情報暗号化キーをリクエスト
    Note right of identity_service: サービスID・データID
    encrypt_service->>aws_kms: ルートキーをリクエストする
    Note right of encrypt_service: アクセスキー・シークレットキー
    aws_kms->>encrypt_service: ルートキーを返す
    Note left of aws_kms: ルートキー
    encrypt_service->>db: 暗号化データキーをリクエストする
    Note right of encrypt_service: サービスID・データID
    db->>encrypt_service: 暗号化データキーを返す
    Note left of db: 暗号化データキー
    encrypt_service->>encrypt_service: ルートキーを利用してデータキーを復号化する
    encrypt_service->>identity_service: 機密情報暗号化キーを返す
    Note left of encrypt_service: データキー
```

## 参考
- [AWS Key Management Service 開発者ガイド - エンベローブ暗号化](https://docs.aws.amazon.com/ja_jp/kms/latest/developerguide/concepts.html#enveloping)