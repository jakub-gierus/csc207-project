import Exceptions.IncorrectUserNameOrPasswordException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogin_LogOut() {
        try {
            UserRepository ur = new UserRepository();
            CreateUserUseCase cuuc = new CreateUserUseCase(ur);
            cuuc.createUser("Basic1", "Basic1", false);
            cuuc.createUser("Admin1", "Admin1", true);

            AdminUser u1 = (AdminUser) ur.getByUsername("Admin1").orElseThrow(IncorrectUserNameOrPasswordException::new);
            BasicUser u2 = (BasicUser) ur.getByUsername("Basic1").orElseThrow(IncorrectUserNameOrPasswordException::new);

            LogInUseCase luc = new LogInUseCase(ur);
            luc.logIn("Admin1", "Admin1");
            luc.logIn("Basic1", "Basic1");
            assertTrue(u1.getIsLoggedIn());
            assertTrue(u2.getIsLoggedIn());

            UserFacade uf1 = new UserFacade(ur, u1);
            UserFacade uf2 = new UserFacade(ur, u2);

            uf1.logOut();
            uf2.logOut();
            assertFalse(u1.getIsLoggedIn());
            assertFalse(u2.getIsLoggedIn());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBan() {
        try {
            UserRepository ur = new UserRepository();
            CreateUserUseCase cuuc = new CreateUserUseCase(ur);
            cuuc.createUser("basic1", "basic1", false);
            cuuc.createUser("admin1", "admin1", true);

            AdminUser u1 = (AdminUser) ur.getByUsername("admin1").orElseThrow(IncorrectUserNameOrPasswordException::new);
            BasicUser u2 = (BasicUser) ur.getByUsername("basic1").orElseThrow(IncorrectUserNameOrPasswordException::new);

            LogInUseCase luc = new LogInUseCase(ur);
            luc.logIn("admin1", "admin1");
            luc.logIn("basic1", "basic1");

            AdminFacade af1 = new AdminFacade(ur, u1);
            af1.banUser("basic1", LocalDateTime.now().plusMinutes(2));
            assertFalse(u2.getIsLoggedIn());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void testUsernameChange() {
        try {
            usecases.user.LogInUseCase uuc = new usecases.user.LogInUseCase();
            assertFalse(uuc.setUsername("Admin1", "Basic1"));
            assertTrue(uuc.setUsername("admin1", "Admin1"));
            List<String> lst = new ArrayList<>();
            lst.add("admin1");
            lst.add("Basic1");
            assertEquals("UserManager's usernames is not correctly updated after username change",
                    lst, UserManager.getUsernames());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*@Test
    public void testLogin_After_Username_Change() {
        entity.user.AdminUser u1 = (entity.user.AdminUser) UserManager.getUser("Admin1");
        entity.user.BasicUser u2 = (entity.user.BasicUser) UserManager.getUser("Basic1");
        try {
            usecases.user.LogInUseCase uuc = new usecases.user.LogInUseCase();
            assertTrue(uuc.logIn("Admin1", "Admin1"));
            assertTrue(uuc.logIn("Basic1", "Basic1"));
            assertTrue(u1.getIsLoggedIn());
            assertTrue(u2.getIsLoggedIn());

            assertTrue(uuc.logOut("Basic1"));
            assertTrue(uuc.setUsername("basic1", "Basic1"));
            assertTrue(u2.setPassword("basic1"));

            assertTrue("UserManager usernames not updated correctly",
                    uuc.logIn( "basic1", "basic1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
