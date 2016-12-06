package com.valverde.buschecker.service; 

import com.valverde.buschecker.IntegrationTest;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.web.dto.DriverDTO;
import com.valverde.buschecker.web.dto.UserDTO;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceIT extends IntegrationTest {

    private Long testUserId;

    @Autowired
    private UserService userService;

    @Before
    public void setup() throws Exception {
        saveTestUser("testowy");
    }

    @Test
    public void userShouldExist() throws Exception {
        Boolean exist = userService.existUser("testowy");
        assertTrue(exist);
    }

    @Test
    public void newUserShouldBeAdded() throws Exception {
        String username = "testowy2";
        saveTestUser(username);
        User user = userService.getUserByUsername(username);
        assertNotNull(user);
    }

    @Test(expected = Exception.class)
    public void userShouldntBeFound() throws Exception {
        String username = "testowy2";
        userService.getUserByUsername(username);
    }

    @Test
    public void updateSimpleUser() throws Exception {
        String username = "updatedTestowy";
        userService.updateUser(createUserDTO(username));
        User user = userService.getUserById(testUserId);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void updateWithNewDriver() throws Exception {
        saveDriverOnUser();
        assertAmountOfDrivers(1);
    }

    @Test
    public void updateWithTwoNewDrivers() throws Exception {
        UserDTO userDTO = createUserDTO("testowy");
        addDriverToUser(userDTO, "Jan", "Kos", null);
        addDriverToUser(userDTO, "Janek", "Kosa", null);
        userService.updateUser(userDTO);
        assertAmountOfDrivers(2);
    }

    @Test
    public void updateSingleDriver() throws Exception {
        saveDriverOnUser();
        User user = userService.getUserById(testUserId);
        Long driverId = user.getDrivers().get(0).getId();

        UserDTO userDTO = createUserDTO("testowa");
        addDriverToUser(userDTO, "Janek", "Kosa", driverId);
        userService.updateUser(userDTO);
        user = assertAmountOfDrivers(1);
        assertDriverOnUser(user, "testowa", "Janek", "Kosa");
    }

    @Test
    public void removeDeletedDriver() throws Exception {
        UserDTO userDTO = createUserDTO("testowy");
        addDriverToUser(userDTO, "Jan", "Kos", null);
        addDriverToUser(userDTO, "Janek", "Kosa", null);
        userService.updateUser(userDTO);
        User user = assertAmountOfDrivers(2);

        userDTO = createUserDTO("testowy2");
        addDriverToUser(userDTO, "Janoslaw", "Kosek",
                user.getDrivers().get(0).getId());
        userService.updateUser(userDTO);

        user = assertAmountOfDrivers(1);
        assertDriverOnUser(user, "testowy2", "Janoslaw", "Kosek");
    }


    private void assertDriverOnUser(User user, String username,
                                    String firstname, String lastname) {
        List<Driver> drivers = user.getDrivers();
        Driver driver = drivers.get(0);

        assertEquals(username, user.getUsername());
        assertEquals(firstname, driver.getFirstname());
        assertEquals(lastname, driver.getLastname());
    }

    private User assertAmountOfDrivers(Integer expectedAmount) throws Exception {
        User user = userService.getUserById(testUserId);
        Integer driversAmount = user.getDrivers().size();
        assertEquals(expectedAmount, driversAmount);
        return user;
    }

    private void saveDriverOnUser() throws Exception {
        UserDTO userDTO = createUserDTO("testowy");
        addDriverToUser(userDTO, "Jan", "Kos", null);
        userService.updateUser(userDTO);
    }

    private void addDriverToUser(UserDTO userDTO, String firstname,
                                 String lastname, Long driverId) {
        List<DriverDTO> drivers = userDTO.getDrivers();
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driverId);
        driverDTO.setFirstname(firstname);
        driverDTO.setLastname(lastname);
        driverDTO.setBuses(new ArrayList<>());
        drivers.add(driverDTO);
    }

    private UserDTO createUserDTO(String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(testUserId);
        userDTO.setUsername(username);
        userDTO.setDrivers(new ArrayList<>());
        return userDTO;
    }

    private void saveTestUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("testowy");
        userService.save(user);
        testUserId = user.getId();
    }
}