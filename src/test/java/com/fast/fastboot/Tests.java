package com.fast.fastboot;

import com.alibaba.fastjson.JSON;
import com.fast.fastboot.common.exception.ErrorCode;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class Tests {
	@Test
	public void test() {
		System.out.println(JSON.toJSONString(ErrorCode.PERMISSION_DENIED));
	}


}
