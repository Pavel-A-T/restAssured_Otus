package tests;

import com.google.inject.Inject;
import dto.PetDTO;
import dto.StoreDTO;
import dto.SuccessResponseDTO;
import dto.UserDTO;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extentions.Extension;
import services.PetStoreApi;
import services.StoreApi;
import services.UserApi;
import specifications.Specification;

@ExtendWith(Extension.class)
public class RestAssuredAPITests {
  @Inject
  private UserApi userApi;
  @Inject
  private PetStoreApi petStoreApi;
  @Inject
  private StoreApi storeApi;


  @BeforeAll
  public static void start() {
    Specification.installSpecification(Specification.requestSpecification("https://petstore.swagger.io/v2"), Specification.responseSpecification(200));
  }

  /**
   * Создание user методом post
   * Получение user методом get
   * Удаление user методом delete
   * из библиотеки rest-assured
   */
  @Test
  public void successCreateUser() {
    UserDTO dto = userApi.createUserDTO();
    SuccessResponseDTO successDTO = userApi.createUserPostMethod(dto);

    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto.getId(), Long.valueOf(successDTO.getMessage()));

    UserDTO userDTO = userApi.requestByMethod(userApi.pathUser(dto.getUsername()), Method.GET, UserDTO.class);
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto, userDTO);

    successDTO = userApi.deleteUserByUsername(dto.getUsername());
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(dto.getUsername(), successDTO.getMessage());
  }

  /**
   * Создание pet методом post
   * Также проверка работы методов
   * post, get, delete для Store
   */
  @Test
  public void checkPetStoreMethods() {
    PetDTO pet = petStoreApi.createPetDTO();
    PetDTO petDTOFromMethod = petStoreApi.createByMethodPost(pet, PetDTO.class);
    Assertions.assertNotNull(petDTOFromMethod);
    Assertions.assertEquals(pet, petDTOFromMethod);

    StoreDTO storeDTO = storeApi.createStoreDTO(Long.valueOf(pet.getId()));
    StoreDTO storeDTOFromMethod = storeApi.createByMethodPost(storeDTO, StoreDTO.class);
    Assertions.assertNotNull(storeDTOFromMethod);
    Assertions.assertEquals(storeDTO.getId(), storeDTOFromMethod.getId());
    Assertions.assertEquals(storeDTO.getPetId(), storeDTOFromMethod.getPetId());

    StoreDTO dto = storeApi.requestByMethod(storeApi.pathUser(String.valueOf(storeDTO.getId())), Method.GET, StoreDTO.class);
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(storeDTO.getId(), dto.getId());
    Assertions.assertEquals(storeDTO.getPetId(), dto.getPetId());

    SuccessResponseDTO successDTO = storeApi.deleteByOrderID(String.valueOf(storeDTO.getId()));
    Assertions.assertNotNull(successDTO);
    Assertions.assertEquals(String.valueOf(storeDTO.getId()), successDTO.getMessage());
  }
}
