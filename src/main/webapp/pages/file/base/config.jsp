<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引配置</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ext-all.css">
<script type="text/javascript">
Ext.onReady(function(){
	//搜索结果表格数据源
	Ext.create('Ext.data.Store',{
		storeId:'fieldsGridStore',
		fields:[{name:'name'},
		        {name:'cnName'},
		        {name:'type'},
		        {name:'store'},
		        {name:'tokenized'},
		        {name:'indexed'}],
		data:[]
	});
	//字段集合表格
	var fieldsGrid=Ext.create('Ext.grid.Panel',{
		padding:0,
		flex:1,
		autoScroll:true,
		rowLines:true,
		columnLines:true,
		store:Ext.data.StoreManager.lookup('fieldsGridStore'),
		columns:[{header:'名称',dataIndex:'name',flex:1},
		         {header:'中文名称',dataIndex:'cnName',flex:1},
		         {header:'类型',dataIndex:'type',flex:1},
		         {header:'存储',dataIndex:'store',flex:1},
		         {header:'分词',dataIndex:'tokenized',flex:1},
		         {header:'索引',dataIndex:'indexed',flex:1}]
	});
	
	//索引基本配置表单
	var basicConfigMessageForml=Ext.create('Ext.form.Panel',{
		padding:0,
		frame:true,
		flex:1,
		layout:{
			type:'hbox',
			align:'stretch'
		},
		items:[{
	        xtype:'fieldset',
	        flex:5,
	        title: '基本配置信息',
	        collapsible: false,
	        layout: {
	        	type:'vbox',
	        	align:'stretch'
	        },
	        items :[{
				xtype:'textfield',
				fieldLabel:'配置名称',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:20
			},{
				xtype:'textfield',
				fieldLabel:'版本号',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:20
			},{
				xtype:'textfield',
				fieldLabel:'索引位置',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:20
			},{
				xtype:'textfield',
				fieldLabel:'索引类型',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:20
			},{
				xtype:'textfield',
				fieldLabel:'默认分词器',
				labelAlig:'left',
				labelWidth:70,
				maxWidth:500,
				margin:20
			}]
	    },{
	    	 xtype:'fieldset',
	    	 flex:5,
		     title: '字段集合',
		     collapsible: false,
		     layout: {
		        type:'vbox',
		        align:'stretch'
		     },
		     items :[fieldsGrid]   
	    }]
	});
	
	Ext.create('Ext.container.Viewport',{
    	layout :{
    		type :'vbox',
    		align:'stretch'
    	},
        items :[basicConfigMessageForml]
    });
});
</script>
</head>
<body>
</body>
</html>