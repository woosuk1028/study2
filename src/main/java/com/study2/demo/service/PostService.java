package com.study2.demo.service;

import com.study2.demo.dto.PostDto;
import com.study2.demo.entity.Post;
import com.study2.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getPagedPostList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        return postRepository.findAll(pageable);
    }

    public void savePost(PostDto dto, String writer) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setWriter(writer);
        post.setCreateDate(LocalDateTime.now());
        post.setUpdateDate(LocalDateTime.now());

        if(!dto.getFile().isEmpty()) {
            String baseUrl = "http://localhost:8080/upload-dir/";
            String filename = dto.getFile().getOriginalFilename();
            String uploadRoot = System.getProperty("user.dir") + "/upload-dir";
            Path savePath = Paths.get(uploadRoot, filename);

            try {
                Files.createDirectories(savePath.getParent());
                dto.getFile().transferTo(savePath.toFile());
                post.setFile(baseUrl + filename);
            } catch(IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }

        }

        postRepository.save(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
