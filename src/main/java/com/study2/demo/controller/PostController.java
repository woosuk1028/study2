package com.study2.demo.controller;

import com.study2.demo.dto.PostDto;
import com.study2.demo.entity.Post;
import com.study2.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String postPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> postPage = postService.getPagedPostList(page);
        model.addAttribute("postPage", postPage);
        return "post";
    }

    @GetMapping("/post/write")
    public String postWrite(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "write";
    }

    @PostMapping("/post/write")
    public String submit(@ModelAttribute PostDto postDto, Authentication auth) {
        String writer = auth.getName();
        postService.savePost(postDto, writer);
        return "redirect:/post";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        return "detail";
    }

    @PostMapping("/post/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        postService.deletePost(id);
        return "redirect:/post";
    }

}
