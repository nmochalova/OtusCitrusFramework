package tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import pojo.DataJson;
import pojo.Support;
import pojo.User;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
//Для вывода логов необходим файл log4j2.properties в resourses (его нет в первоначальном проекте)

public class FirstTestGetUser extends TestNGCitrusSupport { //расширяемся от класса из Citrus framework для TestNG
    //Для JUnit нужно унаследоваться от класса JUnit4CitrusSupport , import com.consol.citrus.junit.JUnit4CitrusSupport;

    //При вызове контекста важно чтобы import был из citrus
    public TestContext context;

    //@Test должна быть от TestNG
    @Test(description = "Получение информации о пользователе", enabled = true)
    @CitrusTest //эта аннотация дает возможность citrus framework видеть этот тестовый метод и запускать его при прогонах
    public void getTestActions() {
        //инициализируем контекст внутри тестового метода, чтобы все переменные и внутренние beans были здесь видны
        this.context = citrus.getCitrusContext().createTestContext();

        //1.
        //Устанавливаем переменную
        context.setVariable("value", "superValue");
        //Получение переменной и вывод ее значения в консоль
        $(echo("Property \"value\" = " + context.getVariable("value")));

        //2.
        //Достанем значение переменной userId из файла citrus.properties
        $(echo("We have userId = " + context.getVariable("userId")));
        //Еще один способ вывода через ${}
        $(echo("Property \"userId\" = " + "${userId}"));

        //3. Вывод переменной при помощи citrus фичу citrus:currentDate()
        variable("now", "citrus:currentDate()");
        $(echo("Today is: ${now}"));

        //Каждый test action (в нашем тесте каждое echo) citrus помечает как TEST STEP №
    }

    //Вариант 1. Простой request-response без мокирования
    @Test(description = "Отправка get-запроса на метод singleUser и валидация ответа (вариант 1)", enabled = true)
    @CitrusTest
    public void singleUserTestV1(){
        this.context = citrus.getCitrusContext().createTestContext();

        //В citrus-context.xml настраиваем своего http-клиента restClientReqres
        //Отправка http-запроса http-клиентом restClientReqres
        $(http()
                .client("restClientReqres")
                .send()
                .get("users/"+context.getVariable("userId"))
        );
        //получение ответа http-клиентом restClientReqres и сверка его с эталонным response
        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getJsonData(),"objectMapper")) //метод из citrus-validation-json
        );
    }

    //Вариант 2. Простой request-response без мокирования
    @Test(description = "Отправка get-запроса на метод singleUser и валидация ответа (вариант 2)", enabled = true)
    @CitrusTest
    public void singleUserTestV2(){
        this.context = citrus.getCitrusContext().createTestContext();

        //В citrus-context.xml настраиваем своего http-клиента restClientReqres
        //Отправка http-запроса http-клиентом restClientReqres
        $(http()
                .client("restClientReqres")
                .send()
                .get("users/"+context.getVariable("userId"))
        );
        //получение ответа http-клиентом restClientReqres и сверка его с эталонным response
        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"email\": \"janet.weaver@reqres.in\",\n" +
                        "        \"first_name\": \"Janet\",\n" +
                        "        \"last_name\": \"Weaver\",\n" +
                        "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}")
        );
    }

    //метод формирует pojo c эталонным response (Control message)
    public User getJsonData() {
        User user = new User();

        DataJson data = new DataJson();
        data.setId(Integer.valueOf(context.getVariable("userId")));
        data.setEmail("janet.weaver@reqres.in");
        data.setFirstName("Janet");
        data.setLastName("Weaver");
        data.setAvatarURL("https://reqres.in/img/faces/2-image.jpg");
        user.setData(data);

        Support support = new Support();
        support.setUrl("https://reqres.in/#support-heading");
        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");
        user.setSupport(support);

        return user;
    }

}
