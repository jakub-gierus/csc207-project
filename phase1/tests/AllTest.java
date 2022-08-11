import controller.FrontController;
import databases.UserRepository;
import entity.art.Art;
import entity.markets.Wallet;
import entity.user.AdminUser;
import entity.user.BasicUser;
import entity.user.User;
import exceptions.user.IncorrectUserNameOrPasswordException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usecases.art.ArtGenerator;
import usecases.art.ArtManager;
import usecases.markets.Market;
import usecases.markets.WalletManager;
import usecases.user.AdminFacade;
import usecases.user.CreateUser;
import usecases.user.LogIn;
import usecases.user.UserFacade;
import utils.Config;
import utils.DynamoDBConfig;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

    public class AllTest {
        Config config;
        FrontController frontController;
        UserRepository ur;
        WalletManager wm;
        ArtManager am;
        DynamoDBConfig dbConfig;
        @Before
        public void setUp() {
            this.config = new Config("./storage/",
                    "basicUsers.csv",
                    "adminUsers.csv",
                    "events.csv",
                    "wallets.csv",
                    "asciiArts.csv",
                    "./lang/",
                    "lang.json",
                    "en");
            this.frontController = new FrontController(config);
            ur = frontController.getUserRepository();
            wm = frontController.getWalletManager();
            am = frontController.getArtManager();
            dbConfig = new DynamoDBConfig();
            CreateUser cuuc = new CreateUser(ur, wm);
            cuuc.createUser("Basic1", "Basic1", false);
            cuuc.createUser("Admin1", "Admin1", true);
        }

        @After
        public void tearDown() {
        }

        @Test
        public void testLogin_LogOut() {
            try {
                UserRepository ur = new UserRepository();
                WalletManager wm = new WalletManager(ur,dbConfig);
                ArtManager am = new ArtManager(wm,dbConfig);
                CreateUser cuuc = new CreateUser(ur, wm);
                cuuc.createUser("Basic1", "Basic1", false);
                cuuc.createUser("Admin1", "Admin1", true);

                AdminUser u1 = (AdminUser) ur.getByUsername("Admin1").orElseThrow(IncorrectUserNameOrPasswordException::new);
                BasicUser u2 = (BasicUser) ur.getByUsername("Basic1").orElseThrow(IncorrectUserNameOrPasswordException::new);

                LogIn luc = new LogIn(ur);
                luc.logIn("Admin1", "Admin1");
                luc.logIn("Basic1", "Basic1");
                assertTrue(u1.getIsLoggedIn());
                assertTrue(u2.getIsLoggedIn());

                UserFacade uf1 = new UserFacade(u1,ur,wm,am);
                UserFacade uf2 = new UserFacade(u2,ur,wm,am);

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
                WalletManager wm = new WalletManager(ur,dbConfig);
                ArtManager am = new ArtManager(wm,dbConfig);
                CreateUser cuuc = new CreateUser(ur,wm);
                cuuc.createUser("basic1", "basic1", false);
                cuuc.createUser("admin1", "admin1", true);

                AdminUser u1 = (AdminUser) ur.getByUsername("admin1").orElseThrow(IncorrectUserNameOrPasswordException::new);
                BasicUser u2 = (BasicUser) ur.getByUsername("basic1").orElseThrow(IncorrectUserNameOrPasswordException::new);

                LogIn luc = new LogIn(ur);
                luc.logIn("admin1", "admin1");
                luc.logIn("basic1", "basic1");

                AdminFacade af1 = new AdminFacade(u1,ur,wm,am);
                af1.banUser("basic1", LocalDateTime.now().plusMinutes(2));
                assertFalse(u2.getIsLoggedIn());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test
        public void testMintArt(){
            try{
                User u1 = frontController.getUserRepository().getByUsername("Basic1").get();
                UserFacade uf = new UserFacade(u1, ur,wm,am);
                uf.addWallet("fromWallet",true);

                assertEquals(wm.getWalletsByUserName(u1.getUsername()).size() , 2);
                Wallet w1 = getWallet(u1,"fromWallet");
                Art newArt = mintArt(w1.getId());
                assertFalse(w1.getCurrency() == 0 && am.getArtByWallet(w1.getId()).size() == 0);

                HashMap<UUID, Art> map = am.getArtByWalletMap(w1.getId());
                assertTrue(map.values().stream().anyMatch(art -> Objects.equals(art.getArt(), newArt.getArt())));

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Test
        public void putItemOnMarket(){
            try{
                User u1 = frontController.getUserRepository().getByUsername("Basic1").get();
                UserFacade uf = new UserFacade(u1, ur,wm,am);
                uf.addWallet("fromWallet",true);

                Wallet w = getWallet(u1,"fromWallet");
                Art art = mintArt(w.getId());

                Market mk = new Market(ur,am,wm);
                mk.addArtToMarket(art.getId());

                assertTrue(mk.checkItem(art));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Test
        public void testCashTrade(){
            try{
                User u1 = frontController.getUserRepository().getByUsername("Basic1").get();
                UserFacade uf = new UserFacade(u1, ur,wm,am);
                uf.addWallet("fromWallet",true);
                Wallet w1 = getWallet(u1,"fromWallet");

                User u2 = frontController.getUserRepository().getByUsername("admin").get();
                UserFacade uf2 = new UserFacade(u2, ur,wm,am);
                Wallet w2 = getWallet(u2,"admin's wallet");

                Market market = new Market(ur,am,wm);
                Art newArt = mintArt(w1.getId());
                market.addArtToMarket(newArt.getId());
                market.makeTradeWithCash(newArt.getId(),w2.getId());

                assertFalse(market.checkItem(newArt));
                assertTrue(am.getArtByWalletMap(w2.getId()).containsKey(newArt.getId()));
                assertFalse(am.getArtByWalletMap(w1.getId()).containsKey(newArt.getId()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        private Art mintArt(UUID walletId){
            Art res = null;
            try{
                ArtGenerator artGenerator = new ArtGenerator();
                String generatedArt = artGenerator.generateArt("plane");
                String price = "10.00";
                float priceDouble = Float.parseFloat(price);
                am.createNewArt("plane", generatedArt, priceDouble, walletId);

                res =  am.getAllArt().stream()
                        .filter(art -> art.getArt().equals(generatedArt))
                        .findFirst()
                        .orElse(null);

            } catch(Exception e){
                e.printStackTrace();
            }
            return res;
        }

        private Wallet getWallet(User user, String name){
            return wm.getWalletsByUserName(user.getUsername())
                    .stream()
                    .filter(w -> w.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }
