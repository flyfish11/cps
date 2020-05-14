package com.cloud.file.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.file.service.FileInfoService;
import com.cloud.file.service.MinioTemplate;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.file.FileInfo;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Classname FileInfoController
 * @Description 文件管理控制器
 * @Author yulj
 * @Date: 2019/07/31 14:46
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private MinioTemplate minioTemplate;

    @Value("${minio.default-bucket}")
    private String defaultBucket;


    /**
     * 文件查询
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询文件信息", notes = "根据查询条件分页查询文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "name", value = "文件名称", required = false, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public Result findFiles(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<FileInfo> fileInfoPage = this.fileInfoService.list(params);
        return ResultUtil.success(fileInfoPage.getTotal(), fileInfoPage.getData());
    }

    /**
     * 通过id删除文件管理
     *
     * @param id id
     * @return R
     */
    @ApiOperation(value = "删除文件", notes = "通过文件id删除文件")
    @GetMapping("/delete")
    @LogAnnotation(serviceId = ServiceIdsConstants.FILE_CENTER, title = "删除文件")
    public Result removeById(@RequestParam String id) {
        this.fileInfoService.deleteFile(id);
        return ResultUtil.success();

    }

    /**
     * 通过文件名称删除文件
     *
     * @param fileName
     * @return
     */
    @ApiOperation(value = "删除文件", notes = "通过文件名称删除文件")
    @LogAnnotation(serviceId = ServiceIdsConstants.FILE_CENTER, title = "删除文件")
    @GetMapping("/deleteByName")
    public Result removeByFileName(@RequestParam String fileName) {
        this.fileInfoService.deleteFileByName(fileName);
        return ResultUtil.success();
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "支持多文件上传")
    @ApiImplicitParam(name = "file", value = "文件流对象", required = true, dataType = "MultipartFile", allowMultiple = true)
    @LogAnnotation(serviceId = ServiceIdsConstants.FILE_CENTER, title = "上传文件")
    public R upload(@RequestParam("file") MultipartFile file) {
        return this.fileInfoService.uploadFile(file);
    }

    /**
     * 获取文件
     *
     * @param fileName 文件空间/名称
     * @param response
     * @return
     */
    @GetMapping("/preview")
    @ApiOperation(value = "文件预览", notes = "根据fileName预览或下载文件")
    public void file(@RequestParam String fileName, HttpServletResponse response) {
        this.fileInfoService.getFile(fileName, response);
    }

}