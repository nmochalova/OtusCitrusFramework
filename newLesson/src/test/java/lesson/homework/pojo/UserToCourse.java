package lesson.homework.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserToCourse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("course")
    private String course;

    @JsonProperty("email")
    private String email;

    @JsonProperty("age")
    private int age;
}
