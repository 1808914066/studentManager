<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	function studentDelete(studentId,disabled) {
		if(confirm("您确定要删除/激活这个学生吗？")) {
			window.location="student.action?action=deleteOrActive&id="+studentId+"&disabled="+disabled;
		}
	}
	//文档加载完成之后
	window.onload=function(){
		var studentdormBuildId="${dormBuildId}";
		//获取菜单select标签
		var dormBuildIdSelect = document.getElementById("dormBuildId");
		//获取下拉框中所有的option
		var options = dormBuildIdSelect.options;
		
		//遍历菜系select标签中所有的option标签
		$.each(options, function(i, option){
		  $(option).attr("selected",option.value == studentdormBuildId);
		})
		
		var searchType="${searchType}";
		//获取后台保存的当前要修改的foodTypeId值
		//获取菜单select标签
		var searchTypeSelect = document.getElementById("searchType");
		//获取下拉框中所有的option
		var  options2 = searchTypeSelect.options;
		//遍历菜系select标签中所有的option标签
		$.each( options2, function(i, option){
		  $(option).attr("selected",option.value == searchType);
		})
	}}
	
	
	$(document).ready(function(){
		$("#student").addClass("active");
	});
</script>
<style type="text/css">
	.span6 {
		width:0px;
		height: 0px;
		padding-top: 0px;
		padding-bottom: 0px;
		margin-top: 0px;
		margin-bottom: 0px;
	}

</style>
<div class="data_list">
		<div class="data_list_title">
			学生管理
		</div>
		<form name="myForm" class="form-search" method="post" action="student.action?action=list" style="padding-bottom: 0px">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='student.action?action=preAdd'">添加</button>
				<span class="data_search">
						<select id="dormBuildId" name="dormBuildId" style="width: 110px;">
							<option value="">全部宿舍楼</option>
							<c:forEach items="${builds }" var="build">
							<option value="${build.id }">${build.name }</option>
							</c:forEach>
						</select>
					
					<select id="searchType" name="searchType" style="width: 80px;">
						<option value="name">姓名</option>
						<option value="stuCode">学号</option>
						  <option value="dormCode">宿舍编号</option>
						<option value="sex">性别</option>
						<option value="tel">电话号码</option>
					</select>
					&nbsp;<input id="keyword" name="keyword" value="${keyword}" type="text"  style="width:120px;height: 30px;" class="input-medium search-query">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>宿舍楼</th>
					<th>寝室编号</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				
					 	<c:forEach items="${students}" var="student">
						<tr>
							<td>${student.stuCode}</td>
							<td>${student.name}</td>
							<td>${student.sex}</td>
							<td>${student.dormBuild.name}</td>
							<td>${student.dormCode}</td>
							<td>${student.tel}</td>
							<td>
								<button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='student.action?action=preUpdate&id=${student.id}'">修改</button>&nbsp;
								<c:if test="${student.disabled == 0}">
									<button class="btn btn-mini btn-danger" type="button" onclick="studentDelete(${student.id},1)">删除</button>
								</c:if>
								<c:if test="${student.disabled == 1}">
									<button class="btn btn-mini btn-danger" type="button" onclick="studentDelete(${student.id},0)">激活</button>	
								</c:if>
							</td>
						</tr>
				</c:forEach> 
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red"></font></div>
</div>