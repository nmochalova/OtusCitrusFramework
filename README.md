# OtusCitrusFramework
**Содержание проекта:** 
- конспект лекции Citrus Framework;
- создание тестового проекта и запуск теста.

## Проект содержит следующие тесты:

**FirstTestGetUser** - Простой request-response с использованием своего http-клиента для метода get и валидация ответа при помощи objectMapper

**FirstTestCreateUser** - Простой request-response  с использованием своего http-клиента для метода post и валидация ответа при помощи objectMapper и отдельных валидаторов citrus

**FirstDataProviderCreateUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем post-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

**FirstDataProviderGetUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем get-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

**FirstBehaviorTest** - тест с ипользованием ApplyBehavior, т.е. поведенческого класса, в котором будем эмулировать некие повторяющиеся действия.

**FirstTestSOAP** - тест с отправкой SOAP-запроса и получения SOAP-ответа, а также с дальнейшей валидацией ответа с эталонным xml.

[Песочница для REST](https://reqres.in/)

[Песочница для SOAP](https://www.dataaccess.com/)

[Документация по citrus framework v.3.2.1](https://citrusframework.org/citrus/reference/3.2.1/html/index.html#preface)

[Учебный проект beginner v.3.2.1](https://github.com/reviol/QA-JAVA/tree/main/lesson_Citrus_Beginner_v3.2.1) 

[Учебный проект extended v.3.2.1](https://github.com/reviol/QA-JAVA/tree/main/lesson_Citrus_Extended_v3.2.1)

[Учебный проект extended v.2.8.0](https://github.com/reviol/QA-JAVA/tree/main/lesson_Citrus_Extended)

[Конспект лекции](https://github.com/nmochalova/OtusCitrusFramework/blob/main/Doc/Конспект.docx)
