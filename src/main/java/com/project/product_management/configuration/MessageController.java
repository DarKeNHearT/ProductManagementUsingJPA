package com.project.product_management.configuration;


import com.project.product_management.configuration.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import com.project.product_management.configuration.dto.Message;

import java.security.Principal;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
    }

}
