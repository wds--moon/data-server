package com.dm.ds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "数据已经存在")
public class DuplicateDataException extends RuntimeException {

    private static final long serialVersionUID = 3282103035660377254L;

    public DuplicateDataException() {
        super("指定数据已经存在");
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
