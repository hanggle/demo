package com.hanggle.demo.service;

import java.util.List;

public interface CacheService {

    List<String> defeatResonByCode(String reasonCode);

    String getTemp(String reasonCode);
}
