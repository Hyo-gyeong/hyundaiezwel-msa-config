package com.msa.comments_api.controller;

import com.msa.comments_api.record.Comment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommentController {

    //    zipkin trace 기록
    private static final Log logger = LogFactory.getLog(CommentController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/comments")
    public Comment[] comments(@RequestParam(defaultValue = "1") String postId) {
        logger.info("comments() has been called");
        Comment[] comments = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments?postId="+postId, Comment[].class);
        return comments;
    }
}
