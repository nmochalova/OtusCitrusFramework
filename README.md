# OtusCitrusFramework
**Содержание проекта:** 
- конспект лекции Citrus Framework;
- создание тестового проекта и запуск теста.

## Проект содержит следующие тесты:

**FirstTestGetUser** - Простой request-response с использованием своего http-клиента для метода get и валидация ответа при помощи objectMapper

**FirstTestCreateUser** - Простой request-response  с использованием своего http-клиента для метода post и валидация ответа при помощи objectMapper и отдельных валидаторов citrus

**FirstDataProviderCreateUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем post-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

**FirstDataProviderGetUserTest** - параметризованный тест с испозованием аннотации @DataProvider из TestNG. Отправляем get-запрос для каждого элемента данных из массива данных, получаем результат и валидируем его на совпадение с ожидаемым результатом.

[Песочница](https://reqres.in/)

[Документация по citrus framework v.3.2.1](https://citrusframework.org/citrus/reference/3.2.1/html/index.html#preface)

[Учебный проект](https://github.com/reviol/QA-JAVA/tree/main/lesson_Citrus_Beginner_v3.2.1)

[Конспект лекции](https://github.com/nmochalova/OtusCitrusFramework/blob/main/Doc/Конспект.docx)
