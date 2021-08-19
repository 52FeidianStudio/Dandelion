package feidian.cloud.dandelion.controller;

import cn.hutool.http.HttpRequest;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.RestController;

import static feidian.cloud.dandelion.controller.DandelionController.*;

@RestController
public class DandelionHeart  extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        System.out.println("开始心跳检查");
        for ( String key : idRouteMap.keySet() ) {
            try {
                if( idRouteMap.get(key).isClient()) {

                    String url = idRouteMap.get(key).getUrl();
                    url = url + "/dandelion/heart";
                    System.out.println(url);
                    HttpRequest.post(url)
                            .timeout(5000)
                            .execute().body();
                }
            }catch (Exception e){
                idRouteMap.remove(key);
            }
        }
        System.out.println("结束心跳检查");
    }
}


