package com.forDragon.BookCatalog.service;

public interface IDataConverter {
    <T> T convertData(String json, Class<T> clase);
}
