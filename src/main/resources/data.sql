DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `user_id` VARCHAR(36) NOT NULL,
    `username` VARCHAR(15) NOT NULL,
    `phone_number` VARCHAR(11) NOT NULL UNIQUE,
    `birth_date` VARCHAR(6) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) NOT NULL,
    `gender` CHAR(1) NOT NULL,
    `social_root` VARCHAR(20),
    `password` VARCHAR(255) NOT NULL,
    `password_count` INT NOT NULL DEFAULT 0,
    `account_lock` BOOLEAN NOT NULL,
    `last_password_expired` TIMESTAMP NOT NULL,
    `profile_image` VARCHAR(255),
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
    `target_type` CHAR(3) NOT NULL,
    `report_type` CHAR(3) NOT NULL,
    `is_visible` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`report_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `notification` (
    `notification_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(36) NOT NULL,
    `target_id` VARCHAR(36) NOT NULL,
    `is_read` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL,
    `modified_at` TIMESTAMP NOT NULL,
    `deleted_at` TIMESTAMP NULL,
    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`notification_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);