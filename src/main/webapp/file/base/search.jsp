<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引搜索</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
Ext.onReady(function(){
	//域 下拉框数据源
	Ext.create('Ext.data.Store',{
		storeId:'searchComboboxStore',
		autoLoad:{params:{type:'getFieldNames'}},
	    proxy:{type:'ajax',url:'<%=request.getContextPath()%>/file/BaseFileServlet.action',reader:{type:'json'}}
	});
	//搜索结果表格数据源
	Ext.create('Ext.data.Store',{
		storeId:'searchResultStore',
		autoLoad:{params:{type:'search',fieldName:'',fieldValue:''}},
	    proxy:{type:'ajax',url:'<%=request.getContextPath()%>/file/BaseFileServlet.action',reader:{type:'json'}}
	});
	//搜索表单
	var searchForm=Ext.create('Ext.form.Panel',{
		frame : true,
		padding:0,
		layout :'column',
		padding:10,
		items :[{
			xtype :'combobox',
			fieldLabel:'选择域',
			queryMode:'local',
			displayField:'fieldName',
			valueField:'fieldName',
			name :'fieldName',
			store:Ext.data.StoreManager.lookup('searchComboboxStore')
		},{
			xtype :'textfield',
			name :'fieldValue',
			margin:'0 0 0 20'
		},{
			xtype :'button',
			text :'搜索',
			handler :function(btn){
				//提交表单
				var formpanel=this.up();
				var formValues=formpanel.getForm().getValues();
				if(formpanel.getForm().isValid()){
					var store=Ext.data.StoreManager.lookup('searchResultStore');
					store.reload({
						params:{type:'search',fieldName:formValues.fieldName,fieldValue:formValues.fieldValue}
					});
				}
			}
		}]
	});
	
	//搜索结果表格
	var searchResultGridPanel=Ext.create('Ext.grid.Panel',{
		frame : true,
		flex:1,
		padding:0,
		title:'搜索结果',
		id :'searchResultGridPanel',
		autoScroll:true,
		rowLines:true,
		columnLines:true,
		store:Ext.data.StoreManager.lookup('searchResultStore'),
		columns: [{ text: '文件名称',  dataIndex: 'fileName' ,flex:1},
		          { text: '文件大小',  dataIndex: 'size' ,flex:1},
		          { text: '文件类型',  dataIndex: 'type' ,flex:1}]
	});
	
    Ext.create('Ext.container.Viewport',{
    	layout :{
    		type :'vbox',
    		align:'stretch'
    	},
    items :[searchForm,searchResultGridPanel]
    })
});

</script>
</head>
<body>

</body>
</html>