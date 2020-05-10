<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="../includes/header.jsp"%>

<!-- BREADCRUMB -->
<nav class="py-5">
   <div class="container">
      <div class="row">
         <div class="col-12">

            <!-- Breadcrumb -->
            <ol class="breadcrumb mb-0 font-size-xs text-gray-400">
               <li class="breadcrumb-item"><a class="text-gray-400" href="index.html">Home</a></li>
               <li class="breadcrumb-item active"><a class="text-gray-400" href="/shop/boradList">게시판</a></li>
               <li class="breadcrumb-item active"><a class="text-gray-400" href="/shop/getBoradList">글 목록</a></li>
               <li class="breadcrumb-item active">수정하기</li>
            </ol>

         </div>
      </div>
   </div>
</nav>

<section class="col-12">
<div class="container">

<div class="row">
   <div class="col-lg-12">
      <h1 class="page-header text-center">게시글 수정</h1>
   </div>
   <!--  /.col-lg-12 -->
</div>
<!--  /.row -->


<div class="row">
   <div class="col-lg-12">
      <div class="panel panel-default">
         <!--  /.panel-heading -->
         <div class="panel-body">
            
            <form role="form" action="/shop/modifyBoardList" method="post">
            
             <!-- 추가 -->
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum }" />'>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount }" />'>
            <input type='hidden' name='type' value='<c:out value="${cri.type }" />'>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword }" />'>
            
               <div class="from-group">
               <label>게시판 번호</label> <input class="form-control" name='b_no' 
                                    value='<c:out value="${board.b_no }"/>'readonly="readonly">
               </div>
               <div class="form-group">
                  <label>제목</label> <input class="form-control" name='b_title'
                                    value='<c:out value="${board.b_title }" />' >
               </div>
               
               <div class="form-group">
                  <label>내용</label>
                  <textarea class="form-control" rows="3" name='b_content'
                              ><c:out value="${board.b_content }" /></textarea>
               </div>
                  
               <%-- <div class="form-group">
                  <label>등록일</label>
                  <input class="form-control" name='b_regdate' type="hidden"
                     value='<fmt:formatDate pattern = "yyyy/MM/dd" value="${board.b_regdate}" />' readonly="readonly">
               </div>
               
               <div class="form-group">
                  <label>수정일</label>
                  <input class="form-control" name='b_updatedate' type="hidden"
                     value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.b_updatedate }" />' readonly="readonly">
               </div> --%>
               
               <div class="form-group">
               <label>회원번호</label> <input type ="hidden" class="form-control" name="m_no" value='<sec:authentication property="principal.member.m_no"/>' readonly="readonly">
               </div>
               <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" >
               <button type="submit" data-oper='modify' class="btn btn-default" >수정</button>
               <button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
               <button type="submit" data-oper='list' class="btn btn-info" >목록으로</button>
         </form>
         </div>
         <!--  end panel-body  -->
      </div>
      <!--  end panel-body -->
   </div>
   <!--  end panel-body  -->
</div>
<!--  /.row -->
</div>
</section>

<script type="text/javascript">
$(document).ready(function(){
   
   var formObj = $("form");
   
   $('button').on("click", function(e){
      
      e.preventDefault();
      
      var operation = $(this).data("oper");
      
      console.log(operation);
      
      if(operation === 'remove'){
         formObj.attr("action", "/shop/removeBoardList");
      } else if(operation === 'list'){
         //move to list
         formObj.attr("action", "/shop/boardList").attr("method", "get");
         var pageNumTag = $("input[name='pageNum']").clone();
         var amountTag = $("input[name='amount']").clone();
         var keywordTag = $("input[name='keyword']").clone();
         var typeTag = $("input[name='type']").clone();
         
         formObj.append(pageNumTag);
         formObj.append(amountTag);
         formObj.append(keywordTag);
         formObj.append(typeTag);
         formObj.empty();
      }
      formObj.submit();
   });
});
</script>


<%@ include file="../includes/footer.jsp" %>