package com.example.demo.controller;

import com.example.demo.annotation.DisableResponseWrapper;
import com.example.demo.annotation.EnableResponseDTOWrapper;
import com.example.demo.annotation.EnableResponseWrapper;
import com.example.demo.model.MainModel;
import com.example.demo.model.MainModelDTO;
import com.example.demo.model.Wrapper;
import com.example.demo.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

@EnableResponseWrapper(wrapperClass = Wrapper.class)
@EnableResponseDTOWrapper(wrapperClass = MainModelDTO.class)
@RequestMapping("/test")
public class Controller {

    @GetMapping
    @JsonView(Views.Internal.class)
    public MainModel test() {
        return new MainModel("Stas", "Name", "Surname");
    }

    @GetMapping("/collection")
    @JsonView(Views.Public.class)
    public Collection<MainModel> testList() {
        Collection<MainModel> mainModels = new ArrayList<>();
        mainModels.add(new MainModel("Stas", "Name1", "Surname1"));
        mainModels.add(new MainModel("Stas", "Name2", "Surname2"));

        return mainModels;
    }

    @DisableResponseWrapper
    @GetMapping("/unwrapped")
    @JsonView(Views.Public.class)
    public MainModel unwrapped() {
        return new MainModel("Stas", "Name", "Surname");
    }
}