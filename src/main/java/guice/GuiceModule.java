package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import dto.PetDTO;
import dto.StoreDTO;
import dto.UserDTO;
import services.PetStoreApi;
import services.StoreApi;
import services.UserApi;

public class GuiceModule extends AbstractModule {
  @Singleton
  @Provides
  public UserApi getUserApi() {
    return new UserApi();
  }

  @Singleton
  @Provides
  public PetStoreApi getPetStoreApi() {
    return new PetStoreApi();
  }

  @Singleton
  @Provides
  public StoreApi getStoreApi() {
    return new StoreApi();
  }

  @Singleton
  @Provides
  public UserDTO getUserDTO(UserApi userApi) {
    return userApi.createUserDTO();
  }

  @Singleton
  @Provides
  public PetDTO getPetDTO(PetStoreApi petStoreApi) {
    return petStoreApi.createPetDTO();
  }

  @Singleton
  @Provides
  public StoreDTO getStoreDTO(StoreApi api, PetDTO petDTO) {
    return api.createStoreDTO(Long.valueOf(petDTO.getId()));
  }
}