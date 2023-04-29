package com.ruoyi.minio.entity;

import lombok.Data;

/**
 * @Author xiaozq
 * @Date 2022/11/17 10:05
 * <p>@Description:</p>
 */
@Data
public class ObjectItem {

    // 文件
    private String objectName;

    // 大小
    private Long size;

}
