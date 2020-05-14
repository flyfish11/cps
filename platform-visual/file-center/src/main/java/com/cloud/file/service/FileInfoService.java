package com.cloud.file.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.file.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Classname FileInfoService
 * @Description 文件管理service层
 * @Author yulj
 * @Date: 2019/07/31 14:48
 */
public interface FileInfoService {

    /**
     * 分页查询文件数据
     *
     * @param params
     * @return
     */
    Page<FileInfo> list(Map<String, Object> params);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    R uploadFile(MultipartFile file);

    /**
     * 根据文件id删除文件
     *
     * @param id
     * @return
     */
    R deleteFile(String id);

    /**
     * 根据文件名称删除文件
     *
     * @param fileName
     * @return
     */
    R deleteFileByName(String fileName);

    /**
     * 读取文件
     *
     * @param fileName
     * @param response
     */
    void getFile(String fileName, HttpServletResponse response);
}
