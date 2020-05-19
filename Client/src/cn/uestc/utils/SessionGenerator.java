package cn.uestc.utils;

import org.springframework.util.DigestUtils;

public class SessionGenerator {//session随机生成方式：根据当前时间字符串生成

	public String gene(String time) {
		String md5 = DigestUtils.md5DigestAsHex(time.getBytes());
		return md5.substring(md5.length()-8, md5.length());
	}
	
}
