# DROP TABLE IF EXISTS `account`;
#
# CREATE TABLE `account`  (
#   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
#   `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户姓名',
#   `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户密码',
#   `roles` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户权限',
#   PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
#
# CREATE TABLE `user_roles`  (
#   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
#   `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
#   `role` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户权限',
#   PRIMARY KEY (`id`) USING BTREE,
#   INDEX `index_id`(`user_id`) USING BTREE,
#   CONSTRAINT `index_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
# ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;