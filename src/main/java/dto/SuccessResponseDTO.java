package dto;

import lombok.*;

@Data
@NoArgsConstructor
public class SuccessResponseDTO {
  private Integer code;
  private String type;
  private String message;
}
