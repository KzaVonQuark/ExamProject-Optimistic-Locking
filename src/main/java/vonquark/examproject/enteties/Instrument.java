package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Instrument extends BaseEntity {

  private String hwId;

  @ManyToOne
  @JoinColumn(name = "laboratory_id", insertable = false, updatable = false)
  private Laboratory laboratory;

  @ManyToMany(mappedBy = "instruments", fetch = FetchType.EAGER)
  private Set<User> users;

  public Instrument() {
    super();
  }

  public Instrument(String hwId) {
    super();
    this.hwId = hwId;
  }

  public void addLaboratory(Laboratory laboratory) {
    laboratory.getInstruments().add(this);
    this.setLaboratory(laboratory);
  }

  public void removeLaboratory(Laboratory laboratory) {
    laboratory.getInstruments().remove(this);
    this.setLaboratory(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Instrument that = (Instrument) o;

    return hwId != null ? hwId.equals(that.hwId) : that.hwId == null;
  }

  @Override
  public int hashCode() {
    return hwId != null ? hwId.hashCode() : 0;
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
