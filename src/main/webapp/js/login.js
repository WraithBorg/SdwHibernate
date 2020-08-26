$(function() {
	var App = {
		init : function() {
			$('#toregister').on('click',App.saveSome);
			$('#download').on('click', App.querySome);
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
				url : 'user',
				data : $.param({m : 'testResponseEntity'}), 
				async : false,
				error : function(e) {
				},
				success : function(e) {
					
				}
			});
		},
	// }}}

	};
	App.init();


});
