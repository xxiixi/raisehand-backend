package com.gentry.raisehand.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyt
 * @since 2023-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String studentEmail;

    private String studentPassword;

//  New Columns
    private String studentMobile;
    private String studentUserid;
    private String studentOpenid;
    private String studentAvatarUrl;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    private String studentName;
}
