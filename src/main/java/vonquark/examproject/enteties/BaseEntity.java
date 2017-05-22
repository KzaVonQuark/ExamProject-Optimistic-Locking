package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Integer id;

  @Version
  private Date updatedAt;

  private String updatedBy;

  protected BaseEntity() {
    this.updatedAt = new Date();
    id = null;
  }

//  @PreUpdate
//  private void onUpdate() {
//    updatedAt = new Date();
//  }

  public Integer getId() {
    return id;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}