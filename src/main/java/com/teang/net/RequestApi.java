package com.teang.net;

import com.teang.base.BaseActivity;
import com.teang.net.bean.BaseResponse;
import com.teang.net.bean.Test;
import com.teang.net.utils.MyObserver;
import com.teang.net.utils.RetrofitUtils;
import com.teang.net.utils.RxHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RequestApi {
    /**
     * Get 请求demo
     *
     * @param context
     * @param observer
     */
    public static void getDemo(BaseActivity context, MyObserver<Test> observer) {
        RetrofitUtils.getApiUrl().getDemo()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    /**
     * 上传图片
     *
     * @param context
     * @param observer
     */
    public static void upImagView(BaseActivity context, String access_token, String str, Observer<BaseResponse> observer) {
        File file = new File(str);
//        File file = new File(imgPath);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("Authorization", access_token);
//        File file =new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RetrofitUtils.getApiUrl().uploadImage(header, body).compose(RxHelper.observableIO2Main(context))
                .subscribe(observer);
    }

    /**
     * 上传多张图片
     *
     * @param files
     */
    public static void upLoadImg(BaseActivity context, String access_token, List<File> files, Observer<BaseResponse> observer1) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("Authorization", access_token);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), photoRequestBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitUtils.getApiUrl().uploadImage1(header, parts).compose(RxHelper.observableIO2Main(context))
                .subscribe(observer1);
    }
}
