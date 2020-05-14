package com.cloud.model.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Classname FileInfo
 * @Description 文件管理
 * @Author yulj
 * @Date: 2019/07/31 14:36
 */
@Data
@ToString
public class FileInfo {
    /**
     * 主键id
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 保存路径
     */
    private String bucketName;

    /**
     * 原文件名
     */
    private String original;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 上传人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}