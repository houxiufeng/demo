package com.example.demo;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void test() throws Exception {
//        User user = new User();
//        user.setGender(true);
//        user.setName("allen");
//        user.setAge(10);
//        user.setNickName("allenxxxx");
//        userDao.save(user);
//
//        User user1 = new User();
//        user1.setName("fitz");
//        user1.setAge(20);
//        userDao.save(user1);

        Assert.assertEquals(2, userDao.findAll().size());
        Assert.assertEquals("fitz", userDao.findById(2L).getName());
        Assert.assertEquals("allen", userDao.findByNameAndGender("allen",true).getName());
//        userDao.delete(userDao.findByUserName("aa1"));
    }

}
