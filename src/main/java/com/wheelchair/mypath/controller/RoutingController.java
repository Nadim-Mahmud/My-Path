package com.wheelchair.mypath.controller;

import com.graphhopper.GHResponse;
import com.graphhopper.ResponsePath;
import com.wheelchair.mypath.model.apiresponse.Response;
import com.wheelchair.mypath.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@RestController
public class RoutingController {

    @Autowired
    private RoutingService routingService;

    @GetMapping(value = "/route")
    public ResponsePath getRoute(
            @RequestParam double fromLat,
            @RequestParam double fromLon,
            @RequestParam double toLat,
            @RequestParam double toLon) {

        return routingService.getBestRoute(fromLat, fromLon, toLat, toLon);
    }
}