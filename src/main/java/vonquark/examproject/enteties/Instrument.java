package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Instrument extends BaseEntity {

  private String hwId;

  @ManyToOne
  private Laboratory laboratory;

  @ManyToMany(mappedBy = "instruments")
  private Set<User> users;

  public Instrument() {
    super();
  }

  public Instrument(String hwId) {
    super();
    this.hwId = hwId;
  }

  public String getHwId() {
    return hwId;
  }

  public void setHwId(String hwId) {
    this.hwId = hwId;
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
