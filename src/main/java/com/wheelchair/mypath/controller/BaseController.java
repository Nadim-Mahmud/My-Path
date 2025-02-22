package com.wheelchair.mypath.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@RestController
public class BaseController {

    @GetMapping("/")
    public String getBaseUrl() {
        return "Hi there..";
    }
}
