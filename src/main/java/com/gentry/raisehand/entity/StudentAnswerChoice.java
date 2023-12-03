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
public class StudentAnswerChoice implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer studentId;

    private Integer questionId;

    private Integer answerId;
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
    @TableLogic(value = "0", delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteFlag;
    private Integer lectureId;

}
