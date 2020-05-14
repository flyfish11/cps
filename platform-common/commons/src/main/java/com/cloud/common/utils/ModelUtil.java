package com.cloud.common.utils;

import com.cloud.common.vo.FieldAndCommentVo;
import com.cloud.model.annotations.Display;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Classname ModelUtil
 * @Description 实体工具类
 * @Author yulj
 * @Date: 2019/08/17 11:13
 */
public class ModelUtil {

    private ModelUtil() {throw new IllegalStateException("Utility class"); }

    /**
     * 根据实体类，拿到属性及属性中文描述
     *
     * @param cls
     * @return
     */
    public static List<FieldAndCommentVo> getFieldAndComment(Class<?> cls) {
        List<FieldAndCommentVo> fieldAndCommentVoList = new ArrayList<>();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            // 若属性上存在Display注解则跳过该属性
            if (field.isAnnotationPresent(Display.class)) {
                continue;
            }
            // 若属性上存在ApiModelProperty注解则取该注解的value值
            if (field.isAnnotationPresent(ApiModelProperty.class)) {
                ApiModelProperty fieldAnno = field.getAnnotation(ApiModelProperty.class);
                FieldAndCommentVo fieldAndCommentVo = FieldAndCommentVo.builder()
                        .label(fieldAnno.value())
                        .prop(field.getName())
                        .isDateField(field.getType().equals(Date.class))
                        .build();
                fieldAndCommentVoList.add(fieldAndCommentVo);
            }
        }
        return fieldAndCommentVoList;
    }
}
