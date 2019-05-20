$(function(){
	$("a[id*=showBtn-]").each(function(){
		// 拆分id数据
		var gid = this.id.split("-")[1] ;
		$(this).on("click",function(){
			window.location = "pages/front/goods/goods_show.jsp?gid=" + gid ;
		})
	}) ;
}) ;