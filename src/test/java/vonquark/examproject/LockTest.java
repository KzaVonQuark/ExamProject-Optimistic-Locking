package vonquark.examproject;

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
  private Laboratory quarkLab;
  private Laboratory jungleLab;
  private Instrument abc;
  private static final Integer AUGUST_ID = 1;
  private static final Integer JOHN_ID = 2;
  private static final Integer ABC_ID = 1;
  private static final Integer QUARKLAB_ID = 1;
  private static final Integer JUNGLELAB_ID = 1;

  private boolean lockInPlace;

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
    sleepOneSecond();
    User augustTheSecond = mUserRepo.findOne(AUGUST_ID);

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
  public void testUpdateLaboratoriesAndName() throws Exception {
    sleepOneSecond();
    User roland = mUserRepo.findOne(AUGUST_ID);
    Laboratory newLab = new Laboratory("New Lab", "Home");
    newLab = mLaboratoryRepo.save(newLab);
    august.getLaboratories().add(newLab);
    saveUser(august);

    roland.setFirstName("Roland");
    roland.setLastName("Nilsson");
    saveUser(roland);

    User updatedAugust = mUserRepo.findOne(AUGUST_ID);
    System.out.println("August " + august.getUpdatedAt());
    System.out.println("Roland " + roland.getUpdatedAt());
    System.out.println("Update " + updatedAugust.getUpdatedAt());

    assertTrue("save 'roland' should cause lock exeption", lockInPlace);

    assertEquals("August", updatedAugust.getFirstName());
    assertEquals("Kobb", updatedAugust.getLastName());
    assertEquals(1, updatedAugust.getLaboratories().size());
  }

  @Test
  public void testUpdateLaboratoryInstrument() throws Exception {
    quarkLab = mLaboratoryRepo.findOne(QUARKLAB_ID);
    sleepOneSecond();
    Laboratory newQuarkLab = mLaboratoryRepo.findOne(QUARKLAB_ID);
    abc = mInstrumentRepo.findOne(ABC_ID);

    quarkLab.getInstruments().remove(abc);
    saveLaboratory(quarkLab);

    newQuarkLab.setName("newQuarkLab");
    saveLaboratory(newQuarkLab);

    assertTrue(lockInPlace);
  }

  private void sleepOneSecond() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
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

    private void resetUsers() {
      august = mUserRepo.findOne(AUGUST_ID);
      august.setFirstName("August");
      august.setLastName("Kobb");
      august.getLaboratories().clear();
      august.getLaboratories().add(quarkLab);
      august.getLaboratories().add(jungleLab);
      august.getInstruments().clear();
      august.getInstruments().add(abc);
      mUserRepo.save(august);

      john = mUserRepo.findOne(JOHN_ID);
    john.setFirstName("John");
    john.setLastName("Doe");
    john.getLaboratories().clear();
    john.getLaboratories().add(quarkLab);
    john.getInstruments().clear();
    mUserRepo.save(john);
  }

  private void resetLaboratories() {
    quarkLab = mLaboratoryRepo.findOne(QUARKLAB_ID);
    quarkLab.setName("QuarkLab");
    quarkLab.setLocation("Earth Orbit");
    quarkLab.getInstruments().clear();
    quarkLab.getInstruments().add(abc);

    jungleLab = mLaboratoryRepo.findOne(JUNGLELAB_ID);
    jungleLab.setName("JungleLab");
    jungleLab.setLocation("Amazon");
    jungleLab.getInstruments().clear();
    mLaboratoryRepo.save(jungleLab);
  }

  private void resetInstruments() {
    abc = mInstrumentRepo.findOne(ABC_ID);
    abc.setHwId("abc123");
  }
}