package com.lxy.common;

import lombok.Data;

@Data
public class JsonResult {
    private boolean success;
    private String status;
    private String msg;
    private Object data;
}
