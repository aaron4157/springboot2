# Transaction Management(事務管理)

## 1 概述

例如，需要在一次請求中操作資料表A、B，然後返回結果。

此時，例外處理機制就要考慮:若更新資料表B出錯、則必須回復對資料表A的操作。方法出錯時，需回復到未執行方法的狀態，以保證方法的原子性。

Springboot 提供兩種方式管理事務(交易)，分別是編程式(programatic)與宣告式(declarative)。前者的出現比較早、然而在一些業務場景仍然很實用。

後者的好處是由框架代為管理，僅需要少數設定即可。但是，使用宣告式事務管理需要仔細設定參數，否則框架的行為可能不如預期。

關於編程式與宣告式事務管理的選擇，可以參考[官方文件](https://docs.spring.io/spring-framework/reference/data-access/transaction/tx-decl-vs-prog.html)

## 2 設定

### 2.1 如何設定編程式事務管理

一段範例程式碼如下。值得注意的是，只要設定好DataSource，顯式調用的DataSourceTransactionManager與JdbcTemplate便已存在Spring容器當中，可直接注入。

```
@Autowired
DataSourceTransactionManager transactionManager;

someTransactionalMethod(params) {
	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	TransactionStatus status = transactionManager.getTransaction(def);
	try {
		// db operations
		// filesystem operations, api invocations, etc.
		transactionManager.commit(status);
	} catch(Exception e) {
		// log exception
		transactionManager.rollback(status);
	}

}
```

下文專門討論宣告式事務管理。

### 2.2 如何設定宣告式事務管理

1. 在Spring boot框架的啟動方法增加註解 @EnableTransactionManagement
1. 在需要保持原子性的方法加上註解 @Transactional

### 2.3 預設行為模式

1. 事務管理只對資料庫操作有效。對於檔案系統的修改，需要自行撰寫回復方法；建議先進行資料庫操作、再儲存檔案
1. 在方法中調用外部方法，事務管理會發生作用、並且是**同步的**。有異步需求(ex.註冊完成則寄信)請移至事件處理流程
1. 若方法沒有拋出錯誤，框架認定事務已完成並進行提交(commit)。所以交易的方法不要捕獲所有例外、交給調用者處理。

### 2.4 事務傳播

考慮以下調用鏈

```
@Transactional
methodA() {
	// do something ...
	methodB();
	methodC();
}
```
事務管理的默認傳播行為是PROPAGATION_REQUIRED，因此若methodC錯誤並回滾、因為mothodA本務、mathodB與methodC共享同一個事務，因此會一起回滾。

可以設定個別步驟(例如methodC)開啟自己的事務，將個別步驟的回滾邏輯隔離出來。事務傳播設定攸關例外處理邏輯，詳細可參考以下技術文章

[詳細瞭解 Spring Transaction 的 Propagation](https://www.tpisoftware.com/tpu/articleDetails/2741)

### 2.5 事件處理

事件處理的目的是解耦合。例如一些前置作業、或初始化，與交易的方法不可並行。簡單的邏輯，可放在上層調用鏈；更細緻的作法是由事務狀態驅動行為，一如資料表的trigger腳本。

Spring boot 框架使用事件監聽技術，應對事務的各種狀態。有:

- AFTER_COMMIT (提交後發送。預設)
- AFTER_ROLLBACK(回滾後發送)
- AFTER_COMPLETION(不論提交或回滾皆會發送)
- BEFORE_COMMIT (提交前發送)

在交易方法中，調用 ApplicationEventPublisher.publishEvent(Object) 方法，廣告事件、再由自訂的事件監聽器(需註記@TransactionalEventListener)捕獲並處理。


## References

[Choosing Between Programmatic and Declarative Transaction Management](https://docs.spring.io/spring-framework/reference/data-access/transaction/tx-decl-vs-prog.html)

[Programmatic Transaction Management in Spring](https://www.baeldung.com/spring-programmatic-transaction-management)

[Spring Boot – Transaction Management Using @Transactional Annotation](https://www.geeksforgeeks.org/spring-boot-transaction-management-using-transactional-annotation/)

[TransactionalEventListener使用场景以及实现原理，最后要躲个大坑](https://juejin.cn/post/7011685509567086606)

