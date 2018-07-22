package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @RequestMapping("/view1")
    public ModelAndView view1(String messages) {
        return new ModelAndView("test", "messages", messages);
    }
}
