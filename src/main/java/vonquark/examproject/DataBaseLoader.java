package vonquark.examproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    addLaboratory("SpaceLab", "Orbit");
    addLaboratory("EarthLab", "Ground control");

    addInstrument("abc-80");

    User august = mUserRepo.findByFirstName("august");
    User john = mUserRepo.findByFirstName("john");
    Laboratory spaceLab = mLaboratoryRepo.findByName("SpaceLab");
    Laboratory earthLab = mLaboratoryRepo.findByName("EarthLab");
    Instrument abc = mInstrumentRepo.findByHwId("abc-80");

    addLaboratoryToUser(spaceLab, august);
    addLaboratoryToUser(earthLab, august);
    addInstrumentToUser(abc, august);

    addLaboratoryToUser(spaceLab, john);

    addInstrumentToLaboratory(abc, spaceLab);
  }

  private void addInstrumentToLaboratory(Instrument instrument, Laboratory laboratory) {
    laboratory.addInstrument(instrument);
    mLaboratoryRepo.save(laboratory);
  }

  private void addInstrumentToUser(Instrument instrument, User user) {
    user = mUserRepo.findOne(user.getId());
    instrument = mInstrumentRepo.findOne(instrument.getId());
    user.getInstruments().add(instrument);
    mUserRepo.save(user);
  }

  private void addLaboratoryToUser(Laboratory laboratory, User user) {
    user = mUserRepo.findOne(user.getId());
    laboratory = mLaboratoryRepo.findOne(laboratory.getId());
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
