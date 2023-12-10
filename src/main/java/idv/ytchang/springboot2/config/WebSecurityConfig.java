package idv.ytchang.springboot2.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import idv.ytchang.springboot2.service.SpringUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	SpringUserService springUserService;
	
	@Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		
		http
		.authorizeRequests() // 定義須被保護URL		
		.requestMatchers("/").permitAll() // 入口 URL'/' 不須驗證	
		.requestMatchers("/css/*").permitAll() // css 不須驗證	
		.requestMatchers("/js/*").permitAll() // javascripts 不須驗證	
		.requestMatchers("/images/*").permitAll() // 圖檔 不須驗證	
		.requestMatchers("/fonts/*").permitAll() // 字型 不須驗證	
		.requestMatchers("/about").permitAll() // URL'/about' 不須驗證
		.requestMatchers("/testimonial").permitAll() // URL'/testimonial' 不須驗證
		.requestMatchers("/contact").permitAll() // URL '/contact' 不須驗證
		.requestMatchers("/admin").hasRole("ADMIN") // ROLE_ADMIN角色可訪問 /admin 底下網址
		.requestMatchers("/user").hasRole("USER") // ROLE_USER角色可訪問 /user 底下網址
		.anyRequest().authenticated() // 其他尚未匹配的URL不限角色，仍要驗證再使用		
		.and()
		.rememberMe() // 保持登錄狀態
		.and()
		.formLogin((form) -> form.loginPage("/login").permitAll()) // 自訂登入頁面
		.logout((logout) -> logout.permitAll())	// 自訂登出跳轉	
		.httpBasic(); // 基本HTTP驗證
		
		return http.build();
	}
	
	
	/**
	 * 執行這一段，產生UUID、打印加密通行碼
	 * @param args
	 */
	public static void main(String... args) {
		// 產生UUID
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		
		
		// 指定加密機制. 並加密通行碼
		PasswordEncoder bCryptPasswordEncoder = passwordEncoder();
		String adminPassword = bCryptPasswordEncoder.encode("abc123"); // password for admin
		String user1Password = bCryptPasswordEncoder.encode("2234"); // password for user1
		
		System.out.println(adminPassword);
		System.out.println(user1Password);
				
	}
	

}
