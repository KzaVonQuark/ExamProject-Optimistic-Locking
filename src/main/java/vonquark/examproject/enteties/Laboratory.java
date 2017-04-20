package vonquark.examproject.enteties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Laboratory extends BaseEntity {

  private String name;
  private String location;
  private Date updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<User> users;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn
  private Set<Instrument> instruments;

  public Laboratory() {
    super();
  }

  public Laboratory(String name, String location) {
    this.name = name;
    this.location = location;
    this.updatedAt = new Date();
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

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
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
