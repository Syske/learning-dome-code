package io.github.syske.websigndemo.controller;

import io.github.syske.websigndemo.entity.AjaxJson;
import io.github.syske.websigndemo.service.SignTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signTest")
public class SignTestController {
    @Autowired
    private SignTestService signTestService;

    @RequestMapping("/generatePersonalCommitment")
    @ResponseBody
    public AjaxJson generatePersonalCommitment(String datas) {
        return signTestService.generatePersonalCommitment(datas);
    }

    @RequestMapping("/signPersonalCommitment")
    @ResponseBody
    public AjaxJson signPersonalCommitment(String datas) {
        return signTestService.signPersonalCommitment(datas);
    }

}
