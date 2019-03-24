package com.ganymede.rtservice.controller;

import com.alibaba.fastjson.JSON;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

@Controller
@RequestMapping("/RtInfoCollectService")
public class RtInfoCollectService {
    private final static Logger logger = LoggerFactory.getLogger(RtInfoCollectService.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/webInfoColService", method = RequestMethod.POST)
    public void webInfoColService(@RequestBody String jsonstr, HttpServletRequest request, HttpServletResponse response) {
        logger.info("webInfoColService 业务开始！ ");

        logger.info("未转换之前的： " + jsonstr);

        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setJsonMessage(jsonstr);
        kafkaMessage.setCount(1);
        kafkaMessage.setTimeStamp(new Date().getTime());
        jsonstr = JSON.toJSONString(kafkaMessage);

        logger.info("未转换之后的： " + jsonstr);

        kafkaTemplate.send("realtime", "key", jsonstr);


        response.setStatus(HttpStatus.OK.value());
        PrintWriter printWriter = getWriter(response);
        printWriter.write("success");
        closePrintwriter(printWriter);
        logger.info("webInfoColService 业务结束！ ");
    }

    private PrintWriter getWriter(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        OutputStream out = null;
        PrintWriter printWriter = null;
        try {
            out = response.getOutputStream();
            printWriter = new PrintWriter(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    private void closePrintwriter(PrintWriter printWriter) {
        printWriter.flush();
        printWriter.close();
    }
}
