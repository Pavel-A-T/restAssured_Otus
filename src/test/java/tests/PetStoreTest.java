package tests;

import com.google.inject.Inject;
import dto.PetDTO;
import dto.StoreDTO;
import dto.SuccessResponseDTO;
import extentions.Extension;
import io.restassured.http.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import services.PetStoreApi;
import services.StoreApi;

@ExtendWith(Extension.class)
public class PetStoreTest {
  private StoreDTO storeDTO;
  private boolean isCreateStore = false;
  @Inject
  private PetStoreApi petStoreApi;
  @Inject
  private StoreApi storeApi;

  @AfterEach
  public void tearDown() {
    if (storeDTO != null && isCreateStore) {
      SuccessResponseDTO successDTO = storeApi.deleteByOrderID(String.valueOf(storeDTO.getId()));
      Assertions.assertNotNull(successDTO);
      Assertions.assertEquals(String.valueOf(storeDTO.getId()), successDTO.getMessage());
    }
  }

  /**
   * Создание pet методом post
   * Также проверка работы методов
   * post, get для Store
   */
  @Test
  public void checkPetStoreMethods() {
    PetDTO pet = petStoreApi.createPetDTO();
    PetDTO petDTOFromMethod = petStoreApi.createByMethodPost(pet, PetDTO.class);
    Assertions.assertNotNull(petDTOFromMethod);
    Assertions.assertEquals(pet, petDTOFromMethod);
    isCreateStore = true;

    storeDTO = storeApi.createStoreDTO(Long.valueOf(pet.getId()));
    StoreDTO storeDTOFromMethod = storeApi.createByMethodPost(storeDTO, StoreDTO.class);
    Assertions.assertNotNull(storeDTOFromMethod);
    Assertions.assertEquals(storeDTO.getId(), storeDTOFromMethod.getId());
    Assertions.assertEquals(storeDTO.getPetId(), storeDTOFromMethod.getPetId());

    StoreDTO dto = storeApi.requestByMethod(storeApi.pathUser(String.valueOf(storeDTO.getId())), Method.GET, StoreDTO.class);
    Assertions.assertNotNull(dto);
    Assertions.assertEquals(storeDTO.getId(), dto.getId());
    Assertions.assertEquals(storeDTO.getPetId(), dto.getPetId());
  }
}
