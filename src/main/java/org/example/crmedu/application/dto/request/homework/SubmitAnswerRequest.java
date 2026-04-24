package org.example.crmedu.application.dto.request.homework;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SubmitAnswerRequest {

  @NotBlank(message = "Answer must not be blank")
  private String answer;

  private List<String> attachmentUrls;
}