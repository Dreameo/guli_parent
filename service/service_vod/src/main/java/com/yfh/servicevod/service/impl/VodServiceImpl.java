package com.yfh.servicevod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.yfh.servicebase.exception.GuliException;
import com.yfh.servicevod.service.VodService;
import com.yfh.servicevod.utils.ConstantPropertiesUtil;
import com.yfh.servicevod.utils.InitObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAliVideo(MultipartFile file) {
        // file 用流上传
        // title： 上传之后显示的名称
        // fileName: 上传文件原始名称
        // inputStream: 上传文件输入流
        try {

            String fileName = file.getOriginalFilename(); //
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);


            String videoId = null;

//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                videoId = response.getVideoId();
            }


            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteVideoById(String id) {
        try {
            // 1. 初始化对象
            DefaultAcsClient client = InitObjectUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            // 2. 创建删除视频 request 和 response 对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            DeleteVideoResponse response = new DeleteVideoResponse();

            // 3. request中设置 视频id
            request.setVideoIds(id);

            // 4. response 调用结果
            response = client.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }

    }

    @Override
    public void deleteAllVideo(List<String> videoList) {
        // 遍历 videolist 用逗号拼接 传入接口进行删除

        try {
            // 1. 初始化对象
            DefaultAcsClient client = InitObjectUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            // 2. 创建删除视频 request 和 response 对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            DeleteVideoResponse response = new DeleteVideoResponse();

            //一次只能批量删20个
            String str = StringUtils.join(videoList.toArray(), ",");

            // 3. request中设置 视频id
            request.setVideoIds(str);

            // 4. response 调用结果
            response = client.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
