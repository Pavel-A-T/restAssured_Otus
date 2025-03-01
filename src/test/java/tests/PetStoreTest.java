package tests;

import com.google.inject.Inject;
import dto.PetDTO;
import dto.StoreDTO;
import dto.UserDTO;
import extentions.Extension;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.PetStoreApi;
import services.StoreApi;

@ExtendWith(Extension.class)
public class PetStoreTest {
  @Inject
  PetDTO pet;
  @Inject
  private StoreDTO storeDTO;
  @Inject
  private PetStoreApi petStoreApi;
  @Inject
  private StoreApi storeApi;

  /**
   * Создание pet методом post
   * Также проверка работы методов
   * post, get для Store
   */
  @Test
  public void checkPetStoreMethods() {
    PetDTO petDTOFromMethod = petStoreApi.createByMethodPost(pet, PetDTO.class);
    Assertions.assertNotNull(petDTOFromMethod);
    Assertions.assertEquals(pet, petDTOFromMethod);

    StoreDTO storeDTOFromMethod = storeApi.createByMethodPost(storeDTO, StoreDTO.class);
    Assertions.assertNotNull(storeDTOFromMethod);
    Assertions.assertEquals(storeDTO.getId(), storeDTOFromMethod.getId());
    Assertions.assertEquals(storeDTO.getPetId(), storeDTOFromMethod.getPetId());

    StoreDTO dto;
    int attemtps = 0;
    do {
      dto = storeApi.requestByMethod(storeApi.pathUser(String.valueOf(storeDTO.getId())), Method.GET, StoreDTO.class);
      attemtps++;
    }
    while (dto == null && attemtps < 5);
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(storeDTO.getId(), dto.getId());
    Assertions.assertEquals(storeDTO.getPetId(), dto.getPetId());
  }
}
