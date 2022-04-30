package com.yfh.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadAliVideo(MultipartFile file);

    void deleteVideoById(String id);

    void deleteAllVideo(List<String> videoList);
}
