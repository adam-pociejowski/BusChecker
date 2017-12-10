package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.Notification;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.enums.ReviewType;
import com.valverde.buschecker.repository.NotificationRepository;
import com.valverde.buschecker.util.NotificationUtils;
import com.valverde.buschecker.web.dto.SmsDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.valverde.buschecker.enums.ReviewType.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    @Before
    public void setup() {
        notificationService = new NotificationService(notificationRepository);
        Mockito.doReturn(new Object()).when(notificationRepository).save(any(Notification.class));
        createUserAndDriver();
    }

    @Test
    public void numberShouldHave9Digits() {
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("12345"));
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("123456"));
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("1234567"));
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("12345678"));
        assertTrue(NotificationUtils.hasCorrectPhoneNumber("123456789"));
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("1234567890"));
    }

    @Test
    public void numberCannotBeNull() {
        assertFalse(NotificationUtils.hasCorrectPhoneNumber(null));
    }

    @Test
    public void numberCannotHaveOtherCharactersThanDigits() {
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("12345678a"));
        assertFalse(NotificationUtils.hasCorrectPhoneNumber("abcdefghi"));
    }

    @Test
    public void shouldSendSingleMessage() {
        List<SmsDTO> messagesToSend = notificationService.getMessagesToSend(driver, user);
        assertEquals(1, messagesToSend.size());
        assertEquals("48"+PHONE_NUMBER, messagesToSend.get(0).getRecipients());
    }

    @Test
    public void shouldSendMessagesForEveryBus() {
        driver.setBuses(new ArrayList<>());
        Bus bus = createBus();
        bus.setInsuranceDate(getEarlierDate(INSURANCE.getMonth()));
        bus.setExtinguisherReviewDate(getEarlierDate(EXTIGUISHERY.getMonth() + 1));
        bus.setLiftReviewDate(getEarlierDate(LIFT.getMonth() - 1));

        Bus bus2 = createBus();
        bus2.setInsuranceDate(getEarlierDate(INSURANCE.getMonth() + 2));
        bus2.setTachographReviewDate(getEarlierDate(TACHOGRAPH.getMonth()));
        driver.getBuses().add(bus);
        driver.getBuses().add(bus2);

        List<SmsDTO> messagesToSend = notificationService.getMessagesToSend(driver, user);
        assertEquals(2, messagesToSend.size());
        assertMessage(messagesToSend.get(0), bus.getInsuranceDate(), INSURANCE);
        assertMessage(messagesToSend.get(1), bus2.getTachographReviewDate(), TACHOGRAPH);
    }

    private void assertMessage(SmsDTO sms, Date date, ReviewType type) {
        assertEquals("48"+PHONE_NUMBER, sms.getRecipients());
        assertEquals(NotificationUtils.getSmsMessage(type, date), sms.getMessage());
    }

    private void createUserAndDriver() {
        driver = new Driver();
        driver.setUsers(new ArrayList<>());
        driver.setBuses(createBuses());
        driver.setPhoneNumber(PHONE_NUMBER);
        driver.setNotificationBefore(NOTIFICATION_BEFORE);

        user = new User();
        user.setDrivers(new ArrayList<>());
        user.getDrivers().add(driver);
        driver.getUsers().add(user);
    }

    private List<Bus> createBuses() {
        List<Bus> buses = new ArrayList<>();
        Bus bus = createBus();
        bus.setExtinguisherReviewDate(getEarlierDate(EXTIGUISHERY.getMonth()));
        buses.add(bus);
        return buses;
    }

    private Bus createBus() {
        Bus bus = new Bus();
        bus.setDriver(driver);
        return bus;
    }

    private Date getEarlierDate(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months * -1);
        calendar.add(Calendar.DAY_OF_YEAR, NOTIFICATION_BEFORE);
        return calendar.getTime();
    }

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private User user;

    private Driver driver;

    private final static String PHONE_NUMBER = "123456789";

    private final static Integer NOTIFICATION_BEFORE = 14;
}
