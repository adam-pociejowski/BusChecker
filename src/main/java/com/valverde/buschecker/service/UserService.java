package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.UserRepository;
import com.valverde.buschecker.util.UserUtils;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@CommonsLog
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverService driverService;

    public Boolean existUser(String username) {
        try {
            getUserByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new Exception("User with username: " + username + " not found");

        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<DriverDTO> getOtherDrivers(String username) throws Exception {
        User user = getUserByUsername(username);
        return driverService.getOtherDriversFromUser(user);
    }

    User getUser(Long id) {
        User user = userRepository.findOne(id);
        UserUtils.removeDuplicateDrivers(user);
        return user;
    }
}
