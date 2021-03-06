<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
   uri="http://www.springframework.org/security/tags"%>
<%@ include file="../includes/header.jsp"%>
<link
   href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap"
   rel="stylesheet">

<!-- BREADCRUMB -->
<nav class="py-5 bg-light" style="font-family: 'Do Hyeon', sans-serif;">
   <div class="container">
      <div class="row">
         <div class="col-12">

            <!-- Breadcrumb -->
            <ol class="breadcrumb mb-0 font-size-xs text-gray-400">
               <li class="breadcrumb-item"><a class="text-gray-400"
                  href="index.html">Home</a></li>
               <li class="breadcrumb-item active"><a class="text-gray-400"
                  href="/shop/boardList">게시판</a></li>
               <li class="breadcrumb-item active"><a class="text-gray-400"
                  href="/shop/getBoardList?b_no=${board.b_no }">글 목록</a></li>
               <li class="breadcrumb-item active">글 수정하기</li>
            </ol>

         </div>
      </div>
   </div>
</nav>

<section class="col-12 bg-light py-6"
   style="font-family: 'Do Hyeon', sans-serif;">
   <div class="container py-4"
      style="background-color: #fff; border: 1px solid #D8D8D8; border-radius: 10px">

      <div class="row py-3">
         <div class="col-12">
            <h1 class="page-header text-center" style="margin-left: 90px">게시글
               수정</h1>
            <hr>
         </div>
         <!--  /.col-lg-12 -->
      </div>
      <!--  /.row -->


      <div class="row">
         <div class="col-lg-12">
            <div class="panel panel-default">
               <!--  /.panel-heading -->
               <div class="panel-body">

                  <form id="modifyform" action="/shop/modifyBoardList" method="post">

                     <!-- 추가 -->
                     <input type='hidden' name='pageNum'
                        value='<c:out value="${cri.pageNum }" />'> <input
                        type='hidden' name='amount'
                        value='<c:out value="${cri.amount }" />'> <input
                        type='hidden' name='type' value='<c:out value="${cri.type }" />'>
                     <input type='hidden' name='keyword'
                        value='<c:out value="${cri.keyword }" />'>

                     <div class="from-group">
                        <label>게시판 번호</label> <input class="form-control" name='b_no'
                           value='<c:out value="${board.b_no }"/>' readonly="readonly" style="background:#fff" title="게시판 번호는 변경할 수 없습니다.">
                     </div>
                     <div class="form-group">
                        <label>제목</label> <input class="form-control" name='b_title'
                           value='<c:out value="${board.b_title }" />'>
                     </div>

                     <div class="form-group">
                        <label>내용</label>
                        <textarea class="form-control" rows="15" name='b_content'
                           style="resize: none"><c:out
                              value="${board.b_content }" /></textarea>
                     </div>

                     <label></label> <input type="hidden" class="form-control"
                        name="m_no"
                        value='<sec:authentication property="principal.member.m_no"/>'
                        readonly="readonly">
                     
                  <div>   
                     <input type="hidden" name="${_csrf.parameterName }"
                        value="${_csrf.token }">
                    
                        <button type="submit" data-oper='remove'
                           class="btn btn-outline-dark btn-s mb-1" title="글 삭제" style="float:right">삭제하기</button>
                        <button type="submit" data-oper='modify'
                           class="btn btn-outline-dark btn-s mb-1" title="글 수정" style="float:right; margin-right:5px">수정하기</button>
                        <button type="submit" data-oper='list'
                           class="btn btn-outline-dark btn-s mb-1" title="게시판으로 돌아가기">목록으로</button>
                 </div>    
                     
                </form>
            </div>
            <!--  end panel-body  -->
         </div>
         <!--  end panel-body -->
      </div>
      <!--  end panel-body  -->
   </div>
   <!--  /.row -->
</section>

<script type="text/javascript">
   $(document).ready(function() {
      var formObj = $("#modifyform");
      $('button').on("click",function(e) {
         e.preventDefault();
         var operation = $(this).data("oper");
         console.log(operation);
         if (operation === 'remove') 
         {
            formObj.attr("action","/shop/removeBoardList");
         } 
         else if (operation === 'list') 
         {
            //move to list
            formObj.attr("action","/shop/boardList").attr("method", "get");
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
         else{
        	 
         }
         formObj.submit();
      });
   });
</script>


<%@ include file="../includes/footer.jsp"%>