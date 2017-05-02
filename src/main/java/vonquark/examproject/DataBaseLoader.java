package vonquark.examproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vonquark.examproject.enteties.Instrument;
import vonquark.examproject.enteties.Laboratory;
import vonquark.examproject.enteties.User;
import vonquark.examproject.repositories.InstrumentRepository;
import vonquark.examproject.repositories.LaboratoryRepository;
import vonquark.examproject.repositories.UserRepository;

@Component
public class DataBaseLoader implements ApplicationRunner {

  @Autowired
  private UserRepository mUserRepo;

  @Autowired
  private InstrumentRepository mInstrumentRepo;

  @Autowired
  private LaboratoryRepository mLaboratoryRepo;

  @Override
  public void run(ApplicationArguments applicationArguments) throws Exception {
    addUser("August", "Kobb", "ak@quark.von");
    addUser("John", "Doe", "jd@quark.von");
    addUser("Anna", "Andersson", "aa@quark.von");

    addLaboratory("QuarkLab", "Earth orbit");
    addLaboratory("JungleLab", "Amazon");

    addInstrument("abc123");
  }

  private void addInstrumentToLaboratory(Instrument instrument, Laboratory laboratory) {
    laboratory.getInstruments().add(instrument);
    mLaboratoryRepo.save(laboratory);
  }

  private void addInstrumentToUser(User user, Instrument instrument) {
    user.getInstruments().add(instrument);
    mUserRepo.save(user);
  }

  private void addLaboratoryToUser(User user, Laboratory laboratory) {
    user.getLaboratories().add(laboratory);
    mUserRepo.save(user);
  }

  private void addInstrument(String hardwareId) {
    Instrument instrument = new Instrument(hardwareId);
    mInstrumentRepo.save(instrument);
  }

  private void addLaboratory(String name, String location) {
    Laboratory lab = new Laboratory(name, location);
    mLaboratoryRepo.save(lab);
  }

  private void addUser(String firstname, String lastname, String userName) {
    User user = new User(firstname, lastname, userName);
    mUserRepo.save(user);
  }
}
