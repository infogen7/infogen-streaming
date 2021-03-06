package com.infogen.hdfs;

import java.io.IOException;

/**
 * @author larry/larrylv@outlook.com/创建时间 2015年12月15日 上午11:14:30
 * @since 1.0
 * @version 1.0
 */
public interface InfoGen_OutputFormat {
	public void write_line(String path, String message) throws IllegalArgumentException, IOException;

	public Integer number_io();

	public Boolean close_all();

	public void setStart_offset(Long start_offset);
}
