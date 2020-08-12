package com.dian.Merchant.Backend.Service;

import com.dian.Merchant.Backend.Mapper.ProductMapper;
import com.dian.Merchant.Backend.Mapper.UserMapper;
import com.dian.Merchant.Backend.Model.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    ProductMapper productMapper;
    SqlSession session;

    public ProductService() {
        try {
            Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getConfiguration().addMapper(ProductMapper.class);
        productMapper = session.getMapper(ProductMapper.class);
    }

    public int create(Product product) {
        int res = productMapper.create(product);
        session.commit();
        return res;
    }

    public int update(Product product) {
        int res = productMapper.update(product);
        session.commit();
        return res;
    }

    public int delete(Product product) {
        int res = productMapper.delete(product);
        session.commit();
        return res;
    }


    public List<Product> findByName(Product product) {
        List<Product> res = productMapper.findByName(product);
        session.commit();
        return res;
    }

    public Product findById(Product product) {
        Product res = productMapper.findById(product);
        session.commit();
        return res;
    }

    public List<Product> findProductById(Product product) {
        List<Product> res = productMapper.findProductById(product);
        session.commit();
        return res;
    }

    public List<Product> findAllProduct() {
        List<Product>  res = productMapper.findAllProduct();
        session.commit();
        return res;
    }

    public int updateAfterTransaction(Product product) {
        int res = productMapper.updateAfterTransaction(product);
        session.commit();
        return res;
    }
}
