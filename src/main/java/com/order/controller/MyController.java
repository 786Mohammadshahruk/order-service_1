package com.order.controller;

import com.order.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {


    @Autowired
    MyService myService;

    @GetMapping("/task")
    public String triggerAsyncTask(){
        myService.performAsyncTask();
        return " Task Triggered";
    }

}
