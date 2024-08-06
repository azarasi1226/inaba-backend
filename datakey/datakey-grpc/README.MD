# DataKey Service GRPC

## See
 - [gRPC-Spring-Boot-Starter Documentation](https://grpc-ecosystem.github.io/grpc-spring/en/)

## 環境構築
**トップページのREADME.mdの環境構築が終わっていること**  
Intellij IDEAで「すべてのGradleプロジェクトの再ロード」を実行する  
Intellij IDEAで「inaba/datakey/datakey-grpc/Tasks/other/generateProto」を実行する  
「datakey/datakey-grpc/src/generated」以下にソースコードが生成され、Intellij IDEAが生成されたソースコードとしてディレクトリをマークする
GRPC Protoファイルからのソースコード生成終わり

## 使い方
[Implementing the Service](https://grpc-ecosystem.github.io/grpc-spring/en/server/getting-started.html#implementing-the-service)を参考に実装する

## gRPCurlについて
手軽にgRPCの通信を実行し、チェックできるツール  

[gRPC サービスをテストする](https://learn.microsoft.com/ja-jp/aspnet/core/grpc/test-tools?view=aspnetcore-8.0#grpcurl)を参考してください  

```shell
PS C:\grpcurl_1.9.1_windows_x86_64> .\grpcurl.exe -plaintext -d '{\"relationId\":\"01J4MBGVAA8ERFHEFR5ZG8M0C5\"}' localhost:9095 dataKey.DataKey.createDataKey
{
  "base64PlaneDataKey": "h3vX87JLJNAKPWMarFzCw54g6spfgICtD75bgEnps08="
}
PS C:\grpcurl_1.9.1_windows_x86_64> .\grpcurl.exe -plaintext -d '{\"relationId\":\"01J4MBGVAA8ERFHEFR5ZG8M0C5\"}' localhost:9095 dataKey.DataKey.getDataKey
{
  "base64PlaneDataKey": "h3vX87JLJNAKPWMarFzCw54g6spfgICtD75bgEnps08="
}
```