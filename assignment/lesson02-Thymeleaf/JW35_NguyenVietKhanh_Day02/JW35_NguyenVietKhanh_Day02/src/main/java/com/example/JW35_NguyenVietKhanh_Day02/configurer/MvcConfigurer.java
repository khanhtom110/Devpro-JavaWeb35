package com.example.JW35_NguyenVietKhanh_Day02.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.JW35_NguyenVietKhanh_Day02.dto.Constant;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer, Constant {
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/UploadFiles/**").addResourceLocations("file:" + FOLDER_UPLOAD);
	}
}
