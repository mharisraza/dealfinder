package com.github.mharisraza.dealfinder.controllers;

import com.github.mharisraza.dealfinder.DealFinderApplication;
import com.github.mharisraza.dealfinder.utils.PageTitles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateRenderController {

    @GetMapping("/")
    public String renderIndex(Model model) {
        model.addAttribute("title", PageTitles.HOMEPAGE);
        return "index.html";
    }
}
