package cc.shallow.sbebean.controller;

import com.alibaba.fastjson.JSON;
import cc.shallow.sbebean.domain.param.RequestPage;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @RequestMapping("/error")
    public Demo test(@RequestBody Demo demo, RequestPage requestPage){
        System.out.println(JSON.toJSONString(requestPage));
        int i = 1 / 0;
        return demo;
    }

    @RequestMapping("/demo")
    public Demo demo(@RequestBody Demo demo, RequestPage requestPage){
        System.out.println(JSON.toJSONString(requestPage));
        return demo;
    }
}


@Data
class Demo{
    private String name;
    private String password;
}
