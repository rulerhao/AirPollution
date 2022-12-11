# AirPollution
## 開啟方法
1. 直接切換到 Master Branch 後 Build 就可以使用了。

## API 使用
1. 因為 API 本身沒有透過字串搜尋的功能，因此字串搜尋的功能由自己實作篩選 Data 內資料。
2. 因為目前站數只有 313 站，所以 limit 設為 1000 可以取得所有的站資料。
3. 有一點我困惑的是關於搜尋站名，在展示圖中搜尋"新"，但在圖片內顯示有新北市的林口，我想這應該是個 BUG，我自己設計會讓搜尋的部分只 Parse 站名。

## 大致說明
1. 以 MVVM + Clean Architecture 為架構，用 Use case 來連接 Model 與 ViewModel。
2. 以 Hilt 來協助建立架構，降低耦合度及 Memory Leak。
3. 以 Flow 運用 Retrofit 來取 Remote api，讓這部分程式碼變得更簡潔也更好寫一些。
4. 如果能把 View 中的 Switch 拉到 ViewModel 中判斷，用各個 State 來取代並存為 Livedata 會更好，這個部份是我需要修正的。