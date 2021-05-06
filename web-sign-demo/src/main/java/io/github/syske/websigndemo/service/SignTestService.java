package io.github.syske.websigndemo.service;

import io.github.syske.websigndemo.entity.AjaxJson;
import org.springframework.stereotype.Service;

@Service
public interface SignTestService {
    /**
     * 生成个人承诺书
     *
     * @param params 请求参数
     * @return 接口返回参数
     */
    AjaxJson generatePersonalCommitment(String params);

    /*
     * 个人承诺书签名
     * @param params 请求参数
     * @return 接口返回参数
     */
    AjaxJson signPersonalCommitment(String params);
}
