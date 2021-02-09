package com.experis.musiclibrary.controllers;

import com.experis.musiclibrary.data_access.CustomerViewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class CustomerViewController {

    CustomerViewRepository crep = new CustomerViewRepository();

    //could have been made to use one function but i am unsure how that would have affected input sanitation
    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("artists", crep.getRandomArtist(5));
        model.addAttribute("tracks", crep.getRandomTrack(5));
        model.addAttribute("genres", crep.getRandomGenre(5));
        return "index";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "query", required = true) String query, Model model) {
        model.addAttribute("searchResults", crep.getTrackSearch(query));
        model.addAttribute("searchQuery", query);
        return "search-result";
    }
}
