package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Integer id;

  @Version
  private Date updatedAt;

  protected BaseEntity() {
    this.updatedAt = new Date();
    id = null;
  }

  @PreUpdate
  private void onUpdate() {
    updatedAt = new Date();
  }

  public Integer getId() {
    return id;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }
}