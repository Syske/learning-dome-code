package io.github.syske.pdfview.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: pdf-view
 * @description: 返回结果
 * @author: syske
 * @create: 2020-05-06 11:41
 */


public class AjaxJson implements Serializable {
    private boolean success = true;
    private MsgType msgType;
    private String msg = "操作成功";
    private Object obj = null;
    private Map<String, Object> attributes;

    public AjaxJson() {
    }

    public AjaxJson(Object obj) {
        this.obj = obj;
    }

    public AjaxJson(String msg) {
        this.msg = msg;
        this.success = false;
    }

    public AjaxJson(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public AjaxJson(String msg, boolean success, MsgType msgType) {
        this.msg = msg;
        this.success = success;
        this.msgType = msgType;
    }

    public AjaxJson(Exception e) {
        this.msg = e.getMessage();
        this.success = false;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJsonStr() {
        JSONObject obj = new JSONObject();
        obj.put("success", this.isSuccess());
        obj.put("msgType", this.msgType);
        obj.put("msg", this.getMsg());
        obj.put("obj", this.obj);
        obj.put("attributes", this.attributes);
        return obj.toJSONString();
    }

    public MsgType getMsgType() {
        return this.msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

}
