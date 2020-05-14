package com.cloud.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.file.dao.FileInfoDao;
import com.cloud.file.service.FileInfoService;
import com.cloud.file.service.MinioTemplate;
import com.cloud.model.common.Page;
import com.cloud.model.file.FileInfo;
import com.cloud.model.user.LoginAppUser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname FileInfoServiceImpl
 * @Description 文件管理service层实现
 * @Author yulj
 * @Date: 2019/07/31 14:49
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService
{

    @Resource
    private FileInfoDao fileInfoDao;

    @Autowired
    private MinioTemplate minioTemplate;

    @Value("${minio.default-bucket}")
    private String defaultBucket;

    @Value("${file.preview.url}")
    private String filePreviewUrl;

    @Override
    public Page<FileInfo> list(Map<String, Object> params) {
        long total = this.fileInfoDao.count(params);

        PageUtil.pageUtil(params);
        List<FileInfo> fileInfoList = Lists.newArrayList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            fileInfoList = this.fileInfoDao.findList(params);
        }
        return new Page<>(total, fileInfoList);
    }

    @Override
    public R uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return R.failed("文件为空！");
        }

        String originalName = file.getOriginalFilename();
        Map<String, Object> resultData = new HashMap<>();
        String fileId = IdUtil.simpleUUID();
        String fileName = fileId + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        try {
            minioTemplate.putObject(defaultBucket, fileName, file.getInputStream());
            Long fileSize = file.getSize();
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(fileId);
            fileInfo.setBucketName(defaultBucket);
            fileInfo.setFileName(fileName);
            fileInfo.setOriginal(originalName);
            fileInfo.setType(FileUtil.extName(originalName));
            fileInfo.setFileSize(fileSize);
            if (loginAppUser != null && StringUtils.isNotBlank(loginAppUser.getAccount())) {
                fileInfo.setCreatedBy(loginAppUser.getAccount());

            }
            fileInfo.setCreatedTime(new Date());
            this.fileInfoDao.insertSelective(fileInfo);

            String fileUrl = filePreviewUrl + "/" + fileName;
            resultData.put("id", fileInfo.getId());
            resultData.put("path", fileUrl);
            resultData.put("src", fileUrl);
            resultData.put("fileName", originalName);
            resultData.put("minioFileName", fileName);
            resultData.put("fileSize", fileSize);
            return R.ok(resultData);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error("文件上传失败", e);
            return R.failed(e.getLocalizedMessage());
        }
    }

    @Override
    public R deleteFile(String id) {
        FileInfo fileInfo = this.fileInfoDao.selectById(id);
        String fileName = fileInfo.getFileName();
        log.info("删除文件{}", fileName);
        try {
            minioTemplate.removeObject(defaultBucket, fileName);
            this.fileInfoDao.deleteById(id);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return R.failed(e.getLocalizedMessage());
        }
        return R.ok();
    }

    @Override
    public R deleteFileByName(String fileName) {
        FileInfo fileInfo = this.fileInfoDao.findOneByFileName(fileName);
        if (fileInfo != null) {
            log.info("删除文件{}", fileName);
            try {
                minioTemplate.removeObject(defaultBucket, fileName);
                this.fileInfoDao.deleteById(fileInfo.getId());
            } catch (Exception e) {
                log.error("文件删除失败", e);
                return R.failed(e.getLocalizedMessage());
            }
        }
        return R.ok();
    }

    @Override
    public void getFile(String fileName, HttpServletResponse response) {
        try (InputStream inputStream = minioTemplate.getObject(defaultBucket, fileName)) {
            if (inputStream != null) {
                response.setContentType("application/octet-stream; charset=UTF-8");
                IoUtil.copy(inputStream, response.getOutputStream());
            }
        } catch (Exception e) {
            log.error("文件读取异常", e);
        }
    }
}
