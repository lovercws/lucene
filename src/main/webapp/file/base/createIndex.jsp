<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<title>创建索引</title>
<script type="text/javascript">
Ext.onReady(function(){
	//表单
	var form=Ext.create('Ext.form.Panel',{
		titleAlign:'center',
		title:'基本检索创建',
		frame:true,
		layout:{
			type:'hbox'
		},
		items:[{
			xtype:'hiddenfield',
			name:'type',
			value:'addIndex'
		},{
			xtype:'filefield',
			buttonText:'选择文件',
			allowBlank:false,
			width:300
		},{
			xtype:'button',
			text:'提交',
			width:100,
			margin:'0 0 0 60',
			handler:function(){
				if(form.getForm().isValid()){
					form.getForm().submit({
						method:'POST',
						url:'<%=request.getContextPath()%>/file/BaseFileServlet.action?type=addIndex',
						success: function(form, action) {
		                       Ext.Msg.alert('Success', action.result.msg);
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', action.result.msg);
		                    }
					});
				}
			}
		}]
	});
	//索引创建过程显示panel
	var indexPanel=Ext.create('Ext.panel.Panel',{
		frame:true,
		html:''
	});
	
	Ext.create('Ext.container.Viewport',{
		layout:{
			type:'vbox',
			align:'stretch'
		},
		items:[form,indexPanel]
	});
});
</script>
</head>
<body>
</body>
</html>