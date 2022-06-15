import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    UserManager.addBasicUser("Basic1", "Basic1");
    UserManager.addAdminUser("Admin1", "Admin1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUsernameChange() {
        try {
            UserUseClass uuc = new UserUseClass();
            assertFalse(uuc.setUsername("Admin1", "Basic1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin_LogOut() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u1.getIsLoggedIn());
            assertTrue(u2.getIsLoggedIn());

            assertTrue(uuc.logOut("Admin1"));
            assertTrue(uuc.logOut("Basic1"));
            assertFalse(u1.getIsLoggedIn());
            assertFalse(u2.getIsLoggedIn());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBan() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));

            assertTrue(u1.banUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertTrue(u2.getIsBanned());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnBan() {
        AdminUser u1 = (AdminUser) UserManager.getUser("Admin1");
        BasicUser u2 = (BasicUser) UserManager.getUser("Basic1");
        try {
            UserUseClass uuc = new UserUseClass();
            assertTrue(uuc.login("Admin1", "Admin1"));
            assertTrue(uuc.login("Basic1", "Basic1"));

            assertTrue(u1.banUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertTrue(u2.getIsBanned());

            assertTrue(u1.unBanUser("Basic1"));
            assertFalse(u2.getIsLoggedIn());
            assertFalse(u2.getIsBanned());
            assertTrue(uuc.login("Basic1", "Basic1"));
            assertTrue(u2.getIsLoggedIn());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
