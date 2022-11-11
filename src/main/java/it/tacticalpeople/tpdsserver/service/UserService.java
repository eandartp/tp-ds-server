package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.User;
import it.tacticalpeople.tpdsserver.dto.UserDto;
import it.tacticalpeople.tpdsserver.mapper.UserMapper;
import it.tacticalpeople.tpdsserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public UserDto saveUser(UserDto userDto) {
        log.debug("Request to save User : {}", userDto);
        User user = mapper.fromDtoToUser(userDto);
        user = userRepository.save(user);
        return mapper.fromUserToDto(user);
    }

    public Optional<User> partialUpdate(UserDto userDto)
    {
        log.debug("Request to partially update User : {}", userDto);
        return userRepository
                .findById(userDto.getId())
                .map(existingUser -> {
                    if(userDto.getUsername() != null)
                        existingUser.setUsername(userDto.getUsername());
                    if(userDto.getName() != null)
                        existingUser.setName(userDto.getName());
                    if(userDto.getSurname() != null)
                        existingUser.setSurname(userDto.getSurname());
                    if(userDto.getBusinessRole() != null)
                        existingUser.setBusinessRole(userDto.getBusinessRole());
                    if(userDto.getEmail() != null)
                        existingUser.setEmail(userDto.getEmail());

                    return existingUser;

                }).map(userRepository::save);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll()
    {
        log.debug("Request to get all Users");
        return userRepository.findAll().stream()
                .map(user -> mapper.fromUserToDto(user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findOne(Long id)
    {
        log.debug("Request to get User : {}", id);
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = null;
        if(user.isPresent())
            userDto = this.mapper.fromUserToDto(user.get());
        return userDto;
    }


    public void delete(Long id)
    {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }


    public UserDto updateUser(Long id, UserDto protDTO) throws ResourceException {
        log.debug("UserService - updateUser");
        User user = null;
        user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Invalid id"));
        user = mapper.fromDtoToUser(protDTO);
        user = userRepository.save(user);
        return mapper.fromUserToDto(user);
    }

}
