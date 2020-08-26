$(function(){
	var App = {
		//{{{ 初始化入口
		init : function(){
			//初始化系统
			$('#system-init').on('click',App.systemInit);
			//TODO
			$('#system-restart').on('click',App.reStart);
		},
		//}}}
		//{{{***********************************  系统初始化    ***********************************
		//{{{ 初始化系统
		systemInit : function(){
			// 过场动画 TODO

			// 删除数据库
			App.delDB();
		},
		//}}}
		//{{{ 删除数据库
		delDB : function(){
			$.ajax({
				url: 'sysinit',
				type:"POST",
				data:{m:'delDB'},
				success :function(e){
						if(e.result){
							App.reStart();
							console.log('数据库删除成功！！！');
						}
					}
			});
		},
		//}}}
		//{{{ 重启服务
		reStart : function(){
			debugger
			console.log('开始重启服务,注意查看进程状态！');
			// 启动服务
			$.ajax({
				url : 'sysinit',
				type: 'POST',
				data: {m:'restart'}
			});
			// 查询数据库是否存在，存在说明Tomcat 已启动
			App.isDbExists4Query();
			// 发现Tomcat 已启动，则查询Tomcat是否启动成功
			//setTimeout(App.queryStatus, 8000);
		},
		//}}}
		//{{{ 重启服务之后，查询数据库是否存在,决定是否开始 请求页面
		isDbExists4Query : function(){
			$.ajax({
				url : 'sysinit',
				type: 'POST',
				data: {m:'isDbExists'},
				success : function(e){
					if(!e.result){
						console.log('数据库未创建，代表 Tomcat 未重启！！！');
						setTimeout(App.isDbExists4Query, 3000);
					}else{
						console.log('数据库已创建， Tomcat 已重启！！！');
						App.queryStatus();
					}
				}
			});
		},
		//}}}
		//{{{ 查询服务状态
		queryStatus : function(){
			console.log('查询服务状态，准备跳转到登录界面！！');
			$.ajax({
			    url: "",
			    type: "POST",
			    data: {
			        xml: "<response></response>"
			    },
			    dataType:"text xml",
			    success: function(xml, textStatus, xhr) {
			    	console.log(arguments);
			        console.log(xhr.status);
			        // 跳转到主界面
			    },
			    complete: function(xhr, textStatus) {
			    	if(xhr.status == 500 ){
			    		 // 继续查询服务状态
			    	}else if(xhr.status == 200){
			    		// 跳转到登录页面
			    		App.toIndex();
			    	}else{
			    		console.log('不知名错误！！');
			    	}

			    }
			});
		},
		//}}}
		//{{{ 跳转到主页面
		toIndex : function(){
			window.location.href="http://127.0.0.1:8080/SdwHibernate/";
		},
		//}}}
		//}}}*********************************** 系统初始化 ***********************************
	};
	App.init();
});
