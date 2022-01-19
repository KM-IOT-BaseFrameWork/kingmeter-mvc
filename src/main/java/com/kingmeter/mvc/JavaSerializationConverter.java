package com.kingmeter.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;


public class JavaSerializationConverter extends AbstractHttpMessageConverter<Object> {
    /**
     * define character code as UTF8 to avoid mess
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF8");


    private Logger LOGGER = LoggerFactory.getLogger(JavaSerializationConverter.class);

    public JavaSerializationConverter() {
        super(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        final String tmp = StreamUtils.copyToString(inputMessage.getBody(),DEFAULT_CHARSET);

        return JSONObject.parseObject(tmp,clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        outputMessage.getBody().write(JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat).getBytes());
    }
}
