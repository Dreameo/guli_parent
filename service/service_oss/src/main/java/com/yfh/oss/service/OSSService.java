package com.yfh.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSSService {
    String uploadAvatar(MultipartFile multipartFile);
}
