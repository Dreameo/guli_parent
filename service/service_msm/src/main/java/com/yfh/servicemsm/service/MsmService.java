package com.yfh.servicemsm.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, String> params, String phone);
}
