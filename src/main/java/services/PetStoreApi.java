package services;

import annotaitions.Path;
import dto.Category;
import dto.PetDTO;
import dto.Tag;
import java.util.ArrayList;
import java.util.List;

@Path("/v2/pet")
public class PetStoreApi extends AbsService<PetStoreApi, PetDTO> {

  public PetDTO createPetDTO() {
    Integer id = 456789256;
    String name = "Pet";
    List<String> photos = new ArrayList<>();
    String status = "available";
    List<Tag> tags = new ArrayList<>();
    Category category = createCategory();

    {
      tags.add(createTag());
    }

    return PetDTO.builder()
        .id(id)
        .name(name)
        .photoUrls(photos)
        .status(status)
        .tags(tags)
        .category(category)
        .build();
  }

  private Tag createTag() {
    Integer id = 1245;
    String name = "Tag";

    return Tag.builder()
        .id(id)
        .name(name)
        .build();
  }

  private Category createCategory() {
    Integer id = 28;
    String name = "category";

    return Category.builder()
        .id(id)
        .name(name)
        .build();
  }
}
