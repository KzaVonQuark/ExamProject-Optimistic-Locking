package vonquark.examproject;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import vonquark.examproject.enteties.Instrument;
import vonquark.examproject.enteties.Laboratory;
import vonquark.examproject.enteties.User;
import vonquark.examproject.repositories.InstrumentRepository;
import vonquark.examproject.repositories.LaboratoryRepository;
import vonquark.examproject.repositories.UserRepository;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LockTest {

  @Autowired
  private UserRepository mUserRepo;

  @Autowired
  private InstrumentRepository mInstrumentRepo;

  @Autowired
  private LaboratoryRepository mLaboratoryRepo;

  private User august;
  private User john;
  private Laboratory spaceLab;
  private Laboratory earthLab;
  private Instrument abc;
  private static final Integer AUGUST_ID = 1;
  private static final Integer JOHN_ID = 2;
  private static final Integer ABC_ID = 1;
  private static final Integer SPACELAB_ID = 1;
  private static final Integer EARTHLAB_ID = 2;

  private boolean lockInPlace;

  private static SessionFactory factory;

  @Before
  public void setUp() throws Exception {
    august = mUserRepo.findOne(AUGUST_ID);
    john = mUserRepo.findOne(JOHN_ID);
    lockInPlace = false;
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testUpdateName() throws Exception {
    User augustTheSecond = mUserRepo.findOne(AUGUST_ID);
    sleepTwoSecond();

    august.setFirstName("Roland");
    saveUser(august);

    augustTheSecond.setLastName("The_second");
    saveUser(augustTheSecond);

    User updatedAugust = mUserRepo.findOne(AUGUST_ID);
    assertTrue("save 'augustTheSecond' should cause lock exeption", lockInPlace);
    assertEquals("Lock should prevent First name being overwritten","Roland", updatedAugust.getFirstName());
    assertEquals("Lock should prevent update of Last name to 'The_second","Kobb", updatedAugust.getLastName());
  }

  @Test
  public void testManyToManyUpdate() throws Exception {
    sleepTwoSecond();
    User roland = mUserRepo.findOne(AUGUST_ID);

    spaceLab = mLaboratoryRepo.findOne(SPACELAB_ID);
    spaceLab.getUsers().remove(mUserRepo.findOne(AUGUST_ID));
    saveLaboratory(spaceLab);

    roland.setLastName("Nilsson");
    saveUser(roland);

    User updatedAugust = mUserRepo.findOne(AUGUST_ID);

//    assertTrue("save 'roland' should cause lock exeption", lockInPlace);

    assertEquals("August", updatedAugust.getFirstName());
    assertEquals("Kobb", updatedAugust.getLastName());
    assertEquals(1, updatedAugust.getLaboratories().size());
  }

  @Test
  public void testLaboratoryAddAndRemoveInstrument() throws Exception {
    spaceLab = mLaboratoryRepo.findOne(SPACELAB_ID);
    Laboratory spaceCopy = mLaboratoryRepo.findOne(SPACELAB_ID);

    abc = mInstrumentRepo.findOne(ABC_ID);
    Instrument newInstrument = new Instrument("new123");
    mInstrumentRepo.save(newInstrument);

    addInstrumentToLab(mInstrumentRepo.findByHwId("new123"), spaceCopy);
    sleepTwoSecond();
    removeInstrumentFromLab(abc, spaceLab);

    spaceLab = mLaboratoryRepo.findOne(SPACELAB_ID);

    assertTrue(lockInPlace);
  }


  private void addInstrumentToLab(Instrument instrument, Laboratory laboratory) {
      laboratory.addInstrument(instrument);
      saveLaboratory(laboratory);
  }

  private void removeInstrumentFromLab(Instrument instrument, Laboratory laboratory) {
    laboratory.removeInstrument(instrument);
    saveLaboratory(laboratory);
  }

  private void saveUser(User user) {
    try {
      mUserRepo.save(user);
    }catch (ObjectOptimisticLockingFailureException e) {
      lockInPlace = true;
    }
  }

  private void saveLaboratory(Laboratory laboratory) {
    try {
      mLaboratoryRepo.save(laboratory);
    } catch (ObjectOptimisticLockingFailureException e) {
      lockInPlace = true;
    }
  }

  private void sleepTwoSecond() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void resetUsers() {
    august = mUserRepo.findOne(AUGUST_ID);
    august.setFirstName("August");
    august.setLastName("Kobb");
    august.getLaboratories().clear();
    august.getLaboratories().add(spaceLab);
    august.getLaboratories().add(earthLab);
    august.getInstruments().clear();
    august.getInstruments().add(abc);
    mUserRepo.save(august);

    john = mUserRepo.findOne(JOHN_ID);
    john.setFirstName("John");
    john.setLastName("Doe");
    john.getLaboratories().clear();
    john.getLaboratories().add(spaceLab);
    john.getInstruments().clear();
    mUserRepo.save(john);
  }

  private void resetLaboratories() {
    spaceLab = mLaboratoryRepo.findOne(SPACELAB_ID);
    spaceLab.setName("QuarkLab");
    spaceLab.setLocation("Earth Orbit");
    spaceLab.getInstruments().clear();
    spaceLab.getInstruments().add(abc);

    earthLab = mLaboratoryRepo.findOne(EARTHLAB_ID);
    earthLab.setName("JungleLab");
    earthLab.setLocation("Amazon");
    earthLab.getInstruments().clear();
    mLaboratoryRepo.save(earthLab);
  }

  private void resetInstruments() {
    abc = mInstrumentRepo.findOne(ABC_ID);
    abc.setHwId("abc123");
  }
}