//package com.valverde.buschecker.service;
//
//import com.valverde.buschecker.IntegrationTest;
//import com.valverde.buschecker.entity.Bus;
//import com.valverde.buschecker.entity.Driver;
//import com.valverde.buschecker.entity.Sitter;
//import com.valverde.buschecker.entity.User;
//import com.valverde.buschecker.web.dto.BusDTO;
//import com.valverde.buschecker.web.dto.DriverDTO;
//import com.valverde.buschecker.web.dto.SitterDTO;
//import com.valverde.buschecker.web.dto.UserDTO;
//import org.junit.Test;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class UserServiceIT extends IntegrationTest {
//
//    private Long testUserId;
//
//    private static final Date NOW = new Date();
//
//    @Autowired
//    private UserService userService;
//
//    @Before
//    public void setup() throws Exception {
//        saveTestUser("testowy");
//    }
//
//    @Test
//    public void userShouldExist() throws Exception {
//        Boolean exist = userService.existUser("testowy");
//        assertTrue(exist);
//    }
//
//    @Test
//    public void userShouldntExist() throws Exception {
//        Boolean exist = userService.existUser("testowy2");
//        assertFalse(exist);
//    }
//
//    @Test
//    public void newUserShouldBeAdded() throws Exception {
//        String username = "testowy2";
//        saveTestUser(username);
//        User user = userService.getUserByUsername(username);
//        assertNotNull(user);
//    }
//
//    @Test(expected = Exception.class)
//    public void userShouldntBeFound() throws Exception {
//        String username = "testowy2";
//        userService.getUserByUsername(username);
//    }
//
//    @Test
//    public void updateSimpleUser() throws Exception {
//        String username = "updatedTestowy";
//        userService.updateUser(createUserDTO(username));
//        User user = userService.getUserById(testUserId);
//        assertEquals(username, user.getUsername());
//    }
//
//    @Test
//    public void updateWithNewDriver() throws Exception {
//        saveDriverOnUser();
//        assertAmountOfDrivers(1);
//    }
//
//    @Test
//    public void updateWithTwoNewDrivers() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        addDriverToUser(userDTO, "Janek", "Kosa", null);
//        userService.updateUser(userDTO);
//        assertAmountOfDrivers(2);
//    }
//
//    @Test
//    public void updateSingleDriver() throws Exception {
//        saveDriverOnUser();
//        User user = userService.getUserById(testUserId);
//        Long driverId = user.getDrivers().get(0).getId();
//
//        UserDTO userDTO = createUserDTO("testowa");
//        addDriverToUser(userDTO, "Janek", "Kosa", driverId);
//        userService.updateUser(userDTO);
//        user = assertAmountOfDrivers(1);
//        assertDriverOnUser(user, "testowa", "Janek", "Kosa");
//    }
//
//    @Test
//    public void removeDeletedDriver() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        addDriverToUser(userDTO, "Janek", "Kosa", null);
//        userService.updateUser(userDTO);
//        User user = assertAmountOfDrivers(2);
//
//        userDTO = createUserDTO("testowy2");
//        addDriverToUser(userDTO, "Janoslaw", "Kosek",
//                user.getDrivers().get(0).getId());
//        userService.updateUser(userDTO);
//
//        user = assertAmountOfDrivers(1);
//        assertDriverOnUser(user, "testowy2", "Janoslaw", "Kosek");
//    }
//
//    @Test
//    public void saveUserWithDriverAndBus() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        addBusToDriver(userDTO.getDrivers().get(0), "bus",null);
//        userService.updateUser(userDTO);
//
//        User user = assertAmountOfDrivers(1);
//        List<Bus> buses = user.getDrivers().get(0).getBuses();
//        assertEquals(1, buses.size());
//    }
//
//    @Test
//    public void saveUserWithDriverAndFewBuses() throws Exception {
//        saveUserWithBuses();
//        User user = assertAmountOfDrivers(1);
//        List<Bus> buses = user.getDrivers().get(0).getBuses();
//        assertEquals(3, buses.size());
//        assertEquals("bus", buses.get(0).getBusName());
//        assertEquals("bus2", buses.get(1).getBusName());
//        assertEquals("bus3", buses.get(2).getBusName());
//    }
//
//    @Test
//    public void deleteBusesFromDriver() throws Exception {
//        saveUserWithBuses();
//        User user = assertAmountOfDrivers(1);
//        Driver driver = user.getDrivers().get(0);
//        Long busId = driver.getBuses().get(0).getId();
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", driver.getId());
//        addBusToDriver(userDTO.getDrivers().get(0), "busUpdated", busId);
//        userService.updateUser(userDTO);
//
//        user = assertAmountOfDrivers(1);
//        List<Bus> buses = user.getDrivers().get(0).getBuses();
//        assertEquals(1, buses.size());
//        assertEquals("busUpdated", buses.get(0).getBusName());
//    }
//
//    @Test
//    public void saveSitter() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        addSitterAndBusToDriver(userDTO.getDrivers().get(0), "imie",
//                "nazwisko", null);
//        userService.updateUser(userDTO);
//
//        User user = assertAmountOfDrivers(1);
//        List<Bus> buses = user.getDrivers().get(0).getBuses();
//        List<Sitter> sitters = buses.get(0).getSitters();
//
//        assertEquals(1, buses.size());
//        assertEquals(1, sitters.size());
//        assertEquals("imie", sitters.get(0).getFirstname());
//        assertEquals("nazwisko", sitters.get(0).getLastname());
//    }
//
//    private void saveUserWithBuses() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        addBusToDriver(userDTO.getDrivers().get(0), "bus",null);
//        addBusToDriver(userDTO.getDrivers().get(0), "bus2",null);
//        addBusToDriver(userDTO.getDrivers().get(0), "bus3",null);
//        userService.updateUser(userDTO);
//    }
//
//    private void assertDriverOnUser(User user, String username,
//                                    String firstname, String lastname) {
//        List<Driver> drivers = user.getDrivers();
//        Driver driver = drivers.get(0);
//
//        assertEquals(username, user.getUsername());
//        assertEquals(firstname, driver.getFirstname());
//        assertEquals(lastname, driver.getLastname());
//    }
//
//    private User assertAmountOfDrivers(Integer expectedAmount) throws Exception {
//        User user = userService.getUserById(testUserId);
//        Integer driversAmount = user.getDrivers().size();
//        assertEquals(expectedAmount, driversAmount);
//        return user;
//    }
//
//    private void saveDriverOnUser() throws Exception {
//        UserDTO userDTO = createUserDTO("testowy");
//        addDriverToUser(userDTO, "Jan", "Kos", null);
//        userService.updateUser(userDTO);
//    }
//
//    private void addSitterToBus(BusDTO busDTO, String firstname,
//                                String lastname, Long sitterId) {
//        SitterDTO sitterDTO = new SitterDTO();
//        sitterDTO.setFirstname(firstname);
//        sitterDTO.setLastname(lastname);
//        sitterDTO.setId(sitterId);
//        List<SitterDTO> sitters = busDTO.getSitters();
//        sitters.add(sitterDTO);
//    }
//
//    private void addSitterAndBusToDriver(DriverDTO driverDTO,  String firstname,
//                                         String lastname, Long sitterId) {
//        BusDTO busDTO = createBusDTO("bus");
//        busDTO.setId(null);
//        addSitterToBus(busDTO, firstname, lastname, sitterId);
//        List<BusDTO> buses = driverDTO.getBuses();
//        buses.add(busDTO);
//    }
//
//    private void addBusToDriver(DriverDTO driverDTO, String busName, Long busId) {
//        BusDTO busDTO = createBusDTO(busName);
//        busDTO.setId(busId);
//        List<BusDTO> buses = driverDTO.getBuses();
//        buses.add(busDTO);
//    }
//
//    private void addDriverToUser(UserDTO userDTO, String firstname,
//                                 String lastname, Long driverId) {
//        List<DriverDTO> drivers = userDTO.getDrivers();
//        DriverDTO driverDTO = new DriverDTO();
//        driverDTO.setId(driverId);
//        driverDTO.setFirstname(firstname);
//        driverDTO.setLastname(lastname);
//        driverDTO.setBuses(new ArrayList<>());
//        drivers.add(driverDTO);
//    }
//
//    private BusDTO createBusDTO(String busName) {
//        BusDTO busDTO = new BusDTO();
//        busDTO.setBusName(busName);
//        busDTO.setNumberOfSeats(22);
//        busDTO.setSideNumber("SIDE");
//        busDTO.setRegisterNumber("REGISTER");
//        busDTO.setExtinguisherReviewDate(NOW);
//        busDTO.setLiftReviewDate(NOW);
//        busDTO.setInsuranceDate(NOW);
//        busDTO.setTachographReviewDate(NOW);
//        busDTO.setTechnicalReviewDate(NOW);
//        busDTO.setSitters(new ArrayList<>());
//        return busDTO;
//    }
//
//    private UserDTO createUserDTO(String username) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(testUserId);
//        userDTO.setUsername(username);
//        userDTO.setDrivers(new ArrayList<>());
//        return userDTO;
//    }
//
//    private void saveTestUser(String username) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword("testowy");
//        userService.save(user);
//        testUserId = user.getId();
//    }
//}