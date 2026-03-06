package com.codingshuttle.testApp.TestAppModule6.services.impl;

import com.codingshuttle.testApp.TestAppModule6.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Data From Dev....... Env";
    }
}
