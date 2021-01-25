package io.github.syske.websigndemo.entity;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AjaxJson implements Serializable {
    private boolean success = true;
    private MsgType msgType;
    private String msg = "操作成功";
    private Object obj = null;
    private Map<String, Object> attributes;
    // 业务类别 1 权限相关业务 2 其他业务（查询、新增等）
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MsgType getMsgType() {
        return this.msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getJsonStr() {
        JSONObject obj = new JSONObject();
        obj.put("success", this.isSuccess());
        obj.put("msgType", this.msgType);
        obj.put("msg", this.getMsg());
        obj.put("obj", this.obj);
        obj.put("attributes", this.attributes);
        obj.put("type", this.type);
        return obj.toJSONString();
    }

    public static Map<String, Object> failedResultMap(int type, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("type", type);
        result.put("message", message);
        result.put("success", false);
        return result;
    }

    @Override
    public String toString() {
        return "AjaxJson{" +
                "success=" + success +
                ", msgType=" + msgType +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                ", attributes=" + attributes +
                ", type=" + type +
                '}';
    }
}
