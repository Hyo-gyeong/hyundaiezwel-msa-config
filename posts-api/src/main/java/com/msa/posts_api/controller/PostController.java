package com.msa.posts_api.controller;

import com.msa.posts_api.record.Comment;
import com.msa.posts_api.record.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostController {

    // zipkin trace 기록
    private static final Log logger = LogFactory.getLog(PostController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/posts")
    public Post[] greeting() {
        // record가 여러건이므로 배열로 변경
        return restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts", Post[].class);
    }

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/posts/{postId}")
    public Map greeting(@PathVariable String postId) {
        System.out.println("serverPort : "+serverPort);
        System.out.println("postId : " + postId);
        logger.info("posts() has been called");
        Post post = restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts/" + postId, Post.class);
        Comment[] comments = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments?postId=" + postId, Comment[].class);
        Map map = new HashMap();
        map.put("post", post);
        map.put("comments", comments);
        return map;
    }

}