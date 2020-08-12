package com.dian.Emoney.Backend.Service;

import com.dian.Emoney.Backend.Mapper.UserMapper;
import com.dian.Emoney.Backend.Model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.io.Reader;
import java.util.List;

@Service
public class UserService {
    UserMapper userMapper;
    SqlSession session;

    public UserService() {
        try {
            Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getConfiguration().addMapper(UserMapper.class);
        userMapper = session.getMapper(UserMapper.class);
    }

    public int register(User user) {
        int res =  userMapper.addUser(user);
        session.commit();
        return res;
    }

    public List<User> login(User user) {
        List<User> res = userMapper.login(user);
        session.commit();
        return res;
    }

    public Integer updateStatus(User user) {
        int res = userMapper.updateStatus(user);
        session.commit();
        return res;
    }

    public List<User> logout(User user) {
        List<User> res = userMapper.logout(user);
        session.commit();
        return res;
    }

    public List<User> topup(User user){
        List<User> res = userMapper.topup(user);
        session.commit();
        return res;
    }

    public Integer updateBalance(User user) {
        int res = userMapper.updateBalance(user);
        session.commit();
        return res;
    }

    public List<User> confirm(User user){
        List<User> res = userMapper.confirm(user);
        session.commit();
        return res;
    }

    public User findById(Long id) {
        User res = userMapper.findById(id);
        session.commit();
        return res;
    }

}
