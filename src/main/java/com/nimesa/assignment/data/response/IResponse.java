package com.nimesa.assignment.data.response;

public interface IResponse<T> {

    T exec() throws Exception;

}
