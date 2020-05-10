package com.sss.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sss.domain.MemberVO;
import com.sss.mapper.MemberMapper;
import com.sss.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService{
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("디테일 서비스 지나감");
		log.warn("Load User By UserName : " + username);
		MemberVO vo = memberMapper.readByEmail(username);
		log.warn("queried by member mapper : " + vo);
		return vo==null? null : new CustomUser(vo);
	}
}
