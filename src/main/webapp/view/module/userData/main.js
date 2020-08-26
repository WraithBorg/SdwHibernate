$(function() {
	var App = {
		init : function() {
			$('#toregister').on('click',App.saveSome);
			$('#toquery').on('click', App.querySome);
			$('#totomcat').on('click',App.restartTomcat);
		},
		//{{{ 保存数据
		saveSome : function(){
			var ss = $('#yourformid').serialize();
			console.log(ss);
			$.ajax({
				cache : true,
				type : "POST",
				url : "user/register",
				data : $('#yourformid').serialize(), // 你的formid
				async : false,
				error : function(request) {
					alert("Connection error");
				},
				success : function(data) {
					console.log('$("#commonLayout_appcreshi").parent().html(data);');
				}
			});
		},	
		//}}}
		// {{{ 查询数据
		querySome : function() {
			var tableData;
			$.ajax({
				cache : true,
				type : 'POST',
				url : '../../../user',
				data : $.param({m : 'pageList'}), 
				async : false,
				error : function(e) {
				},
				success : function(e) {
					tableData = e.data.data;
					$('#example').DataTable({
						data : tableData,
						// 使用对象数组，一定要配置columns，告诉 DataTables 每列对应的属性
						// data 这里是固定不变的，name，position，salary，office 为你数据里对应的属性
						columns : [ {
							data : 'username'
						}, {
							data : 'password'
						}, {
							data : 'credit'
						}, {
							data : 'locked'
						}, {
							data : 'createDate'
						}, {
							data : 'modifyDate'
						}, {
							data : function(data,type,row){
								return '<a href="../../../user?m=selectById&id='+data.id+'">'+'编辑'+'</a>';
							}
						}  ]
					});
				}
			});
		},
	// }}}
	//{{{ 重启Tomcat	
	restartTomcat : function(){
		$.ajax({
			type : post,
			url : 'user',
			data : $.param({m : 'restartTomcat'}),
			success : function(){
				console.log("充气成功");
			}
		});
	}	
	//}}}

	};
	App.init();


});
