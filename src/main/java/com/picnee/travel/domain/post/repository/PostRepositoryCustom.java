package com.picnee.travel.domain.post.repository;

import com.picnee.travel.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PostRepositoryCustom {
    Page<Post> findByPosts(String boardCategory, String region, String sort, Pageable pageable);

    Page<Post> findMyPosts(UUID userId, Pageable pageable);
}
