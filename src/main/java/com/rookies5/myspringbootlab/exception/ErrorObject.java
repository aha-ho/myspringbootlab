package com.rookies5.myspringbootlab.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private int statusCode;
    private String message;
    private long timestamp;
}