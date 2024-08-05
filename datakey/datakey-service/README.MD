# DataKey Service

## Sample
Sample ULID  
01J4H8MB272NVCPYM56Q2C00SB

Sample ULIDをrelationIdにセットしてDataKeyを作成する
```shell
curl -X 'POST' \
  'http://localhost:8085/api/datakeys' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "relationId": "01J4H8MB272NVCPYM56Q2C00SB"
}'
```

レスポンス
```json
{
  "base64PlaneDataKey": {
    "value": "I59Rbl51cX9rTB3ZE5ftUvydy9T1qzS/XYXeyZ0Y5kw="
  }
}
```

レスポンスヘッダー
```text
 connection: keep-alive 
 content-type: application/json 
 date: Mon,05 Aug 2024 12:36:13 GMT 
 keep-alive: timeout=60 
 transfer-encoding: chunked 
```

Sample ULIDをrelationIdにセットしてDataKeyを取得する
```shell
curl -X 'GET' \
  'http://localhost:8085/api/datakeys/01J4H8MB272NVCPYM56Q2C00SB' \
  -H 'accept: */*'
```

レスポンス
```json
{
  "base64PlaneDataKey": {
    "value": "I59Rbl51cX9rTB3ZE5ftUvydy9T1qzS/XYXeyZ0Y5kw="
  }
}
```

レスポンスヘッダー
```text
 connection: keep-alive 
 content-type: application/json 
 date: Mon,05 Aug 2024 12:38:26 GMT 
 keep-alive: timeout=60 
 transfer-encoding: chunked 
```