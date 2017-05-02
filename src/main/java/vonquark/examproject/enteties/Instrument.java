package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Instrument extends BaseEntity {

  private String hwId;
  private Date updatedAt;

  @ManyToOne
  private Laboratory laboratory;

  @ManyToMany(mappedBy = "instruments")
  private Set<User> users;

  public Instrument() {
    super();
  }

  public Instrument(String hwId) {
    this.hwId = hwId;
    this.updatedAt = new Date();
  }

  public String getHwId() {
    return hwId;
  }

  public void setHwId(String hwId) {
    this.hwId = hwId;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Laboratory getLaboratory() {
    return laboratory;
  }

  public void setLaboratory(Laboratory laboratory) {
    this.laboratory = laboratory;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
