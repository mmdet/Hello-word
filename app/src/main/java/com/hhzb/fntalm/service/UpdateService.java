package com.hhzb.fntalm.service;

import android.view.View;

import com.allenliu.versionchecklib.AVersionService;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.ToastUtils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/19.
 */

public class UpdateService extends AVersionService {

    @Override
    public void onResponses(AVersionService service, String response) {
       // ToastUtils.showShortToast(getApplication(),"1:"+response);
        try{
           // ToastUtils.showShortToast(getApplication(),"2:"+response);
            String json = CommonJsonCallback.parseXml(response);
            //ToastUtils.showShortToast(getApplication(),"json:"+json);
            JSONObject jsonObj = new JSONObject(json);
            ToastUtils.showShortToast(getApplication(),"jsonObj");
            String isSuccess = jsonObj.getString("IsSuccess");
            String errorMsg = jsonObj.getString("ErrorMsg");
            //ToastUtils.showShortToast(getApplication(),isSuccess);
            if (isSuccess.equals("true")) {
                String relust = jsonObj.getString("Rst");
                if(relust.equals("null") || relust.equals("")){
                    ToastUtils.showShortToast(getApplication(),"已经是最新版本！");
                    return;
                }
                JSONObject versonJSONObject = jsonObj.getJSONObject("Rst");
                //ToastUtils.showShortToast(getApplication(),"kao");
                String versionNo = versonJSONObject.getString("VersionNo");
                String downUrl = versonJSONObject.getString("Url");
                String comment = versonJSONObject.getString("Comment");
                service.showVersionDialog(downUrl,versionNo,comment);
            }else{
                //ToastUtils.showShortToast(getApplication(),"else");
            }
        }catch (Exception e){
           // ToastUtils.showShortToast(getApplication());
        }

    }
}
