# Vivace

## 対応バージョン・ビルド情報

- 対象: Paper 1.19.4 / 1.20.1〜1.20.6 / 1.21.x（1.21.11まで）/ 26.1.x〜26.3
- コンパイルAPI: Paper 26.3（現行の安定版API）
- ビルド用JDK: Java 25
- Javaコンパイルターゲット: Java 17（class file 61）
- Windowsでのビルド: `gradlew.bat clean build`
- 出力先: `build/libs/Vivace-1.19.4-26.3.jar`

## 互換性方針

このプロジェクトはPaper 26.3 APIを使ってコンパイルしますが、生成するクラスファイルはJava 17向けにします。これにより、古いPaperサーバーでもJava 17以降の実行環境でプラグインを読み込める形を維持します。

ただし、Javaのバイトコード互換性だけでPaper APIの互換性が保証されるわけではありません。JavaソースはPaper 1.19.4から存在するAPIだけを使い続ける必要があります。今回のソース確認では、使用しているBukkit/Paper APIはInventory、Command、Event、Configuration、Adventure Componentなどの既存APIで、26.3専用APIへの依存は見つかっていません。

`plugin.yml` の `api-version` は `1.19` のままです。Paper公式ドキュメントでは `api-version` は1.13から26.3まで指定可能で、指定値より古いサーバーはプラグインを読み込まない仕様です。最低対応を1.19.4に保つため、ここは引き上げていません。

## 注意事項

- Paper 26.1以降のサーバー起動にはJava 25以上が必要です。Paper 1.20.1〜1.21.11はJava 21以上、1.19.xはJava 17以上が必要です。
- Paper 26.3向けAPIを読むため、ビルドにはJDK 25が必要です。IntelliJ IDEAのGradle JVMもJDK 25に設定してください。
- このZIPの作成時点では、JDK 25がないためこちらの環境でGradleビルドは実行できていません。IntelliJで `clean build` を実行して確認してください。
- 1つのJARで全対象版をサポートできるかは、Paper 1.19.4 / 1.20.x / 1.21.x / 26.3の各サーバーで実際に起動・コマンド・GUIをテストして確定してください。
- Folia対応は対象外です。
