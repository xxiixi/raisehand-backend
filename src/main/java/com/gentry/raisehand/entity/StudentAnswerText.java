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
 * @since 2023-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentAnswerText implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer studentId;

    private Integer questionId;

    private String answerContent;
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;


}
