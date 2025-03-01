package tests;

import com.google.inject.Inject;
import dto.SuccessResponseDTO;
import dto.UserDTO;
import extentions.Extension;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserApi;

@ExtendWith(Extension.class)
public class UserTest {
  @Inject
  private UserDTO dto;
  @Inject
  private UserApi userApi;


  /**
   * Создание user методом post
   * Получение user методом get
   * из библиотеки rest-assured
   */
  @Test
  public void testSuccessCreateUser() {
    SuccessResponseDTO successDTO = userApi.createUserPostMethod(dto);
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto.getId(), Long.valueOf(successDTO.getMessage()));

    UserDTO userDTO;
    int attemtps = 0;
    do {
      userDTO = userApi.requestByMethod(userApi.pathUser(dto.getUsername()), Method.GET, UserDTO.class);
      attemtps++;
    }
    while (userDTO == null && attemtps < 5);
    Assertions.assertNotNull(userDTO);
    Assertions.assertEquals(dto, userDTO);
  }
}