package com.example.demo;

import com.example.demo.dao.User2Dao;
import com.example.demo.domain.User;
import com.example.demo.domain.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private User2Dao userDao;

    @Test
    public void test() throws Exception {
        User user = userDao.findById(2L);
        if (user != null) {
            System.out.println("------1------" +user.getName() + "," + user.getNickName());
        }
        User user1 = userDao.findByName("allen");
        if (user1 != null) {
            System.out.println("------2------" + user1.getId());
        }
        List<User> users = userDao.findByAge(20);
        System.out.println("-------3-----"+users.size());

        User user3 = userDao.findByNameAndGender("allen",false);
        if (user3 != null) {
            System.out.println("-----4xxx-------" + user3.getName()+ "," + user3.getNickName());
        }
    }

    @Test
    public void test2() {
        User user = new User();
        user.setName("kent");
        user.setAge(12);
        user.setGender(true);
        user.setNickName("fff");
        userDao.insert(user);
        System.out.println(user.getId());
    }

    @Test
    public void test3() {
        User user = new User();
        user.setId(5L);
//        user.setAge(50);
//        user.setName("xxyy");
        user.setNickName("nb,你好");
//        user.setGender(false);
        userDao.update(user);
    }

    @Test
    public void test4() {
        userDao.delete(13L);
    }

    @Test
    public void test5() {
        List<UserInfo> userInfos = userDao.findUserInfos("kent","cn");
        for (UserInfo item : userInfos) {
            System.out.println(item.getId() + ":" + item.getName() + ":" + item.getCountryName());
        }
    }

}
