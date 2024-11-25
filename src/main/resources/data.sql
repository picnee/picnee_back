DROP TABLE IF EXISTS `review_vote_restaurant`;
DROP TABLE IF EXISTS `review_vote_accommodation`;
DROP TABLE IF EXISTS `review_vote_touristspot`;
DROP TABLE IF EXISTS `image`;
DROP TABLE IF EXISTS `users_review`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `like_place`;
DROP TABLE IF EXISTS `like_list`;
DROP TABLE IF EXISTS `users_post`;
DROP TABLE IF EXISTS `users_post_comment`;
DROP TABLE IF EXISTS `post_comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `place`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `user_id` VARCHAR(36) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(11) UNIQUE,
    `birth_date` VARCHAR(6),
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `nickname` VARCHAR(255) NOT NULL UNIQUE,
    `gender` VARCHAR(10),
    `social_root` VARCHAR(20),
    `password_count` INT NOT NULL DEFAULT 0,
    `account_lock` BOOLEAN NOT NULL,
    `last_password_expired` TIMESTAMP NOT NULL,
    `profile_image` VARCHAR(255),
    `is_default_nickname` BOOLEAN NOT NULL DEFAULT 0,
    `is_marketing` BOOLEAN NOT NULL,
    `is_alarm` BOOLEAN NOT NULL,
    `role` VARCHAR(10) NOT NULL,
    `state` VARCHAR(20),
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    `deleted_at` TIMESTAMP NULL,
    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `report` (
    `report_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `target_id` VARCHAR(36) NOT NULL,
    `report_target_type` VARCHAR(30) NOT NULL,
    `report_type` VARCHAR(30) NOT NULL,
    `is_visible` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`report_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `notification` (
    `notification_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `notification_type` VARCHAR(30) NOT NULL ,
    `target_id` VARCHAR(36) NOT NULL,
    `is_read` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`notification_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `board` (
    `board_id`	        VARCHAR(36)	    NOT NULL,
	`region`	        VARCHAR(255)	NOT NULL,
	`board_category`    VARCHAR(30)	    NOT NULL    COMMENT '식당 000/숙소 001 /관광지 002/여행정보 100',
	`created_at`	    TIMESTAMP	    NOT NULL,
	`modified_at`   	TIMESTAMP	    NOT NULL,
    `deleted_at`        TIMESTAMP       NULL,
    `is_deleted`        BOOLEAN         NOT NULL    DEFAULT FALSE,
    PRIMARY KEY (`board_id`)
);

CREATE TABLE `post` (
    `post_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `board_id` VARCHAR(36) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `viewed` BIGINT DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    `deleted_at` TIMESTAMP NULL,
    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`post_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`board_id`) REFERENCES `board`(`board_id`)
);

CREATE TABLE `post_comment` (
    `post_comment_id`	    VARCHAR(36)	NOT NULL,
	`content`	        LONGTEXT	NOT NULL,
    `likes`             BIGINT DEFAULT 0,
	`created_at`	    TIMESTAMP	NOT NULL,
	`modified_at`	    TIMESTAMP	NOT NULL,
	`deleted_at`	    TIMESTAMP	NULL,
	`is_deleted`	    BOOLEAN	    NULL	  DEFAULT FALSE,
	`post_id`	        VARCHAR(36)	NOT NULL,
	`post_comment_parent_id`	VARCHAR(36)	NULL,
    `user_id`           VARCHAR(36) NOT NULL,
    PRIMARY KEY (`post_comment_id`),
    FOREIGN KEY (`post_id`) REFERENCES `post`(`post_id`),
    FOREIGN KEY (`post_comment_parent_id`) REFERENCES `post_comment`(`post_comment_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `users_post`(
    `user_post_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `post_id` VARCHAR(36) NOT NULL,
    `is_viewed` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`user_post_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`post_id`) REFERENCES `post`(`post_id`)
);

CREATE TABLE `users_post_comment`(
    `user_post_comment_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `post_comment_id` VARCHAR(36) NOT NULL,
    `is_liked` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`user_post_comment_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`post_comment_id`) REFERENCES `post_comment`(`post_comment_id`)
);



CREATE TABLE `place` (
    `place_id`	         VARCHAR(36)     NOT NULL,
	`place_name`	     VARCHAR(255)    NOT NULL,
	`place_type`	     VARCHAR(30)	 NOT NULL	 COMMENT '식당 000/숙소 001 /관광지 002',
	`place_point`	     VARCHAR(255)    NOT NULL,
    `google_place_id`    VARCHAR(255)	 NOT NULL    UNIQUE,
	`created_at`	     TIMESTAMP	     NOT NULL,
	`modified_at`	     TIMESTAMP	     NOT NULL,
    PRIMARY KEY (`place_id`)
);

CREATE TABLE `like_list` (
	`like_list_id`	 VARCHAR(36)	NOT NULL,
	`like_list_name` VARCHAR(255)	NOT NULL	COMMENT '미입력 시, 서비스 단에서 임의 생성해주기',
	`created_at`     TIMESTAMP      NOT NULL,
    `modified_at`    TIMESTAMP      NOT NULL,
	`user_id`	     VARCHAR(36)	NOT NULL,
    PRIMARY KEY (`like_list_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `like_place` (
	`like_place_id`	 VARCHAR(36)	NOT NULL,
	`like_list_id`   VARCHAR(255)	NOT NULL,
    `place_id`       VARCHAR(255)	NOT NULL,
	`created_at`     TIMESTAMP      NOT NULL,
    `modified_at`    TIMESTAMP      NOT NULL,
    PRIMARY KEY (`like_place_id`),
    FOREIGN KEY (`like_list_id`) REFERENCES `like_list`(`like_list_id`),
    FOREIGN KEY (`place_id`) REFERENCES `place`(`place_id`)
);

CREATE TABLE `review` (
    `review_id`               VARCHAR(36)    NOT NULL,
	`title`                   VARCHAR(255)   NULL,
	`content`                 LONGTEXT       NULL,
	`is_vote_review`          BOOLEAN        NOT NULL    DEFAULT FALSE,
	`is_smoking`              BOOLEAN        NOT NULL    DEFAULT FALSE,
	`is_card`                 BOOLEAN        NOT NULL    DEFAULT FALSE,
	`is_korean_employee`      BOOLEAN        NOT NULL    DEFAULT FALSE,
	`is_korean_menu`          BOOLEAN        NOT NULL    DEFAULT FALSE,
	`recommendation_status`    VARCHAR(15)    NOT NULL    COMMENT '추천/보통/비추천',
	`created_at`              TIMESTAMP      NOT NULL,
	`modified_at`             TIMESTAMP      NOT NULL,
	`deleted_at`              TIMESTAMP      NULL,
	`is_deleted`              BOOLEAN	     NULL	     DEFAULT FALSE,
	`user_id`                 VARCHAR(36)    NOT NULL,
	`place_id`                VARCHAR(36)    NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`place_id`) REFERENCES `place`(`place_id`)
);

CREATE TABLE `review_vote_restaurant` (
	`review_id`               VARCHAR(36)    NOT NULL,
	`is_taste_positive`       BOOLEAN        NOT NULL,
	`is_ambience_positive`    BOOLEAN        NOT NULL,
	`is_service_positive`     BOOLEAN        NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review`(`review_id`)
);

CREATE TABLE `review_vote_accommodation` (
	`review_id`                    VARCHAR(36)    NOT NULL,
	`is_cleanliness_positive`      BOOLEAN        NOT NULL,
	`is_accessibility_positive`    BOOLEAN        NOT NULL,
	`is_service_positive`          BOOLEAN        NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review`(`review_id`)
);

CREATE TABLE `review_vote_touristspot` (
	`review_id`                    VARCHAR(36)    NOT NULL,
    `is_accessibility_positive`    BOOLEAN        NOT NULL,
	`is_crowded`                   BOOLEAN        NOT NULL,
	`is_experience_positive`       BOOLEAN        NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review`(`review_id`)
);

CREATE TABLE `users_review` (
    `user_review_id`    VARCHAR(36)    NOT NULL,
	`score`             INT            NOT NULL     DEFAULT 0,
	`created_at`        TIMESTAMP      NOT NULL,
	`modified_at`       TIMESTAMP      NOT NULL,
	`user_id`           VARCHAR(36)    NOT NULL,
	`review_id`         VARCHAR(36)    NOT NULL,
    PRIMARY KEY (`user_review_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review`(`review_id`)
);

CREATE TABLE `image` (
	`image_id`             VARCHAR(36)     NOT NULL,
	`image_url`            VARCHAR(255)    NOT NULL,
	`image_target_type`    VARCHAR(10)     NOT NULL    COMMENT '리뷰 000/게시글 001',
	`target_id`            VARCHAR(36)     NOT NULL    COMMENT '다른 테이블의 PK인 UUID',
	`created_at`           TIMESTAMP       NOT NULL,
	`modified_at`          TIMESTAMP       NOT NULL,
	`deleted_at`           TIMESTAMP       NULL,
	`is_deleted`           BOOLEAN         NOT NULL    DEFAULT FALSE,
    PRIMARY KEY (`image_id`)
);