package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @ApiModelProperty(value = "This is the first name", required = true)
    @JsonProperty("first_name")
    private String firstName;

    @ApiModelProperty(required = true)
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;

}
