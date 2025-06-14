package com.example.course_application.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<User> getAllUsers(int page, int limit, String sortBy, int sortDirection) {
        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return userRepository.findAll(pageable).getContent();
    };

    @Override
    public User getUserById(String id) {
        User userFromDB = userRepository.findById(id).orElse(null);
        return userFromDB;
        // return null;
    };

    @Override
    public User getUserByUsername(String username) {
        User userFromDB = userRepository.findByUsername(username).orElse(null);
        return userFromDB;
    };

    @Override
    public User updateUser(UserInput userInput, String id, User userFromDB) {

        User user = userFromDB;

        if (userInput.getName() != null) {
            user.setName(userInput.getName());
        }

        if (userInput.getDOB() != null) {
            user.setDOB(userInput.getDOB());
        }

        if (userInput.getPhone() != 0) {
            user.setPhone(userInput.getPhone());
        }

        if (userInput.getEmail() != null) {
            user.setEmail(userInput.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return updatedUser;

    };

    @Override
    public Boolean deleteUser(String id) {
        User userFromDB = userRepository.findById(id).orElse(null);
        if (userFromDB != null) {
            userFromDB.setDeleted(true);
            userRepository.save(userFromDB);
            return true;
        }
        return false;
    };

}
