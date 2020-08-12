package com.dian.Merchant.Backend.Service;

import com.dian.Merchant.Backend.Mapper.UserMapper;
import com.dian.Merchant.Backend.Model.MerchantUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

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

    public int register(MerchantUser user) {
        int res =  userMapper.addUser(user);
        session.commit();
        return res;
    }

    public List<MerchantUser> login(MerchantUser user) {
        List<MerchantUser> res = userMapper.login(user);
        session.commit();
        return res;
    }

    public Integer updateStatus(MerchantUser user) {
        int res = userMapper.updateStatus(user);
        session.commit();
        return res;
    }

    public List<MerchantUser> logout(MerchantUser user) {
        List<MerchantUser> res = userMapper.logout(user);
        session.commit();
        return res;
    }

}
