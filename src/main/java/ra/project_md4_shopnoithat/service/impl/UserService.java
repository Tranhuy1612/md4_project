package ra.project_md4_shopnoithat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ra.project_md4_shopnoithat.dao.impl.UserDao;
import ra.project_md4_shopnoithat.dto.request.FormLoginDto;
import ra.project_md4_shopnoithat.dto.request.FormRegisterDto;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.IUserService;

import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        return null;
    }

    public User findById(Long id) {
        return null;
    }
    public void save(FormRegisterDto formRegisterDto) {
        // chuyen doi dto sang model
        User user = new User();
        user.setUsername(formRegisterDto.getUsername());
        user.setPassword(formRegisterDto.getPassword());
        user.setFullName(formRegisterDto.getEmail());
        userDao.save(user);
    }


    public void delete(Long id) {

    }
    public User login(FormLoginDto formLoginDto){
        return userDao.login(formLoginDto);
    }

    public User register(FormRegisterDto formRegisterDto){
        return userDao.register(formRegisterDto);
    }

    @Override
    public boolean checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.checkEmail(email);
    }
}
