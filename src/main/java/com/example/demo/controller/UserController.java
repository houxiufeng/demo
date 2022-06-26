package com.example.demo.controller;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.FutureUtil;
import com.example.demo.common.RedisUtil;
import com.example.demo.common.Result;
import com.example.demo.dto.UserDto;
import com.example.demo.po.User;
import com.example.demo.service.ESUserService;
import com.example.demo.service.UserService;
import com.example.demo.vo.ESUserInfo;
import com.example.demo.vo.UserVo;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ESUserService esUserService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/hi")
    public Result sayHi() throws InterruptedException {
        doCallableTasks();
        doRunnableTasks();
        //redis string test
        redisUtil.set("key1", "hi redis");
        System.out.println(redisUtil.get("key1"));
        //redis obj test
        User user = new User();
        user.setId(1L);
        user.setName("bob");
        user.setGender(true);
        user.setAge(20);
        redisUtil.set("u1", user);
        User u1 = (User) redisUtil.get("u1");
        System.out.println(u1);
        //redis lock test
        boolean lock = redisUtil.lock("lock", "aaa");
        if (lock) {
            logger.info("i am locked");
        }
        Thread.sleep(300L);
        redisUtil.unlock("lock", "aaa");

        return Result.success("heart beat!");
    }

    @PostMapping(value = "/user")
    public Result<UserVo> addUser(@RequestBody UserDto userDto) {
        logger.info("controller addUser userDto:{}", userDto);
        User user = userService.addUser(userDto);
        UserVo userVo = Convert.convert(UserVo.class, user);
        return Result.success(userVo);
    }

    @DeleteMapping(value = "/user/{id}")
    public Result deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @PutMapping(value = "/user")
    public Result updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return Result.success();
    }

    @GetMapping(value = "/users")
    public Result<List<User>> listUsers(UserDto userDto) {
        logger.info("controller listUsers userDto:{}", userDto);
        List<User> users = userService.listByCondition(userDto);
        return Result.success(users);
    }

    @GetMapping(value = "/page/users")
    public Result<Page<User>> listPageUsers(UserDto userDto) {
        logger.info("controller listUsers userDto:{}", userDto);
        Page<User> userPage = userService.listByPage(userDto);
        return Result.success(userPage);
    }

    @GetMapping(value = "/es/users")
    public Result<List<ESUserInfo>> listUsersFromEs(UserDto userDto) {
        List<ESUserInfo> users = esUserService.findByNameAndAge(userDto.getName(), userDto.getAge());
        return Result.success(users);
    }

    @DeleteMapping(value = "/es/index/user")
    public Result deleteUserIndex() {
        esUserService.deleteIndex();
        return Result.success();
    }

    @PostMapping(value = "/es/index/user")
    public Result addUserIndex() {
        esUserService.createIndex();
        return Result.success();
    }

    @PostMapping(value = "/es/user")
    public Result addEsUser(@RequestBody ESUserInfo esUserInfo) {
        esUserService.save(esUserInfo);
        return Result.success();
    }

    @GetMapping(value = "/es/all/users")
    public Result<List<ESUserInfo>> listAllUsersFromEs() {
        List<ESUserInfo> users = Lists.newArrayList(esUserService.findAll());
        return Result.success(users);
    }

    @PostMapping(value = "/mock/user")
    public Result<UserVo> mockSaveUser(@RequestBody UserDto userDto) {
        UserVo userVo = Convert.convert(UserVo.class, userDto);
        return Result.success(userVo);
    }

    private void doCallableTasks() {
        List<String> list = FutureUtil.DEAULT_FUTURE.callableTask(() -> {
                    return "aaa";
                })
                .callableTask(() -> {
                    sleep(100L);
                    return "bbb";
                })
                .callableTask(() -> {
                    return "ccc";
                })
                .callableTask(() -> {
                    return "ddd";
                }).waitResult(30);

        for (String s : list) {
            logger.info("callableTask result {}", s);
        }
    }

    private void doRunnableTasks() {
        FutureUtil.DEAULT_FUTURE.runnableTask(() -> {
                    logger.info("runnableTask aaa");
                })
                .runnableTask(() -> {
                    logger.info("runnableTask bbb");
                })
                .runnableTask(() -> {
                    sleep(100L);
                    logger.info("runnableTask ccc");
                })
                .runnableTask(() -> {
                    logger.info("runnableTask ddd");
                }).waitExecute(30);
        logger.info("doRunnableTasks over!");
    }

    private void sleep(long n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
