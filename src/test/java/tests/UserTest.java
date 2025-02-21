package tests;

import com.google.inject.Inject;
import dto.SuccessResponseDTO;
import dto.UserDTO;
import extentions.Extension;
import io.restassured.http.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.UserApi;

@ExtendWith(Extension.class)
public class UserTest {
  private UserDTO dto;
  private boolean isCreateUser = false;
  @Inject
  private UserApi userApi;

  @AfterEach
  public void tearDown() {
    if (dto != null && isCreateUser) {
      SuccessResponseDTO successDTO = userApi.deleteUserByUsername(dto.getUsername());
      Assertions.assertNotNull(successDTO);
      Assertions.assertEquals(dto.getUsername(), successDTO.getMessage());
    }
  }


  /**
   * Создание user методом post
   * Получение user методом get
   * из библиотеки rest-assured
   */
  @Test
  public void testSuccessCreateUser() {
    dto = userApi.createUserDTO();
    SuccessResponseDTO successDTO = userApi.createUserPostMethod(dto);
    isCreateUser = true;

    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto.getId(), Long.valueOf(successDTO.getMessage()));

    UserDTO userDTO = userApi.requestByMethod(userApi.pathUser(dto.getUsername()), Method.GET, UserDTO.class);
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto, userDTO);
  }
}