package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.CombinedFilter;
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

    public List<User> getAllUsers(CombinedFilter combinedFilter) {
        Sort sort = Sort.unsorted();
        if (!combinedFilter.getSort().getField().equals("")) {
            Sort.Direction direction = combinedFilter.getSort().getOrder() == 1 ? Sort.Direction.ASC
                    : combinedFilter.getSort().getOrder() == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, combinedFilter.getSort().getField());
        }
        Pageable pageable = PageRequest.of(combinedFilter.getPage() - 1, combinedFilter.getLimit(), sort);

        return userRepository.findAll(pageable).getContent();
    };

    public User getUserById(String id) {
        User userFromDB = userRepository.findById(id).orElse(null);
        return userFromDB;
        // return null;
    };

    public User getUserByUsername(String username) {
        User userFromDB = userRepository.findByUsername(username).orElse(null);
        return userFromDB;
    };

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

    public void deleteUser(String id) {
        Optional<User> userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            userRepository.deleteById(id);
        }
    };

}
