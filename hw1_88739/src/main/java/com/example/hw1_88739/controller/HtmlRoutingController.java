package com.example.hw1_88739.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlRoutingController {
    @RequestMapping(value="/")
    public static String welcome() {
        return "index";
    }

    @RequestMapping(value="/coordinatessearch")
    public static String coordinatesSearch(){return "coordinatessearch";}

    @RequestMapping(value="/airqualitysearch")
    public static String airQualitySearch() {
        return "airqualitysearch";
    }

    @RequestMapping(value="/weathersearch")
    public static String weatherSearch() {
        return "weathersearch";
    }
}
