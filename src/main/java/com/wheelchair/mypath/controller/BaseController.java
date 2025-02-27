package com.wheelchair.mypath.controller;

import com.wheelchair.mypath.cron.MapUpdateScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@RestController
public class BaseController {

    @Autowired
    MapUpdateScheduler mapUpdateScheduler;

    @GetMapping("/")
    public String getBaseUrl() {
        return "Hi there..";
    }

    @GetMapping("/test")
    public String test() {
        mapUpdateScheduler.updateMapWeekly();

        return "done";
    }
}
