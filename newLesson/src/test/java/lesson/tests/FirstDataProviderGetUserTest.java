package lesson.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstDataProviderGetUserTest extends TestNGCitrusSupport {
    public TestContext context;

    //Параметризованный тест. В массиве @DataProvider забиты ожидаемые результаты
    @DataProvider(name = "dataProvider")
    public Object[][] getDataProvider() {
        return new Object[][] {
                new Object[]{"1","George","Bluth"},
                new Object[]{"2","Janet","Weaver"},
                new Object[]{"3","Emma", "Wong"},
                new Object[]{"4","Eve", "Holt"},
                new Object[]{"5","Charles", "Morris"},
                new Object[]{"6","Tracey", "Ramos"},
                new Object[]{"7","Michael", "Lawson"},
                new Object[]{"8","Lindsay", "Ferguson"},
                new Object[]{"9","Tobias", "Funke"},
                new Object[]{"10","Byron", "Fields"},
                new Object[]{"11","George", "Edwards"},
                new Object[]{"12","Rachel", "Howell"}
        };
    }

    @Test(description = "Получение информации о пользователе", dataProvider = "dataProvider")
    @CitrusTest
    public void getTestAction(String id, String name, String surname){
        this.context = citrus.getCitrusContext().createTestContext();

        //отправляем запрос по id из dataProvider
        $(http()
                .client("restClientReqres")
                .send()
                .get("users/" + id)
        );

        //получаем ответ и валидируем с ожидаемым результатом также из dataProvider
        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()                            //ожидаемый результат
                        .expression("$.data.id",id)
                        .expression("$.data.first_name",name)
                        .expression("$.data.last_name",surname)
                )
        );
    }

//    public User getJsonData(String name, String surname, Integer id) {
//        User user = new User();
//
//        DataJson data = new DataJson();
//        data.setId(id);
//        data.setEmail("janet.weaver@reqres.in");
//        data.setFirstName(name);
//        data.setLastName(surname);
//        data.setAvatarURL("https://reqres.in/img/faces/2-image.jpg");
//        user.setData(data);
//
//        Support support = new Support();
//        support.setUrl("https://reqres.in/#support-heading");
//        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");
//        user.setSupport(support);
//
//        return user;
//    }
}
