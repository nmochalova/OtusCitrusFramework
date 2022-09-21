package lesson.behavior;

import com.consol.citrus.TestActionRunner;
import com.consol.citrus.TestBehavior;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import lesson.pojo.CreateUserResponse;
import org.springframework.http.HttpStatus;
import com.consol.citrus.context.TestContext;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonSupport.json;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

/**
 * ApplyBehavior (для Citrus 3.2.1)
 * Поведенческие классы, в котором будем эмулировать некие повторяющиеся действия.
 * Пример: создание пользователя, как предусловия к какому-то тесту.
 * Behavior можно использовать из любого места теста, в т.ч. между шагами.
 */
public class CreateUserBehavior implements TestBehavior {
    private TestContext context;
    public String name;
    public String job;

    public CreateUserBehavior(String name, String job, TestContext context) {
        this.context = context;
        this.name = name;
        this.job = job;
    }

    @Override
    public void apply(TestActionRunner testActionRunner) {
        testActionRunner.run(http()
                .client("restClientReqres")
                .send()
                .post("users")
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}")
        );

        testActionRunner.run(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getResponseData(name,job), "objectMapper"))
                .validate(json()
                        .ignore("$.createdAt"))
                .extract(fromBody()
                        .expression("$.id","currentId")  //извлекаем id
                        .expression("$.createdAt","createdAt"))
        );

        echo("Behavior DONE! Created user with: currentId = ${currentId} and createdAt = ${createdAt}");

        context.setVariable("currentId","${currentId}"); //сетим id в контекст
    }

    private CreateUserResponse getResponseData(String name, String job) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@"); //citrus validation matcher - проверка, что id есть number
        createUserResponse.setCreatedAt("unknown");

        return createUserResponse;
    }
}
