package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;

public class BaseEntityListener {

  @PrePersist
  @PreRemove
  @PreUpdate
  public void beforeAnyOperation(BaseEntity entity) {
      entity.setUpdatedAt(new Date());
      System.out.println(entity.getClass() + " - " + entity.getUpdatedBy());
    }
}