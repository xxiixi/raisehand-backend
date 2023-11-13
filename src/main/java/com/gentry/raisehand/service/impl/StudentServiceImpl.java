package com.gentry.raisehand.service.impl;

import com.gentry.raisehand.entity.Student;
import com.gentry.raisehand.mapper.StudentMapper;
import com.gentry.raisehand.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyt
 * @since 2023-11-13
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
