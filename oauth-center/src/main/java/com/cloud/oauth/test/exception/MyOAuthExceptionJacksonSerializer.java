package com.cloud.oauth.test.exception;

import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @Description 定义异常MyOAuth2Exception的序列化
 * @Author wwz
 * @Date 2019/07/11
 * @Param
 * @Return
 */
public class MyOAuthExceptionJacksonSerializer extends StdSerializer<MyOAuth2Exception>
{

    protected MyOAuthExceptionJacksonSerializer() {
        super(MyOAuth2Exception.class);
    }

    @Override
    public void serialize(MyOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        Result error = ResultUtil.error(value.getHttpErrorCode(), value.getSummary());
        jgen.writeStartObject(error);
        jgen.writeEndObject();
    }
}
