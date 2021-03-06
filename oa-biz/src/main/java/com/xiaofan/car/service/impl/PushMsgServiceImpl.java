package com.xiaofan.car.service.impl;

import com.xiaofan.car.persistence.dingding.enums.MsgTypeEnum;
import com.xiaofan.car.persistence.dingding.msg.*;
import com.xiaofan.car.service.PushMsgService;
import com.xiaofan.car.util.json.JSONHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * TODO: 请添加描述
 *
 * @author gongdaoshun
 * @date 2017/8/17
 * @since 1.0.0
 */
@Service
@Slf4j
public class PushMsgServiceImpl implements PushMsgService {

    @Value(value = "${DINGDING_CAR_OA_HTTP_URL:''}")
    private String httpUrl;

    @Value(value = "${CAR_OA_ACCESS_TOKEN:''}")
    private String accessToken;

    private static final String ACCESS_TOKEN="access_token";

    @Override
    public boolean pushTextMsg(TextMsg textMsg) {
        // 1.校验参数
        Assert.isTrue(StringUtils.isNotBlank(textMsg.getText().getContent()),"消息内容不能为空");
        textMsg.setMsgtype(MsgTypeEnum.TEXT.getCode());
        return this.pushMsgToDingDing(textMsg);
    }

    @Override
    public boolean pushLinkMsg(LinkMsg linkMsg) {
        linkMsg.setMsgtype(MsgTypeEnum.LINK.getCode());
        return this.pushMsgToDingDing(linkMsg);
    }

    @Override
    public boolean pushActionCardMsg(ActionCardMsg actionCardMsg) {
        actionCardMsg.setMsgtype(MsgTypeEnum.ACTION_CARD.getCode());
        return this.pushMsgToDingDing(actionCardMsg);
    }

    @Override
    public boolean pushFeedCardMsg(FeedCardMsg feedCardMsg) {
        feedCardMsg.setMsgtype(MsgTypeEnum.FEED_CARD.getCode());
        return this.pushMsgToDingDing(feedCardMsg);
    }

    @Override
    public boolean pushMarkdownMsg(MarkdownMsg markdownMsg) {
        markdownMsg.setMsgtype(MsgTypeEnum.MARKDOWN.getCode());
        return this.pushMsgToDingDing(markdownMsg);
    }

    /**
     * 推送消息
     * @param object
     */
    private boolean pushMsgToDingDing(Object object){
        boolean flag = false;
        try{
            String WEBHOOK_TOKEN = httpUrl+"?"+ACCESS_TOKEN+"="+accessToken;
            HttpClient httpclient = HttpClients.createDefault();

            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");

            String textMsg = JSONHelper.obj2JSONString(object);

            log.info("调用地址为：{}，参数为：{}",WEBHOOK_TOKEN,textMsg);
            StringEntity se = new StringEntity(textMsg, "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                flag=true;
                String result= EntityUtils.toString(response.getEntity(), "utf-8");
                log.info("调用钉钉机器人接口返回结果为：{}",result);
            }
            else{
            }
        }catch(Exception e){
            log.error("推送钉钉机器人消息失败：",e);
        }
        return flag;
    }
}
