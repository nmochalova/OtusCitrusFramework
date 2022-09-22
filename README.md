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

**TestHelperHTTP** - пример http-хелпера, который умеет обращаться отправлять запросы от имени своего http-клиента на некий endpoint и валидировать полученный ответ

[Песочница для REST](https://reqres.in/)

[Песочница для SOAP](https://www.dataaccess.com/)

[Документация по citrus framework v.3.2.1](https://citrusframework.org/citrus/reference/3.2.1/html/index.html#preface)

[Учебный проект](https://github.com/reviol/QA-JAVA)

[Конспект лекции](https://github.com/nmochalova/OtusCitrusFramework/blob/main/Doc/Конспект.docx)
