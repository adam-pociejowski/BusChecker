package com.valverde.buschecker.util; 

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.SitterDTO;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

public class BusUtilsTest {
    private Date now;
    private Bus bus;
    private BusDTO busDTO;

    @Before
    public void setup() throws Exception {
        now = new Date();
        bus = new Bus();
        bus.setSitters(new ArrayList<>());
        busDTO = new BusDTO();
    } 
    
    @Test
    public void busFieldsShouldBeReplaced() throws Exception {
        bus.setSideNumber("15");
        bus.setInsuranceDate(null);

        busDTO.setSideNumber("18");
        busDTO.setInsuranceDate(now);

        BusUtils.setFieldsToBus(bus, busDTO);
        assertEquals("18", bus.getSideNumber());
        assertEquals(now, bus.getInsuranceDate());
    }

    @Test
    public void busFieldsShouldBeSet() throws Exception {
        busDTO.setSideNumber("18");
        busDTO.setNumberOfSeats(22);
        busDTO.setInsuranceDate(now);
        busDTO.setExtinguisherReviewDate(now);
        busDTO.setLiftReviewDate(now);

        BusUtils.setFieldsToBus(bus, busDTO);
        assertEquals("18", bus.getSideNumber());
        assertEquals(22, bus.getNumberOfSeats());
        assertEquals(now, bus.getInsuranceDate());
        assertEquals(now, bus.getExtinguisherReviewDate());
        assertEquals(now, bus.getLiftReviewDate());
    }

    @Test(expected = BusUtils.BusUtilsException.class)
    public void shouldThrowExceptionOnNullBus() {
        BusUtils.setFieldsToBus(null, busDTO);
    }

    @Test(expected = BusUtils.BusUtilsException.class)
    public void shouldThrowExceptionOnNullBusDTO() {
        BusUtils.setFieldsToBus(bus, null);
    }

    @Test
    public void newSittersShouldBeSetToBus() {
        busDTO.setSitters(createNewSittersDTOList());
        BusUtils.updateSittersInBus(bus, busDTO);
        Integer expectedAmount = busDTO.getSitters().size();
        Integer sittersAmount = bus.getSitters().size();

        assertEquals(expectedAmount, sittersAmount);
    }

    @Test
    public void oldSitterShouldBeUpdated() {
        List<SitterDTO> sitterDTOs = new ArrayList<>();
        sitterDTOs.add(createSitterDTO(1L, "Kacper", "Jarek"));
        sitterDTOs.add(createSitterDTO(2L, "Karol", "Osa"));
        busDTO.setSitters(sitterDTOs);
        bus.setSitters(createSittersList());

        BusUtils.updateSittersInBus(bus, busDTO);
        Integer sittersAmount = bus.getSitters().size();
        Sitter sitter = bus.getSitters().get(0);
        Sitter sitter2 = bus.getSitters().get(1);

        assertEquals(new Integer(2), sittersAmount);
        assertEquals("Kacper", sitter.getFirstname());
        assertEquals("Jarek", sitter.getLastname());
        assertEquals("Karol", sitter2.getFirstname());
        assertEquals("Osa", sitter2.getLastname());
    }

    @Test
    public void newSittersShouldBeAdded() {
        List<SitterDTO> dtos = createSittersDTOList();
        dtos.addAll(createNewSittersDTOList());
        busDTO.setSitters(dtos);
        bus.setSitters(createSittersList());
        BusUtils.updateSittersInBus(bus, busDTO);
        Integer sittersAmount = bus.getSitters().size();

        assertEquals(new Integer(4), sittersAmount);
    }

    @Test
    public void sitterShouldBeDeletedFromBus() {
        List<SitterDTO> sitterDTOs = new ArrayList<>();
        sitterDTOs.add(createSitterDTO(1L, "Adam", "Kos"));
        busDTO.setSitters(sitterDTOs);
        bus.setSitters(createSittersList());
        BusUtils.updateSittersInBus(bus, busDTO);

        Integer sittersAmount = bus.getSitters().size();
        Sitter sitter = bus.getSitters().get(0);

        assertEquals(new Integer(1), sittersAmount);
        assertEquals("Adam", sitter.getFirstname());
        assertEquals("Kos", sitter.getLastname());
    }

    @Test(expected = BusUtils.BusUtilsException.class)
    public void shouldThrowExceptionWhenBusIsNull() {
        BusUtils.updateSittersInBus(null, busDTO);
    }

    @Test(expected = BusUtils.BusUtilsException.class)
    public void shouldThrowExceptionWhenBusDTOIsNull() {
        BusUtils.updateSittersInBus(bus, null);
    }

    private List<Sitter> createSittersList() {
        List<Sitter> sitters = new ArrayList<>();
        sitters.add(createSitter(1L, "Adam", "Kos"));
        sitters.add(createSitter(2L, "Karol", "Osa"));
        return sitters;
    }

    private Sitter createSitter(Long id, String firstname, String lastname) {
        Sitter sitter = new Sitter();
        sitter.setId(id);
        sitter.setFirstname(firstname);
        sitter.setLastname(lastname);
        return sitter;
    }

    private List<SitterDTO> createNewSittersDTOList() {
        List<SitterDTO> sitters = new ArrayList<>();
        sitters.add(createSitterDTO(null, "Jan", "Kos"));
        sitters.add(createSitterDTO(null, "Karol", "Lupa"));
        return sitters;
    }

    private List<SitterDTO> createSittersDTOList() {
        List<SitterDTO> sitters = new ArrayList<>();
        sitters.add(createSitterDTO(1L, "Adam", "Kos"));
        sitters.add(createSitterDTO(2L, "Karol", "Osa"));
        return sitters;
    }

    private SitterDTO createSitterDTO(Long id, String firstname, String lastname) {
        SitterDTO sitter = new SitterDTO();
        sitter.setId(id);
        sitter.setFirstname(firstname);
        sitter.setLastname(lastname);
        return sitter;
    }
}