package com.yfh.servicemsm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yfh.servicemsm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, String> params, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5t6PmmwLCX6t7uCi81cj", "khpNpzc2DDIxJTz7HWM5gstpcrZj43");
        IAcsClient client = new DefaultAcsClient(profile);

        // 固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone); // 手机号
        request.putQueryParameter("SignName", "阿里云短信测试"); // 签名名称 ： 这里用的是阿里云测试签名
        request.putQueryParameter("TemplateCode", "SMS_154950909"); // 模版值 这里使用阿里云测试的模板
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params)); // 将 code转化为 "{\"code\":\"1234\"}" json格式 因此用fastjson转化

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }




//    public class Sample {
//
//        /**
//         * 使用AK&SK初始化账号Client
//         * @param accessKeyId
//         * @param accessKeySecret
//         * @return Client
//         * @throws Exception
//         */
//        public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
//            Config config = new Config()
//                    // 您的AccessKey ID
//                    .setAccessKeyId(accessKeyId)
//                    // 您的AccessKey Secret
//                    .setAccessKeySecret(accessKeySecret);
//            // 访问的域名
//            config.endpoint = "dysmsapi.aliyuncs.com";
//            return new com.aliyun.dysmsapi20170525.Client(config);
//        }
//
//        public static void main(String[] args_) throws Exception {
//            java.util.List<String> args = java.util.Arrays.asList(args_);
//            com.aliyun.dysmsapi20170525.Client client = Sample.createClient("accessKeyId", "accessKeySecret");
//            SendSmsRequest sendSmsRequest = new SendSmsRequest()
//                    .setSignName("阿里云短信测试")
//                    .setTemplateCode("SMS_154950909")
//                    .setPhoneNumbers("18172745452")
//                    .setTemplateParam("{\"code\":\"1234\"}");
//            // 复制代码运行请自行打印 API 的返回值
//            client.sendSms(sendSmsRequest);
//        }
//    }

}
