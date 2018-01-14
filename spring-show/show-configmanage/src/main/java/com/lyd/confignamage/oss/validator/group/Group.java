package com.lyd.confignamage.oss.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * 	本项目未用到
 * @author lyd
 * @date 2017年10月18日
 * @version 1.0
 * @CSDN http://blog.csdn.net/it_lyd
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
