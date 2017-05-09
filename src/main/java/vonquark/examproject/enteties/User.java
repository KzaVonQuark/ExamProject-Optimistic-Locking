package vonquark.examproject.enteties;

import org.springframework.data.jpa.repository.Lock;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User extends BaseEntity {

  private String  firstName;
  private String lastName;
  private String userName;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Instrument> instruments;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Laboratory> laboratories;

  public User() {
    super();
  }

  public User(String firstName, String lastName, String userName) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Set<Instrument> getInstruments() {
    return instruments;
  }

  public void setInstruments(Set<Instrument> instruments) {
    this.instruments = instruments;
  }

  public Set<Laboratory> getLaboratories() {
    return laboratories;
  }

  public void setLaboratories(Set<Laboratory> laboratories) {
    this.laboratories = laboratories;
  }
}