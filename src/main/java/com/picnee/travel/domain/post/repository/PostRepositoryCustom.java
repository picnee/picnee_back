package com.picnee.travel.domain.post.repository;

import com.picnee.travel.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findByPosts(Pageable pageable);
}
