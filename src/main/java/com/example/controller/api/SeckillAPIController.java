package com.example.controller.api;

import com.example.base.controller.BaseApiController;
import com.example.base.result.Result;
import com.example.model.Orders;
import com.example.model.User;
import com.example.service.ISeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by think on 2020/6/7.
 */
@RestController
public class SeckillAPIController extends BaseApiController implements InitializingBean{

    @Autowired
    public ISeckillService seckillService;

    @RequestMapping(value="seckill/{courseNo}",method= RequestMethod.GET)
    public Result<Orders> seckill(User user, @PathVariable String courseNo){
        if(user == null){
            return Result.failure();
        }
        return seckillService.seckillFlow(user, courseNo);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        seckillService.cacheAllCourse();
    }

    @RequestMapping(value="seckillResult/{courseNo}",method=RequestMethod.GET)
    public Result<Orders> seckillResult(User user, @PathVariable String courseNo){
        if(user == null){
            return Result.failure();
        }
        return seckillService.seckillResult(user, courseNo);
    }


}
