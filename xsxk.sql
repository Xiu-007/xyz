/*
 Navicat Premium Data Transfer

 Source Server         : 123456
 Source Server Type    : MySQL
 Source Server Version : 80013 (8.0.13)
 Source Host           : localhost:3307
 Source Schema         : xsxk

 Target Server Type    : MySQL
 Target Server Version : 80013 (8.0.13)
 File Encoding         : 65001

 Date: 22/05/2025 16:47:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9090/files/download/avatar.png', 'ADMIN');

-- ----------------------------
-- Table structure for choice
-- ----------------------------
DROP TABLE IF EXISTS `choice`;
CREATE TABLE `choice`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名称',
  `teacher_id` int(10) NULL DEFAULT NULL COMMENT '授课教师',
  `student_id` int(10) NULL DEFAULT NULL COMMENT '选课学生',
  `course_id` int(10) NULL DEFAULT NULL COMMENT '课程ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '选课信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of choice
-- ----------------------------
INSERT INTO `choice` VALUES (5, 'Java基础', 3, 2, 2);
INSERT INTO `choice` VALUES (6, 'python基础', 6, 2, 1);
INSERT INTO `choice` VALUES (8, 'Java基础', 3, 4, 2);
INSERT INTO `choice` VALUES (9, 'Go语言', 3, 4, 4);
INSERT INTO `choice` VALUES (11, 'python基础', 6, 4, 1);

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '学院描述',
  `score` int(10) NULL DEFAULT NULL COMMENT '最低学分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学院信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '物流工程学院', '这是物流工程学院，牛逼', 30);
INSERT INTO `college` VALUES (2, '经济管理学院', '这是经济管理学院，厉害厉害', 40);
INSERT INTO `college` VALUES (6, '马克思主义学院', '	\n这是马克思主义学院，牛逼plus', 30);
INSERT INTO `college` VALUES (8, '软件学院', '牛逼plus', 40);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '课程介绍',
  `score` int(10) NULL DEFAULT NULL COMMENT '课程学分',
  `teacher_id` int(10) NULL DEFAULT NULL COMMENT '授课教师',
  `num` int(10) NULL DEFAULT NULL COMMENT '开班人数',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课时间',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课地点',
  `college_id` int(10) NULL DEFAULT NULL COMMENT '所属学院',
  `already_num` int(10) NULL DEFAULT 0 COMMENT '已选人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '课程信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'python基础', '学习python基础，夯实基础。', 3, 6, 30, '周二第四大节', '2B202', 7, 2);
INSERT INTO `course` VALUES (2, 'Java基础', '学习Java基础，为项目开发打好基础。', 3, 3, 30, '周三第四大节', '3B303', 7, 2);
INSERT INTO `course` VALUES (3, 'C语言', '学习C语言', 3, 4, 40, '周五第一大节', '4B404', 7, 0);
INSERT INTO `course` VALUES (4, 'Go语言', '这是Go语言', 4, 3, 40, '周二第三大节', '2B203', 7, 1);

-- ----------------------------
-- Table structure for dean
-- ----------------------------
DROP TABLE IF EXISTS `dean`;
CREATE TABLE `dean`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '院长信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dean
-- ----------------------------
INSERT INTO `dean` VALUES (10, 'yang', '123456', '杨副院长', '男', 'DEAN', 'http://localhost:9091/files/download/1747018866385-微信图片_20250508104046.jpg');

-- ----------------------------
-- Table structure for documents
-- ----------------------------
DROP TABLE IF EXISTS `documents`;
CREATE TABLE `documents`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文档名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文档类型',
  `filePath` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件存储路径',
  `fileSize` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件大小(字节)',
  `fileType` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件类型',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '审核状态',
  `teacherOpinion` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师审批意见',
  `deanOpinion` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '院长审批意见',
  `uploadTime` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `userId` int(20) NOT NULL COMMENT '上传用户ID',
  `createdAt` datetime NOT NULL COMMENT '创建时间',
  `updatedAt` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`userId` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_type`(`type` ASC) USING BTREE,
  INDEX `idx_upload_time`(`uploadTime` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文档管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of documents
-- ----------------------------
INSERT INTO `documents` VALUES (10, '2400131241粟泳璋.docx', 'thesis', '.\\uploads\\b82e054b-cfb5-4fcd-8ac3-670d2f0c1971_2400131241粟泳璋.docx', '209052', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'TEACHER_PENDING', '发士大夫', '发顺丰的', '2025-05-14 12:26:21', 1, '2025-05-14 12:26:21', '2025-05-21 09:49:21');
INSERT INTO `documents` VALUES (14, 'a310efccc6e23c6a862754d42e662c5c.pdf', 'task', '.\\uploads\\d29421cb-a876-4de2-833a-c57c77c9a734_a310efccc6e23c6a862754d42e662c5c.pdf', '469531', 'application/pdf', 'TEACHER_REJECTED', '还需修改', NULL, '2025-05-17 15:38:33', 1, '2025-05-17 15:38:33', '2025-05-20 15:33:56');
INSERT INTO `documents` VALUES (15, '高校毕业论文管理系统.docx', 'proposal', '.\\uploads\\d6b5b4aa-bd22-46e2-add5-2db40462461b_高校毕业论文管理系统.docx', '22059', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'APPROVED', '没问题', NULL, '2025-05-17 21:40:19', 1, '2025-05-17 21:40:19', '2025-05-17 21:44:39');
INSERT INTO `documents` VALUES (16, '项目报告封面模版.docx', 'plagiarism', '.\\uploads\\a5616735-0a90-4ba8-99f1-dc0390a1ee6a_项目报告封面模版.docx', '18056', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'APPROVED', '1111', NULL, '2025-05-20 14:23:14', 1, '2025-05-20 14:23:14', '2025-05-21 09:33:41');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '公告内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (6, '公告标题', '快结束了', '2025-05-22 09:23:57');

-- ----------------------------
-- Table structure for secretary
-- ----------------------------
DROP TABLE IF EXISTS `secretary`;
CREATE TABLE `secretary`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教务秘书信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of secretary
-- ----------------------------
INSERT INTO `secretary` VALUES (2, 'xiao', '123456', '肖秘书', '女', 'SECRETARY', 'http://localhost:9091/files/download/1746863580542-微信图片_20250508104110.png');

-- ----------------------------
-- Table structure for speciality
-- ----------------------------
DROP TABLE IF EXISTS `speciality`;
CREATE TABLE `speciality`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业名称',
  `college_id` int(10) NULL DEFAULT NULL COMMENT '学院ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '专业信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of speciality
-- ----------------------------
INSERT INTO `speciality` VALUES (1, '计算机科学与技术', 8);
INSERT INTO `speciality` VALUES (2, '软件工程', 8);
INSERT INTO `speciality` VALUES (4, '马克思主义哲学', 6);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `teacherId` int(10) NULL DEFAULT NULL COMMENT '指导老师ID',
  `assignedTeacher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '指导老师名字',
  `college_id` int(10) NULL DEFAULT NULL COMMENT '学院ID',
  `score` int(10) NULL DEFAULT NULL COMMENT '学分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'zhangsan', '123456', '张三', 'STUDENT', '男', '10000', 'http://localhost:9091/files/download/1722324698822-柴犬.jpeg', 3, NULL, NULL, NULL);
INSERT INTO `student` VALUES (2, 'lisi', '123456', '李四', 'STUDENT', '男', '10001', 'http://localhost:9091/files/download/1722324706756-拉布拉多.jpeg', NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES (3, 'wangwu', '123456', '王五', 'STUDENT', '男', '10002', 'http://localhost:9091/files/download/1722324732342-柯基.jpeg', NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES (4, 'zhaoliu', '123', '赵六', 'STUDENT', '男', '10003', 'http://localhost:9091/files/download/1722329105934-金毛.jpeg', NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES (5, 'liqi', '123456', '李七', 'STUDENT', '女', '10004', 'http://localhost:9091/files/download/1722329141458-柯基.jpeg', 3, NULL, NULL, NULL);
INSERT INTO `student` VALUES (6, 'xiaolijun', '123456', '肖黎军', 'STUDENT', '女', NULL, 'http://localhost:9091/files/download/1746750217808-微信图片_20250508104110.png', 6, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职称',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `speciality_id` int(10) NULL DEFAULT NULL COMMENT '专业ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '教师信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (3, 'zhang', '123456', '张老师', '女', '教授', 'TEACHER', 'http://localhost:9091/files/download/1722322696451-柴犬.jpeg', NULL);
INSERT INTO `teacher` VALUES (4, 'li', '123', '李老师', '男', '副教授', 'TEACHER', 'http://localhost:9091/files/download/1722323069575-柯基.jpeg', NULL);
INSERT INTO `teacher` VALUES (6, 'wang', '123456', '王老师', '男', '副教授', 'TEACHER', 'http://localhost:9091/files/download/1722323075913-拉布拉多.jpeg', NULL);

SET FOREIGN_KEY_CHECKS = 1;
