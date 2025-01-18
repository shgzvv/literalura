package com.example.literalura.service.data;

public interface IDataConvert {
    <T> T convert(String json, Class<T> clase);
}
