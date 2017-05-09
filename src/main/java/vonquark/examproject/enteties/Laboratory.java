package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Laboratory extends BaseEntity {

  private String name;
  private String location;

  @ManyToMany(mappedBy = "laboratories")
  private Set<User> users;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratory", orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<Instrument> instruments;

  public Laboratory() {
    super();
  }

  public Laboratory(String name, String location) {
    super();
    this.name = name;
    this.location = location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Laboratory that = (Laboratory) o;

    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return location != null ? location.equals(that.location) : that.location == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (location != null ? location.hashCode() : 0);
    return result;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Instrument> getInstruments() {
    return instruments;
  }

  public void setInstruments(Set<Instrument> instruments) {
    this.instruments = instruments;
  }
}
