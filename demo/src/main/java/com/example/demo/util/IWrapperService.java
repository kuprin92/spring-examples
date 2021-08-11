package com.example.demo.util;

import org.springframework.stereotype.Service;

@Service
public interface IWrapperService {
    Object getData(Object body);
}