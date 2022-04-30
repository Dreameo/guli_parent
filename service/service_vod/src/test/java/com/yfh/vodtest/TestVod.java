package com.yfh.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestVod {

    @Test  // 1. 根据视频id 获取视频播放地址
    public void testVod() throws ClientException {


        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6PmmwLCX6t7uCi81cj", "khpNpzc2DDIxJTz7HWM5gstpcrZj43");

        // 创建获取视频地址request 和 response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向 request对象里面设置 视频id
        request.setVideoId("9e5face24f264063ba4f35259e7933f8");

        // 调用初始化对象里面的方法， 传递request 获取数据
        response = client.getAcsResponse(request);
        try {
//            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    @Test // 获取视频凭证
    public void testVodAuth() throws ClientException {

        // 1. 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6PmmwLCX6t7uCi81cj", "khpNpzc2DDIxJTz7HWM5gstpcrZj43");

        // 2. 创建获取视频地址的request 和 response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 3. 向 request对象里面设置 视频id
        request.setVideoId("9e5face24f264063ba4f35259e7933f8");


        // 4. 调用初始化对象的方法 得到凭证
        response = client.getAcsResponse(request);

        System.out.println("playauth: " + response.getPlayAuth());
    }


    @Test
    public void testUpload() {
        String accessKeyId = "LTAI5t6PmmwLCX6t7uCi81cj";
        String accessKeySecret = "khpNpzc2DDIxJTz7HWM5gstpcrZj43";
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(now);

        String title = "upload_video" + format; // 上传文件之后的名称
        String fileName = "F:\\开发\\12项目\\在线教育--谷粒学院\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4"; // 本地文件路径和名称

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    @Test
    public void testDelVod() {

    }
}
