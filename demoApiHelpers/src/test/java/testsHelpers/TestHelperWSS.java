package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;
import pojo.wss.ErrorResponse;
import pojo.wss.WssError;

//WebSocket Helper. Работаем с открытым сервисом по криптовалютам: wss://stream.binance.com:9443/stream?streams=${tickerLower}@depth
//работает в асинхронном режиме
public class TestHelperWSS extends TestNGCitrusTestRunner {
    public TestContext context;

    @Test(description = "WSS Helper", invocationCount = 1 ) //invocationCount - количество запусков
    //Если поставить invocationCount=3, то можно увидеть как некоторые тесты зафейлятся, потому что придут данные от сервиса, а мы ожидаем ошибку
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();
        context.setVariable("payload","none");

        //отправить запрос на WebSocket service (на urlWSS)
        send(action ->action
                .endpoint("wssHelperClient"));

        //получить ответ от WebSocket service (ответ м.б.в разных форматах: json, xml, string и пр.)
        receive(action ->action
                .endpoint("wssHelperClient")
                .messageType(MessageType.JSON)
                .extractFromPayload("$.*","payload")
                .payload(getJdonDataResponseWSS(),"objectMapper"));

        echo("############: ${payload}");
    }

    //Эталонный ответ, с которым сравниваем. В этом случае ожидаем ошибку 3 + текст. Однако тест иногда может вернуть данные.
    private ErrorResponse getJdonDataResponseWSS() {
        ErrorResponse errorResponse = new ErrorResponse();

        WssError error = new WssError();
        error.setCode(3);
        error.setMsg("Invalid JSON: EOF while parsing a value at line 1 column 0");
        errorResponse.setError(error);

        return errorResponse;
    }
}
