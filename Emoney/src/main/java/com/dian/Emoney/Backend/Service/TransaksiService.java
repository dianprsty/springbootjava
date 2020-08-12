package com.dian.Emoney.Backend.Service;


import com.dian.Emoney.Backend.Mapper.TransaksiMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.Reader;

@Service
public class TransaksiService {
    TransaksiMapper transaksiMapper;
    SqlSession session;

    public TransaksiService() {
        try {
            Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getConfiguration().addMapper(TransaksiMapper.class);
        transaksiMapper = session.getMapper(TransaksiMapper.class);
    }

}
