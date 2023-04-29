-- App关系表
CREATE TABLE `qx_relation`  (
  `from_id` int(0) NOT NULL COMMENT '发起方',
  `to_id` int(0) NOT NULL COMMENT '接收方',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '1关注2拉黑',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 新增菜单
INSERT INTO `sys_menu` VALUES (2003, '评论管理', 2000, 3, 'comment ', 'app/comment/index', '', 1, 0, 'C', '0', '0', 'app:comment:list', 'message', 'admin', '2023-04-07 15:58:31', 'admin', '2023-04-07 16:03:47', '');
INSERT INTO `sys_menu` VALUES (2004, '点赞管理', 2000, 4, 'liskes', 'app/likes/index', NULL, 1, 0, 'C', '0', '0', 'app:likes:list', 'button', 'admin', '2023-04-07 16:48:26', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '话题管理', 2000, 5, 'subject', 'app/subject/index', NULL, 1, 0, 'C', '0', '0', 'app:subject:list', 'component', 'admin', '2023-04-07 18:48:24', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '关系管理', 2000, 6, 'relation', 'app/relation/index', NULL, 1, 0, 'C', '0', '0', 'app:relation:list', 'peoples', 'admin', '2023-04-09 09:53:58', '', NULL, '');

-- 新增字段评论数点赞数
ALTER TABLE `qx`.`qx_invitation`
ADD COLUMN `comment_number` int(11) ZEROFILL NOT NULL DEFAULT 0 COMMENT '评论数' AFTER `content`,
MODIFY COLUMN `praise_number` int(11) ZEROFILL NULL DEFAULT NULL COMMENT '点赞数' AFTER `content`;

-- App评论表
CREATE TABLE `qx_comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `father_id` bigint(0) NOT NULL COMMENT '父主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户主键',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论类型',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4
-- 修改帖子表点赞数字段
ALTER TABLE `qx`.`qx_invitation`
MODIFY COLUMN `praise_number` int(11) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '点赞数' AFTER `content`;
-- 添加评论表点赞数字段
ALTER TABLE `qx`.`qx_comment`
ADD COLUMN `praise_number` bigint(0) NOT NULL DEFAULT 0 COMMENT '点赞数' AFTER `content`;
-- 修改评论表
ALTER TABLE `qx`.`qx_comment`
DROP COLUMN `type`,
CHANGE COLUMN `father_id` `reply_user_id` bigint(0) NOT NULL COMMENT '回复的userId' AFTER `user_id`,
ADD COLUMN `invitation_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '帖子id' AFTER `reply_user_id`,
ADD COLUMN `parent_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '评论父id' AFTER `invitation_id`;