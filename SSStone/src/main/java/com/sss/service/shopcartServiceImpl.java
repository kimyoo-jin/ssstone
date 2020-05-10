package com.sss.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sss.domain.Shopping_cartVO;
import com.sss.mapper.MemberMapper;
import com.sss.mapper.ProductMapper;
import com.sss.mapper.ShoppingCartMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Repository
@AllArgsConstructor
@Service
@Log4j

public class shopcartServiceImpl implements shopcartService{
	
	@Setter(onMethod_={@Autowired})
	private ShoppingCartMapper cartMapper;
	
	@Setter(onMethod_=@Autowired)
	private ProductMapper productMapper;
	
	@Setter(onMethod_=@Autowired)
	private MemberMapper memberMapper;

	
	//장바구니 등록	
	@Override
	public void insert(Shopping_cartVO vo) {
		
		cartMapper.insert(vo);
	}
	
	//상품 1개 지우기
	@Override
	public boolean delete(Long p_no) {
		
		log.info("delete:"+p_no);
		
		return cartMapper.delete(p_no) == 1;
	}
	
	//상품 모두 삭제(한 카트가 삭제됨)
	@Override
	public boolean deleteAll(Long m_no) {
		
		log.info("deleteAll:" + m_no);
		
		return cartMapper.deleteAll(m_no) ==1;
	}
	

	//shopcart의 product 총금액
	@Override
	public int sumMoney(Long m_no) {
		
		return cartMapper.sumMoney(m_no);
	}
	
	//한 카드의 상품 개수
	@Override
	public int countCart(Shopping_cartVO vo) {
		
		return 0;
	}
	
	//장바구니 리스트 보기
	@Override
	public List<Shopping_cartVO> listCart(Long m_no) {
		return cartMapper.listCart(m_no);
	}
	
	
	

	//총 카트의 개수
	@Override
	public long getamount(Shopping_cartVO vo) {
		
		
		return 0;
	}

}
