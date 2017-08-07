package com.hhzb.fntalm.fargment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Admin;
import com.hhzb.fntalm.bean.WxOuath;
import com.hhzb.fntalm.fargment.home.buycard.BuyCardFargment;
import com.hhzb.fntalm.fargment.home.cancel.CancelFragment;
import com.hhzb.fntalm.fargment.home.chaxun.SearchFargment;
import com.hhzb.fntalm.fargment.home.input.InputCountFragment;
import com.hhzb.fntalm.fargment.home.output.OutPutFragment;
import com.hhzb.fntalm.fargment.home.recharge.RechargeFargment;
import com.hhzb.fntalm.module.AdminModule;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.CommonDialog;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.EncodeUtils;
import com.mmdet.lib.utils.JSONUtils;
import com.mmdet.lib.utils.ScreenUtils;

import org.json.JSONObject;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

import static com.hhzb.fntalm.R.id.view;

/**
 * Created by Administrator on 2017/3/21.
 */

public class QcordeFragment extends BaseFragment {

    private int mMenuId;
    private String appid="wx9b194f138b76bf92";//wx89760086fb149268
    private String appSecret="3792ecd9c682a3601b01c15889344ea8";//71bcdd2ed7057ef4b39a65960056902e
    private WebView webView;
    private String mState ;
    private String mCode ;
    private Runnable runnable;
    private Handler mHandler = new Handler() ;
    LinearLayout errorlay,tip_voew,loading_view;
    public static QcordeFragment newInstance(int menuId) {
        QcordeFragment fg = new QcordeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonData.ARG_MENUID, menuId);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMenuId = bundle.getInt(CommonData.ARG_MENUID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_qcorde, container, false);
        TTS.speck_input_qcode(getActivity());
        initView(view);
        return view;
    }
    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }
    private void initView(View view) {

        errorlay = (LinearLayout)view.findViewById(R.id.error);
        tip_voew = (LinearLayout)view.findViewById(R.id.tip_voew);
        loading_view = (LinearLayout)view.findViewById(R.id.loading_view);


        topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
                Toast.makeText(getActivity(), "Right Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void leftclick(){
                pop();
            }
        });

        mState = CommonFun.getOutTradeNo();


        webView = (WebView)view.findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setBuiltInZoomControls(true);  //启用内置缩放装置
        settings.setJavaScriptEnabled(true);    //启用JS脚本
        webView.setWebViewClient(new WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  //加载新的url
                webView.setVisibility(View.GONE);
                tip_voew.setVisibility(View.GONE);
                errorlay.setVisibility(View.GONE);
                loading_view.setVisibility(View.VISIBLE);
                //开始定时加载
                refreshStates();
                return true;    //返回true,代表事件已处理,事件流到此终止
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                errorlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.i("tag","onReceivedHttpError");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.i("tag","onLoadResource");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //最先开始执行
                Log.i("tag","onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("tag","onPageFinished");
            }
        });
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

        String redirect_uri = EncodeUtils.urlEncode("http://guifu.chinahighsoft.com/WeChat/WxRedirect.aspx");
        String url = "https://open.weixin.qq.com/connect/qrconnect?appid="+appid+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_login&state="+mState+"#wechat_redirect";
        webView.loadUrl(url);  //加载url
    }

    /**
     * 每隔5秒执行一次（定时刷新）
     */
    protected void refreshStates() {
        runnable = new Runnable() {
            @Override
            public void run() {
                getCode();
                mHandler.postDelayed(this, 6000);
            }
        };
        mHandler.postDelayed(runnable, 6000);//启动,5秒后执行runnable.
    }

    private void getCode() {
        RequestCenter.customer_WxCheck(getActivity(), CommonFun.getShopId(getActivity()), mState, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mHandler.removeCallbacks(runnable);
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        JSONObject arr = jsonObj.getJSONObject("Rst");
                        WxOuath wxOuath  = JSONUtils.jsonToBean(arr.toString(),WxOuath.class);
                        mCode = wxOuath.getCode();
                        getAccessToken(mCode);
                    } else {
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    String mAccessToken,mExpiresIn,mRefreshToken,mOpenid;
    private void getAccessToken(String mCode) {
        //{"access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE"}
        //{"errcode":40029,"errmsg":"invalid code"}
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appSecret+"&code="+mCode+"&grant_type=authorization_code";

        RequestCenter.customer_WxToken(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = responseObj.toString();
                    JSONObject jsonObj = new JSONObject(json);
                    if(!json.contains("errcode")){
                        mAccessToken = jsonObj.getString("access_token");
                        mExpiresIn = jsonObj.getString("expires_in");
                        mRefreshToken = jsonObj.getString("refresh_token");
                        mOpenid = jsonObj.getString("openid");
                        getUserMobile(mOpenid);
                    }
                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });

    }

    private void getUserMobile(final String openid) {
        RequestCenter.customer_GetMobileByOpenID(getActivity(),openid, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        String mobile = jsonObj.getString("Rst");
                        if(mMenuId == CommonData.MENU_INPUT){
                            //如果是存衣菜单进来的，那么要走第三步骤进行衣物件数录入
                            startWithPop(InputCountFragment.newInstance(mobile));
                        }else{
                            if(mMenuId==CommonData.MENU_OUTPUT){
                                startWithPop(OutPutFragment.newInstance(mobile));
                            }else if(mMenuId==CommonData.MENU_CANCEL){
                                startWithPop(CancelFragment.newInstance(mobile));
                            }else if(mMenuId==CommonData.MENU_BUYCARD){
                                startWithPop(BuyCardFargment.newInstance(mobile));
                            }else if(mMenuId==CommonData.MENU_RECHARGE){
                                startWithPop(RechargeFargment.newInstance(mobile));
                            }else if(mMenuId == CommonData.MENU_SEARCH){
                                startWithPop(SearchFargment.newInstance(mobile));
                            }
                        }

                    } else {
                        //如果没有手机号码
                        //弹框提示
                        showAlert(openid);
                    }
                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });}

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mHandler.removeCallbacks(runnable);
    }

    private void showAlert(final String openid) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setMessage("您还未将您的微信还未绑定柜子，绑定后才能使用微信登录使用服务，请先绑定？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fg.startSingleTask(QcordeFragment.this,MenuFragment.newInstance());

            }
        });
        builder.setPositiveButton("前往绑定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fg.startSingleTask(QcordeFragment.this,BindFragment.newInstance(mMenuId,openid));

            }
        });
        builder.setCancelable(true);//设置不可取消
        android.support.v7.app.AlertDialog mAlertDialog = builder.create();
        Window win = mAlertDialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = ScreenUtils.getScreenWidth(getActivity())/2;
        params.y = ScreenUtils.getScreenHeight(getActivity()) /4;//设置y坐标
        win.setAttributes(params);
        mAlertDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        mAlertDialog.show();
    }

}
