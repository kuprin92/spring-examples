package com.example.demo.model;


import com.example.demo.util.IWrapperModel;
import com.example.demo.util.Views;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper implements IWrapperModel {
    @JsonUnwrapped
    @JsonView({Views.Public.class, Views.Internal.class})
    Object main;
    @JsonView(Views.Public.class)
    String someInfo;

    @Override
    public void setData(Object object) {
        someInfo = object.toString();
    }

    @Override
    public void setBody(Object object) {
        main = object;
    }
}