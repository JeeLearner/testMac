package com.lyd.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.lyd.exception.web.MyException;

/**
 * hibernate-validator校验工具类 主要校验bean
 * 		参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 * @author lyd
 * @date 2017年9月15日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
public class ValidatorUtil {

	private static Validator validator;
	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	/**
	 * 校验对象
	 * 		
	 * @author lyd
	 * @date 2017年9月15日
	 * @param obj 待校验对象
	 * @param groups 待校验的组
	 */
	public static void validateEntity(Object obj, Class<?>... groups){
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj, groups);
		if(!constraintViolations.isEmpty()){
			ConstraintViolation<Object> constraintViolation = constraintViolations.iterator().next();
			throw new MyException(constraintViolation.getMessage());
		}
	}
}
