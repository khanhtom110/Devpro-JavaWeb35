package vn.devpro.javaweb35.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/UploadFiles/**").addResourceLocations(
				"file:D:/Workspace/Khoa_Hoc_Devpro/JavaWeb/class/lesson03-JPA/javaweb35/javaweb35/UploadFiles/");
	}
}
