package services;

import static io.restassured.RestAssured.given;

import annotaitions.Path;
import dto.UserDTO;
import dto.SuccessResponseDTO;

@Path("/user")
public class UserApi extends AbsService<UserApi, UserDTO> {

  public UserDTO createUserDTO() {
    Long id = 125463L;
    String email = "pr@ya.ru";
    String firstName = "Alex";
    Integer userStatus = 1237;
    String phone = "+7458956425";
    String lastName = "Pirogov";
    String password = "1234";
    String username = "pirogAS";

    return UserDTO.builder()
        .id(id)
        .email(email)
        .userStatus(userStatus)
        .firstName(firstName)
        .phone(phone)
        .lastName(lastName)
        .password(password)
        .username(username)
        .build();
  }

  public SuccessResponseDTO createUserPostMethod(UserDTO dto) {
    return given()
        .body(dto)
        .when()
        .post(getPath())
        .then()
        .log()
        .all()
        .extract().as(SuccessResponseDTO.class);
  }

  public SuccessResponseDTO deleteUserByUsername(String username) {
    return given()
        .when()
        .delete(getPath() + pathUser(username))
        .then()
        .log().all()
        .extract().as(SuccessResponseDTO.class);
  }
}
