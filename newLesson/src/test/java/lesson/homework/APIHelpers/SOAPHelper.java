package lesson.homework.APIHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import lesson.features.CustomMarshaller;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

public class SOAPHelper extends TestNGCitrusSupport {
    public TestContext context;

    @Test(description = "SOAP тест: число 20", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        CustomMarshaller<Class<NumberToDollars>> ptxRq = new CustomMarshaller<>(); //ptxRq - pojo to xml request
        CustomMarshaller<Class<NumberToDollarsResponse>> ptxRs = new CustomMarshaller<>(); //ptxRq - pojo to xml response

        //Отправка soap-сообщения с использованием самописного маршелера CustomMarshaller
        run(soap()
                .client("soapClient")
                .send()
                .message()
                .body(ptxRq.convert(NumberToDollars.class, getNumberToDollarsRequest(),
                        "http://www.dataaccess.com/webservicesserver/",
                        "NumberToDollars"))
        );

        //Получение soap-ответа и сравнение его с эталонным xml
        run(soap()
                .client("soapClient")
                .receive()
                .message()
                .body(ptxRs.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(),
                        "http://www.dataaccess.com/webservicesserver/",
                        "NumberToDollarsResponse"))
        );
    }

    //Метод который возвращает эталонный pojo-объект NumberToDollars для сравнения c полем Num=15
    //Для генерации pojo на основе wsdl необходимо в pom.xml подключить плагин cxf-codegen-plugin
    //и далее из терминала сгенерить pojo командой: mvn generate-source
    public NumberToDollars getNumberToDollarsRequest() {
        NumberToDollars numberToDollars = new NumberToDollars();
        numberToDollars.setDNum(new BigDecimal("20"));
        return numberToDollars;
    }

    //Метод который возвращает эталонный pojo-объект NumberToDollarsResponse для сравнения c ответом "fifteen dollars"
    public NumberToDollarsResponse getNumberToDollarsResponse() {
        NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
        numberToDollarsResponse.setNumberToDollarsResult("twenty dollars");
        return numberToDollarsResponse;
    }
}
