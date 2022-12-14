# OtusCitrusFramework
**Содержание проекта:** 
- Проект **newLesson** содержит примеры тестов с использованием Citrus Framework v.3.2.1  
- Проект **demoApiHelpers** содержит примеры API Helpers с использованием Citrus Framework v.2.8.0 

## newLesson содержит следующие тесты:

**FirstTestGetUser** - Простой request-response с использованием своего http-клиента для метода get и валидация ответа при помощи objectMapper

**FirstTestCreateUser** - Простой request-response  с использованием своего http-клиента для метода post и валидация ответа при помощи objectMapper и отдельных валидаторов citrus

**FirstDataProviderCreateUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем post-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

**FirstDataProviderGetUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем get-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

**FirstBehaviorTest** - тест с ипользованием ApplyBehavior, т.е. поведенческого класса, в котором будем эмулировать некие повторяющиеся действия.

**FirstTestSOAP** - тест с отправкой SOAP-запроса и получения SOAP-ответа, а также с дальнейшей валидацией ответа с эталонным xml.

**TestMock** - тест с демонстрацией мокирования сервиса Reqres при вызове get-запроса и получения ответа от заглушки, вместо Reqres.


## demoApiHelpers содержит следующие тесты:

**TestHelperHTTP** - пример http-хелпера, который умеет отправлять запросы от имени своего http-клиента на некий endpoint, конвертировать полученный ответ из Json в pojo и сравнивать его с ожидаемым ответом.

**TestHelperSOAP** - пример soap-хелпера, который умеет отправлять запросы от имени своего soap-клиента на некий endpoint, конвертировать полученный ответ из xml в pojo и сравнивать его с ожидаемым ответом.

**TestHelperWSS** - пример WebSocket-хелпера, который умеет отправлять запросы от имени своего wss-клиента на некий открытый WebSocket сервис от Binance, конвертировать полученный ответ из Json в pojo и сравнивать его с ожидаемым ответом.

**TestHelperMock** - пример Mock-хелпера, который умеет отправлять запросы от имени своего mock-клиента на свой mock-сервер, получать хардкордный ответ, конвертировать полученный ответ из Json в pojo и сравнивать его с ожидаемым ответом.

**TestHelperConsumerMQ** - пример action для консумера, который принимает данные из очереди MQ и валидирует их (не рабочий тест)
**TestHelperProduserMQ** - пример action для продюсера, который передает данные в очередь MQ и валидирует их (не рабочий тест)

**TestHelperSQL** - пример action для sql-запроса к БД Postgrees (не рабочий тест)


[Песочница для REST](https://reqres.in/)

[Песочница для SOAP](https://www.dataaccess.com/)

[Документация по citrus framework v.3.2.1](https://citrusframework.org/citrus/reference/3.2.1/html/index.html#preface)

[Учебный проект](https://github.com/reviol/QA-JAVA)

[Конспект лекции](https://github.com/nmochalova/OtusCitrusFramework/blob/main/Doc/Конспект.docx)
