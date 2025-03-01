package services;

import static io.restassured.RestAssured.given;

import annotaitions.Path;
import io.restassured.http.Method;
import io.restassured.response.Response;

public abstract class AbsService<T, V> {
  public String pathUser(String pathVar) {
    return "/" + pathVar;
  }

  protected String getPath() {
    Class<? extends AbsService> clazz = getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = clazz.getDeclaredAnnotation(Path.class);
      return path.value().startsWith("/") ? path.value() : "/" + path.value();
    }
    else {
      return "";
    }
  }

  public V createByMethodPost(V dto, Class<V> dtoClass) {
    return given()
        .body(dto)
        .when()
        .post(getPath())
        .then()
        .log().all()
        .extract().as(dtoClass);
  }

  public <V> V requestByMethod(String path, Method method, Class<V> responseClass) {
    Response response = given()
        .relaxedHTTPSValidation()
        .when()
        .request(method, getPath() + path)
        .then()
        .log().all()
        .extract()
        .response();
    if (response.getStatusCode() == 200) {
      return response.as(responseClass);
    } else {
      return null;
    }
  }
}