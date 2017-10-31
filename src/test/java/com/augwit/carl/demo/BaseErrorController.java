package com.augwit.carl.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 5:56 PM 10/31/2017
 * @Modified By:
 */
@Controller
public class BaseErrorController implements ErrorController{
    private static final Logger logger= LoggerFactory.getLogger(BaseErrorController.class);

    @Override
    public String getErrorPath() {
        logger.info("There has a error! Please return prefix page");
        return "error/error";
    }
    @RequestMapping
    public String error(){
        return getErrorPath();
    }
}
