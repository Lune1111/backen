-- 评论表增加字段
ALTER TABLE `qx`.`qx_comment`
ADD COLUMN `report_number` varchar(255) NULL DEFAULT 0 COMMENT '举报数' AFTER `praise_number`;

-- 帖子表增加字段
ALTER TABLE `qx`.`qx_invitation`
ADD COLUMN `report_number` int(0) NULL DEFAULT 0 COMMENT '举报数' AFTER `comment_number`;

-- 举报表
CREATE TABLE `qx_report`  (
  `user_id` int(0) NOT NULL COMMENT '用户主键',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报类型',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志'
)
-- 举报表添加字段
ALTER TABLE `qx`.`qx_report`
ADD COLUMN `father_id` int(0) NOT NULL COMMENT '帖子评论举报' AFTER `user_id`;

-- 角色表添加背景图字段
ALTER TABLE `qx`.`qx_user`
ADD COLUMN `bground` varchar(255) NULL COMMENT '背景地址' AFTER `status`;