package services;

import static io.restassured.RestAssured.given;

import annotaitions.Path;
import dto.StoreDTO;
import dto.SuccessResponseDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Path("/v2/store/order")
public class StoreApi extends AbsService<StoreApi, StoreDTO> {

  public StoreDTO createStoreDTO(Long petID) {
    LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
    Instant instant = zonedDateTime.toInstant();
    String dateTime = DateTimeFormatter.ISO_INSTANT.format(instant);

    Integer quantity = 2;
    Long id = 145897L;
    String status = "placed";
    Boolean complete = true;

    return StoreDTO.builder()
        .quantity(quantity)
        .id(id)
        .petId(petID)
        .status(status)
        .shipDate(dateTime)
        .complete(complete)
        .build();
  }

  public SuccessResponseDTO deleteByOrderID(String id) {
    return given()
        .when()
        .delete(getPath() + pathUser(id))
        .then()
        .log().all()
        .extract().as(SuccessResponseDTO.class);
  }
}
