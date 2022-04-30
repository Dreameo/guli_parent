package com.yfh.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadAliVideo(MultipartFile file);
}
