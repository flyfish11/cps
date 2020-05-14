package com.cloud.file.controller;

import com.cloud.file.service.MinioTemplate;
import com.cloud.file.vo.MinioItem;
import com.cloud.file.vo.MinioObject;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Classname MinioEndpoint
 * @Description minio 对外提供服务端点
 * @Author yulj
 * @Date: 2019/07/31 01:00
 */
@ConditionalOnProperty(name = "minio.endpoint.enable", havingValue = "true")
@RestController
@AllArgsConstructor
@RequestMapping("${minio.endpoint.name:/minio}")
public class MinioEndpoint {
    private final MinioTemplate template;

    /**
     * Bucket Endpoints
     */
    @SneakyThrows
    @PostMapping("/bucket/{bucketName}")
    public Bucket createBucker(@PathVariable String bucketName) {

        template.createBucket(bucketName);
        Optional<Bucket> bucket = template.getBucket(bucketName);
        if (bucket.isPresent()) {
            return  bucket.get();
        }
      return null;

    }

    @SneakyThrows
    @GetMapping("/bucket")
    public List<Bucket> getBuckets() {
        return template.getAllBuckets();
    }

    @SneakyThrows
    @GetMapping("/bucket/{bucketName}")
    public Bucket getBucket(@PathVariable String bucketName) {
        return template.getBucket(bucketName).orElseThrow(() -> new IllegalArgumentException("Bucket Name not found!"));
    }

    @SneakyThrows
    @DeleteMapping("/bucket/{bucketName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBucket(@PathVariable String bucketName) {
        template.removeBucket(bucketName);
    }

    /**
     * Object Endpoints
     */
    @SneakyThrows
    @PostMapping("/object/{bucketName}")
    public MinioObject createObject(@RequestBody MultipartFile object, @PathVariable String bucketName) {
        String name = object.getOriginalFilename();
        template.putObject(bucketName, name, object.getInputStream(), object.getSize(), object.getContentType());
        return new MinioObject(template.getObjectInfo(bucketName, name));

    }

    @SneakyThrows
    @PostMapping("/object/{bucketName}/{objectName}")
    public MinioObject createObject(@RequestBody MultipartFile object, @PathVariable String bucketName, @PathVariable String objectName) {
        template.putObject(bucketName, objectName, object.getInputStream(), object.getSize(), object.getContentType());
        return new MinioObject(template.getObjectInfo(bucketName, objectName));

    }

    @SneakyThrows
    @GetMapping("/object/{bucketName}/{objectName}")
    public List<MinioItem> filterObject(@PathVariable String bucketName, @PathVariable String objectName) {

        return template.getAllObjectsByPrefix(bucketName, objectName, true);

    }

    @SneakyThrows
    @GetMapping("/object/{bucketName}/{objectName}/{expires}")
    public Map<String, Object> getObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable Integer expires) {
        Map<String, Object> responseBody = new HashMap<>(8);
        // Put Object info
        responseBody.put("bucket", bucketName);
        responseBody.put("object", objectName);
        responseBody.put("url", template.getObjectURL(bucketName, objectName, expires));
        responseBody.put("expires", expires);
        return responseBody;
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/object/{bucketName}/{objectName}/")
    public void deleteObject(@PathVariable String bucketName, @PathVariable String objectName) {

        template.removeObject(bucketName, objectName);
    }


}
