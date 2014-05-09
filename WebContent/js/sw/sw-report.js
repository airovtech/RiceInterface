try{
Ext.require([ 'Ext.Window',
              'Ext.form.*',
              'Ext.data.*',
              'Ext.chart.*',
              'Ext.grid.*',
              'Ext.layout.container.Column',
              'Ext.fx.target.Sprite',
              'Ext.layout.container.Fit' ]);

swReportType = {
	CHART : '1',
	MATRIX : '2',
	TABLE : '3'
};
	
swChartType = {
	LINE : "line",
	AREA : "area",
	BAR : "bar",
	COLUMN : "column",
	PIE : "pie",
	GAUGE : "gauge",
	RADAR : "radar",
	SCATTER : "scatter",
	DEFAULT : this.LINE
};

function ReportInfo() 
{
		this.reportType = swReportType.CHART;
		this.chartType = swChartType.DEFAULT;
		this.targetWorkId = '';
		this.is3Dimension = false;
		this.isStacked = false;
		this.isChartView = true;
		this.isShowLegend = true;
		this.stringLabelRotation = 'auto';
		this.target = null;
		this.width = 1024/2;
		this.height = 768/2;
		this.columnSpans =  1;
		this.xFieldName = null;
		this.yValueName = null;
		this.xGroupName = null;
		this.yGroupName = null;
		this.groupHeaders = null;
		this.groupNames = null;
		this.values = null;
		this.labelRotate = null;	
};

swReportInfo = new ReportInfo();

swReportResizing = false;

Ext.onReady(function () {

try{
	smartChart = {
		companyId : currentUser.companyId,
		userId : currentUser.userId,
		requestUrl : "get_report_data.sw",
		labelFont : '11px dotum,Helvetica,sans-serif',
		reportInfos : {},
	
		getFields : function(target) {
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var fields = new Array();
				if(!isEmpty(swReportInfo.xFieldName)){
					fields.push({name: swReportInfo.xFieldName});
					if(!isEmpty(swReportInfo.xGroupName))
						fields.push({name: swReportInfo.xGroupName});
					if(!isEmpty(swReportInfo.yGroupName))
						fields.push({name: swReportInfo.yGroupName});
				}
				for ( var i = 0; i < swReportInfo.groupNames.length; i++)
					fields.push({name: swReportInfo.groupNames[i]});
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getFields]', null, error);
			}			
			return fields;
		},
		
		getColNames : function(target) {
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var fields = new Array();
				if(!isEmpty(swReportInfo.xFieldName)){
					fields.push(swReportInfo.xFieldName);
					if(!isEmpty(swReportInfo.xGroupName))
						fields.push(swReportInfo.xGroupName);
					if(!isEmpty(swReportInfo.yGroupName))
						fields.push(swReportInfo.yGroupName);
				}
				for ( var i = 0; i < swReportInfo.groupNames.length; i++)
					fields.push(swReportInfo.groupNames[i]);
				if(!isEmpty(swReportInfo.groupHeaders)){
					for(var i=0; i<swReportInfo.groupHeaders.length; i++){
						var startColumnIndex = swReportInfo.groupHeaders[i].startColumnIndex;
						var numberOfColumns = swReportInfo.groupHeaders[i].numberOfColumns;
						var titleText = swReportInfo.groupHeaders[i].titleText;
						if(fields.length>startColumnIndex+numberOfColumns){
							for(var index=startColumnIndex; index<startColumnIndex+numberOfColumns; index++){
								fields[index] = fields[index].split(titleText+".")[1];
							}
						}
					}
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getColumns]', null, error);
			}			
			return fields;
		},
		
		getColModel : function(target) {
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var fields = new Array();
				if(!isEmpty(swReportInfo.xFieldName)){
					fields.push({name: swReportInfo.xFieldName, index: swReportInfo.xFieldName, align: 'center'});
					if(!isEmpty(swReportInfo.xGroupName))
						fields.push({name: swReportInfo.xGroupName, index: swReportInfo.xGroupName, align: 'center'});
					if(!isEmpty(swReportInfo.yGroupName))
						fields.push({name: swReportInfo.yGroupName, index: swReportInfo.yGroupName, align: 'center'});
				}
				for ( var i = 0; i < swReportInfo.groupNames.length; i++)
					fields.push({name: swReportInfo.groupNames[i], index: swReportInfo.groupNames[i], align: 'right'});
				if(!isEmpty(swReportInfo.groupHeaders)){
					for(var i=0; i<swReportInfo.groupHeaders.length; i++){
						var startColumnIndex = swReportInfo.groupHeaders[i].startColumnIndex;
						var numberOfColumns = swReportInfo.groupHeaders[i].numberOfColumns;
						var titleText = swReportInfo.groupHeaders[i].titleText;
						if(fields.length>startColumnIndex+numberOfColumns){
							for(var index=startColumnIndex; index<startColumnIndex+numberOfColumns; index++){
								fields[index].index = fields[index].name.split(titleText+".")[1];
							}
						}
					}
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getColModel]', null, error);
			}			
			return fields;
		},
		
		getTheme : function(chartType, target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				if(chartType === swChartType.LINE)
					return "Base";
				else if(chartType === swChartType.AREA)
					return "Base";
				else if(chartType === swChartType.BAR)
					return "Base";
				else if(chartType === swChartType.PIE)
					return "Category2";
				else if(chartType === swChartType.COLUMN)
					return "Base";
				else if(chartType === swChartType.GUAGE)
					return "Base";
				else if(chartType === swChartType.RADAR)
					return "Category2";
				else if(chartType === swChartType.SCATTER)
					return "Base";
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getTheme]', null, error);
			}			
		},
		
		getAxes : function(chartType, target) {
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var yAxisPosition = "left";
				var xAxisPosition = "bottom";
				var yAxisGrid = true;
				if(chartType === swChartType.AREA)
					yAxisGrid = false;
				if(chartType === swChartType.BAR){
					yAxisPosition = "bottom";
					xAxisPosition = "left";
				}
				
				if(chartType === swChartType.BAR
						|| chartType === swChartType.PIE
						|| chartType === swChartType.GAUGE
						|| chartType === swChartType.RADAR){
					swReportInfo.labelRotate = {
						font: smartChart.labelFont
					};
				}
				
				var numericLabel = {
						renderer: Ext.util.Format.numberRenderer('0,0'),
						font: smartChart.labelFont
					};
				var stringLabel = {
						font: smartChart.labelFont
					};
	
				if(chartType === swChartType.PIE) return [];
				else if(chartType === swChartType.RADAR){
					return [{
		                type: 'Radial',
		                position: 'radial',
		                label: {
		                    display: true,
		                    font: smartChart.labelFont
		                }
					}];		
				}else if(chartType === swChartType.GAUGE){
					return [{
		                type: 'gauge',
		                position: 'gauge',
		                title: swReportInfo.xfieldName,
		                minimum: 0,
		                maximum: 100,
		                steps: 10,
		                margin: -10
		            }];
				}else if(chartType === swChartType.SCATTER){
					return [{
						        type: 'Numeric',
						        position: 'left',
						        fields: swReportInfo.groupNames,
						        title: swReportInfo.yValueName,
						        grid: true,
						        minimum: 0,
						        label : numericLabel
						    }, {
						        type: 'Category',
						        position: 'bottom',
						        fields: [ swReportInfo.xFieldName ],
						        title: swReportInfo.xFieldName,
						        label: swReportInfo.labelRotate
						    }];
				}else if(chartType === swChartType.LINE 
						|| chartType === swChartType.AREA
						|| chartType === swChartType.BAR
						|| chartType === swChartType.COLUMN){ 
					return [{
						type : 'Numeric',
						minimum : 0,
						position : yAxisPosition,
						grid : yAxisGrid,
						fields : swReportInfo.groupNames,
						title : swReportInfo.yValueName,
						minorTickSteps : 1,
						label: numericLabel
					}, {
						type : 'Category',
						position : xAxisPosition,
						fields : [ swReportInfo.xFieldName ],
						title : swReportInfo.xFieldName,
						label: swReportInfo.labelRotate
					} ];
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getAxes]', null, error);
			}			
		},
	
		getSeriesForPIE : function(index, target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var series = new Array();
				series = [{
				    type: swReportInfo.chartType,
				    field: swReportInfo.groupNames[index],
				    showInLegend: true,
				    donut: 20,
				    highlight: {
				      segment: {
				        margin: 20
				      }
				    },
	                tips: {
	                    trackMouse: true,
	                    height : 32,
	                    width : 160,
	                    renderer: function(storeItem, item) {
							if(!isEmpty(target)){
								swReportInfo = smartChart.reportInfos[target];
							}
	                    	var total = 0;
	                    	for(var i=0; i<swReportInfo.values.length; i++){
	                    		total += swReportInfo.values[i][ swReportInfo.groupNames[index]];
	                    	}
//	                    	this.setTitle(storeItem.data[ swReportInfo.xFieldName] + "<br>" + storeItem.data[swReportInfo.groupNames[index]] + "  (" + Math.round(storeItem.data[swReportInfo.groupNames[index]]/total * 100) + "%)");
	                    	this.setTitle(item.storeItem.data[ swReportInfo.xFieldName] + "<br>" + item.storeItem.data[swReportInfo.groupNames[index]] + "  (" + Math.round(item.storeItem.data[swReportInfo.groupNames[index]]/total * 100) + "%)");
	                    }
	                },
				    label: {
				        field: swReportInfo.xFieldName,
				        display: 'rotate',
				        contrast: true,
				        font: smartChart.labelFont,
	                    renderer: function(v, storeItem, item) {
							if(!isEmpty(target)){
								swReportInfo = smartChart.reportInfos[target];
							}
	                    	var total = 0;
	                    	for(var i=0; i<swReportInfo.values.length; i++){
	                    		total += swReportInfo.values[i][ swReportInfo.groupNames[index]];
	                    	}
	                    	return Math.round(item.data[swReportInfo.groupNames[index]]/total * 100) + "%";
	                    }
				    }		}];
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getSeriesForPIE]', null, error);
			}			
		    return series;
		},
		
		getSeries : function(chartType, target) {
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var markerConfig = {
						type: 'circle',
						radius: 3,
						size: 3,							
					}; 
				var highlight = {
	                    size: 7,
	                    radius: 7
	                };
				var axis = "left";
				if(chartType === swChartType.BAR) axis = "bottom";
				
				if(chartType === swChartType.LINE){
					var series = new Array();
					for(var i=0; i<swReportInfo.groupNames.length; i++){
						series.push({
							type : swChartType.COLUMN,
							axis : axis,
							showInLegend: false,
							xField : swReportInfo.xFieldName
						});
						series.push({
							type : chartType,
							axis : axis,
							xField : swReportInfo.xFieldName,
							yField : swReportInfo.groupNames[i],
							showInLegend: swReportInfo.is3Dimension,
			                highlight: highlight,
			                markerConfig: markerConfig,
			                style:{
								'stroke-width': 0		                	
			                },
			                tips: {
			                    trackMouse: true,
			                    height : 32,
			                    width : 100,
			                    renderer: function(storeItem, item) {
			                    	this.setTitle(item.series.yField + "<br>" + Ext.util.Format.number(item.value[1], "0,0"));
			                    }
			                }
						});
					}
					return series;
				}else if(chartType === swChartType.RADAR){
					var series = new Array();
					for(var i=0; i<swReportInfo.groupNames.length; i++){
						series.push({
							type : chartType,
							xField : swReportInfo.xFieldName,
							yField : swReportInfo.groupNames[i],
							showInLegend: swReportInfo.is3Dimension,
							showMarkers: true,
							markerConfig: markerConfig,
			                tips: {
			                    trackMouse: true,
			                    height : 32,
			                    width : 100,
			                    renderer: function(storeItem, item) {
			                    	this.setTitle(item.series.yField + "<br>" + Ext.util.Format.number(storeItem.data[item.series.yField], "0,0") );
			                    }
			                },
							style:{
								'stroke-width': 2,
								fill: 'none'
							}
						});
					}
					return series;
					
				}else if(chartType === swChartType.SCATTER){
					var series = new Array();
					for(var i=0; i<swReportInfo.groupNames.length; i++){
						series.push({
					        type: chartType,
						    showInLegend: swReportInfo.is3Dimension,
			                highlight: highlight,
			                label: {
			                	orientation: swReportInfo.labelOrientation
			                },
			                markerConfig: markerConfig,
			                tips: {
			                    trackMouse: true,
			                    height : 32,
			                    width : 100,
			                    renderer: function(storeItem, item) {
			                    	this.setTitle(item.series.yField + "<br>" + Ext.util.Format.number(storeItem.data[item.series.yField], "0,0") );
			                    }
			                },
			                style : {
			                    'stroke-width': 0
			                },
					        axis: 'left',
					        xField: swReportInfo.xFieldName,
					        yField: swReportInfo.groupNames[i]
						});
					}
					return series;
					
				}else if(chartType === swChartType.AREA){
					return [{
						type : chartType,
						axis : axis,
						xField : swReportInfo.xFieldName,
						yField : swReportInfo.groupNames,
					    showInLegend: swReportInfo.is3Dimension,
						highlight : false,
		                tips: {
		                    trackMouse: true,
		                    height : 32,
		                    width : 100,
		                    renderer: function(storeItem, item) {
		                    	this.setTitle(item.storeField + "<br>" + Ext.util.Format.number(storeItem.data[item.storeField], "0,0") );
		                    }
		                }
					}];
					
				}else if( chartType === swChartType.GAUGE
						|| chartType === swChartType.COLUMN
						|| chartType === swChartType.BAR){
					return [{
						type : chartType,
		                gutter: 80,
						axis : axis,
						xField : swReportInfo.xFieldName,
						yField : swReportInfo.groupNames,
					    showInLegend: true,
						highlight : true,
						stacked : swReportInfo.isStacked,
		                tips: {
		                    trackMouse: true,
		                    height : 32,
		                    width : 100,
		                    renderer: function(storeItem, item) {
		                    	this.setTitle(item.yField + "<br>" + Ext.util.Format.number(item.value[1], "0,0"));
		                    }
		                }
					}];
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getSeries]', null, error);
			}			
		},
	
		getChartData : function(reportId, target, removeAfterLoad, targetWorkId) {
			$.ajax({
				url : smartChart.requestUrl,
				data : {
					companyId : smartChart.companyId,
					userId : smartChart.userId,
					targetWorkId : targetWorkId,
					reportId : reportId
				},
				success : function(data, status, jqXHR) {
					try{
						if(data){
							if(!isEmpty(target)){
								swReportInfo = smartChart.reportInfos[target];
							}
							swReportInfo.xFieldName = data.xFieldName;
							swReportInfo.yValueName = data.yValueName;
							swReportInfo.xGroupName = data.xGroupName;
							swReportInfo.yGroupName = data.yGroupName;
							swReportInfo.groupHeaders = data.groupHeaders;
							swReportInfo.groupNames = data.groupNames;
							swReportInfo.values = data.values;
							if(swReportInfo.isChartView && swReportInfo.reportType === swReportType.CHART){
								if((swReportInfo.stringLabelRotation === "auto" && (swReportInfo.values.length>12 || swReportInfo.width<600)) || swReportInfo.stringLabelRotation === "rotated" ){
									swReportInfo.labelRotate = {
						                	rotate : {
						                		degrees : 45
						                	},
											font: smartChart.labelFont
						                };
								}else{
									swReportInfo.labelRotate = {
											font: smartChart.labelFont
									};
								}
								
								if(swReportInfo.groupNames.length > 1){
									if(swReportInfo.chartType == swChartType.BAR || swReportInfo.chartType == swChartType.COLUMN){
										$('.js_work_report_view_page .js_stacked_chart').show();
									}else{
										$('.js_work_report_view_page .js_stacked_chart').hide();								
									}
									swReportInfo.is3Dimension = true;
								}else{
									$('.js_work_report_view_page .js_stacked_chart').hide();
									swReportInfo.is3Dimension = false;
								}
								if(!isEmpty(target)){
									smartChart.reportInfos[target] = swReportInfo;
								}
								smartChart.createChart(target);
								if(!isEmpty(target))
									smartChart.resizePane($('#' + target).parent());
	
							}else if(!swReportInfo.isChartView || swReportInfo.reportType === swReportType.MATRIX){
								if(!isEmpty(target)){
									smartChart.reportInfos[target] = swReportInfo;
								}
								smartChart.createMatrix(target);						
							}else if(swReportInfo.reportType === swReportType.TABLE){
								
							}
							if(!isEmpty(removeAfterLoad)){
								removeAfterLoad.remove();
							}
							smartPop.closeProgress();
						}
					}catch(error){
						smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getChartData]', null, error);
				}			
				},
				error : function(xhr, ajaxOptions, thrownError){
					smartPop.closeProgress();
					smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getChartData]', null, thrownError);
				}
			});
		},
	
		load : function(reportType, reportId, chartType, isStacked, target, targetWorkId) {
			try{
				swReportInfo = new ReportInfo();
				swReportInfo.reportType = reportType;
				if(isEmpty(chartType)) chartType = swChartType.DEFAULT;
				swReportInfo.targetWorkId = targetWorkId;
				swReportInfo.chartType = chartType;
				swReportInfo.isStacked = isStacked;
				swReportInfo.target = target;
				$('#'+target).html('');
				swReportInfo.width = $('#' + target).width();
				smartChart.getChartData(reportId, null, null, targetWorkId);
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady load]', null, error);
			}			
		},
		
		loadPane : function(reportType, reportId, chartType, isStacked, isChartView, isShowLegend, stringLabelRotation, target, columnSpans, removeAfterLoad, targetWorkId) {
			try{
				var reportInfo = new ReportInfo();
				reportInfo.reportType = reportType;
				if(isEmpty(chartType)) chartType = swChartType.DEFAULT;
				reportInfo.chartType = chartType;
				reportInfo.targetWorkId = targetWorkId;
				reportInfo.isStacked = isStacked;
				reportInfo.isChartView = isChartView;
				reportInfo.isShowLegend = isShowLegend;
				reportInfo.stringLabelRotation = stringLabelRotation;
				reportInfo.target = target;
				var targetDiv = $('#'+target);
				reportInfo.width = targetDiv.parents('.js_dashboard_pane_row').width()/columnSpans-5;
				targetDiv.html('').parents('.js_work_report_pane_page').width(reportInfo.width);
				reportInfo.columnSpans = columnSpans;
				smartChart.reportInfos[target] = reportInfo;
				smartChart.getChartData(reportId, target, removeAfterLoad, targetWorkId);
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady loadPane]', null, error);
			}			
		},
		
		loadWithData : function(reportType, data, chartType, isStacked, target) {
			try{
				reportInfo = smartChart.reportInfos[target];
				if(isEmpty(reportInfo)){
					reportInfo = new ReportInfo();
					smartChart.reportInfos[target] = reportInfo;
				}
				reportInfo.reportType = reportType;
				if(isEmpty(chartType)) chartType = swChartType.DEFAULT;
				reportInfo.chartType = chartType;
				reportInfo.isStacked = isStacked;
				reportInfo.target = target;
				$('#'+target).html('');
				reportInfo.width = $('#' + target).width();
				if(data){
					reportInfo.xFieldName = data.xFieldName;
					reportInfo.yValueName = data.yValueName;
					reportInfo.xGroupName = data.xGroupName;
					reportInfo.yGroupName = data.yGroupName;
					reportInfo.groupHeaders = data.groupHeaders;
					reportInfo.groupNames = data.groupNames;
					reportInfo.values = data.values;
					if((reportInfo.stringLabelRotation === "auto" && (reportInfo.values.length>12 || reportInfo.width<600)) || reportInfo.stringLabelRotation === "rotated" ){
						reportInfo.labelRotate = {
			                	rotate : {
			                		degrees : 45
			                	},
								font: smartChart.labelFont
			                };
					}else{
						reportInfo.labelRotate = {
								font: smartChart.labelFont
						};
					}
					if(reportInfo.reportType === swReportType.CHART){
						smartChart.createChart(target);
					}else if(reportInfo.reportType === swReportType.MATRIX){
						smartChart.createMatrix();				
						smartChart.resize();
					}else if(reportInfo.reportType === swReportType.TABLE){
						
					}
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady loadWithData]', null, error);
			}			
		},
		
		reload : function(chartType, isStacked, isChartView){
			try{
				swReportInfo.chartType = chartType;
				swReportInfo.isStacked = isStacked;
				$('#'+swReportInfo.target).html('');
				swReportInfo.width = $('#' + swReportInfo.target).width();
				if((swReportInfo.stringLabelRotation === "auto" && (swReportInfo.values.length>12 || swReportInfo.width<600)) || swReportInfo.stringLabelRotation === "rotated" ){
					swReportInfo.labelRotate = {
		                	rotate : {
		                		degrees : 45
		                	},
							font: smartChart.labelFont
		                };
				}else{
					swReportInfo.labelRotate = {
							font: smartChart.labelFont
					};
				}
				if(swReportInfo.reportType === swReportType.CHART && isChartView){
					smartChart.createChart();
				}else if((swReportInfo.reportType === swReportType.MATRIX) || (swReportInfo.reportType === swReportType.CHART && !isChartView)){
					smartChart.createMatrix();				
				}else if(swReportInfo.reportType === swReportType.TABLE){
					
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady reload]', null, error);
			}			
		},
		
		resize : function(){
			try{
				$('#'+swReportInfo.target).html('');
				swReportInfo.width = $('#' + swReportInfo.target).width();
				if((swReportInfo.stringLabelRotation === "auto" && (swReportInfo.values.length>12 || swReportInfo.width<600)) || swReportInfo.stringLabelRotation === "rotated" ){
					swReportInfo.labelRotate = {
		                	rotate : {
		                		degrees : 45
		                	},
							font: smartChart.labelFont
		                };
				}else{
					swReportInfo.labelRotate = {
							font: smartChart.labelFont
					};
				}
				
				if(swReportInfo.reportType === swReportType.CHART){
					smartChart.createChart();
				}else if(swReportInfo.reportType === swReportType.MATRIX){
					smartChart.createMatrix();				
				}else if(swReportInfo.reportType === swReportType.TABLE){
					
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady resize]', null, error);
			}			
		},
		
		resizePane : function(workReportPane){
			try{
				if(!isEmpty(workReportPane)){
					var target = "chart_target_" + workReportPane.attr('panePosition');
					swReportInfo = smartChart.reportInfos[target];
					swReportInfo.columnSpans = parseInt(workReportPane.attr('paneColumnSpans'));
					var targetDiv = $("#" + target);
					if(isEmpty(targetDiv)) return;
					swReportInfo.width = targetDiv.parents('.js_dashboard_pane_row').width()/swReportInfo.columnSpans-5;
					targetDiv.html('').parents('.js_work_report_pane_page').width(swReportInfo.width);
					if((swReportInfo.stringLabelRotation === "auto" && (swReportInfo.values.length>12 || swReportInfo.width<600)) || swReportInfo.stringLabelRotation === "rotated" ){
						swReportInfo.labelRotate = {
			                	rotate : {
			                		degrees : 45
			                	},
								font: smartChart.labelFont
			                };
					}else{
						swReportInfo.labelRotate = {
								font: smartChart.labelFont
						};
					}
					smartChart.reportInfos[target] = swReportInfo;
					if(swReportInfo.isChartView && swReportInfo.reportType === swReportType.CHART){
						smartChart.createChart(target);
					}else if(!swReportInfo.isChartView || swReportInfo.reportType === swReportType.MATRIX){
						smartChart.createMatrix(target);				
					}else if(swReportInfo.reportType === swReportType.TABLE){
						
					}				
				}else{
					var chartTargetPane = $(".js_chart_target_pane");
					if(isEmpty(chartTargetPane)) return;
					
					for(var i=0; i<chartTargetPane.length; i++){
						var target = $(chartTargetPane[i]).attr('id');
						swReportInfo = smartChart.reportInfos[target];
						var targetDiv = $('#'+target);
						swReportInfo.width = targetDiv.parents('.js_dashboard_pane_row').width()/swReportInfo.columnSpans-5;
						targetDiv.html('').parents('.js_work_report_pane_page').width(swReportInfo.width);
						if((swReportInfo.stringLabelRotation === "auto" && (swReportInfo.values.length>12 || swReportInfo.width<600)) || swReportInfo.stringLabelRotation === "rotated" ){
							swReportInfo.labelRotate = {
				                	rotate : {
				                		degrees : 45
				                	},
									font: smartChart.labelFont
				                };
						}else{
							swReportInfo.labelRotate = {
									font: smartChart.labelFont
							};
						}
						smartChart.reportInfos[target] = swReportInfo;
						if(swReportInfo.isChartView && swReportInfo.reportType === swReportType.CHART){
							smartChart.createChart(target);
						}else if(!swReportInfo.isChartView || swReportInfo.reportType === swReportType.MATRIX){
							smartChart.createMatrix(target);				
						}else if(swReportInfo.reportType === swReportType.TABLE){
							
						}
					}
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady sizePane]', null, error);
			}			
		},
		
		getColumns : function(target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
	        	var columns = new Array();
	        	columns.push({
	        		id: swReportInfo.xFieldName,
	        		text: swReportInfo.xFieldName,
	        		flex: 1,
	        		sortable: false,
	        		dataIndex: swReportInfo.xFieldName,
	                summaryType: 'count',
	                summaryRenderer: function(value, summaryData, dataIndex) {
	                	if(value == swReportInfo.values.length){
	                		return smartMessage.get("reportGrandTotal") + '(' + value + ')';
	                	}else{
	                		return smartMessage.get("reportSubTotal") + '(' + value + ')';
	                	}
	                }        	});
	        	for(var i=0; i<swReportInfo.groupNames.length; i++){
		        	columns.push({
		        		text: swReportInfo.groupNames[i],
		        		align: 'right',
		        		sortable: false,
		                summaryType: 'sum',
		        		dataIndex: swReportInfo.groupNames[i],
		        		renderer: Ext.util.Format.numberRenderer('0,0')
		        	});		        		
	        	}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getColumns]', null, error);
			}			
        	return columns;			
		},
		
		createMatrix : function(target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				    Ext.create('Ext.grid.Panel', {
				        renderTo:  Ext.get(swReportInfo.target),
				        collapsible: false,
				        frame: false,
				        frameHeader: false,
				        bodyBorder: false,
				        sortableColumns: false,
				        enableColumnHide: false,
				        enableColumnMove: false,
				        draggable: false,
				        columnLines: false,
						store : Ext.create('Ext.data.JsonStore', {
							fields : smartChart.getFields(target),
							groupField: swReportInfo.xGroupName,
							data : swReportInfo.values
						}),
						width: swReportInfo.width,
						height: swReportInfo.height,
				        features: [{
				            id: 'group',
				            ftype: 'groupingsummary',
				            groupHeaderTpl: swReportInfo.xGroupName + ' : {name}',
				            hideGroupedHeader: false,
				            enableGroupingMenu: false,
				            enableNoGroups: false
				        },{
				            id: 'summary',
				            ftype: 'summary'
				        }],
				        columns: smartChart.getColumns(target)
				    });
				}else{
				    Ext.create('Ext.grid.Panel', {
				        renderTo:  Ext.get(swReportInfo.target),
				        collapsible: false,
				        frame: false,
				        frameHeader: false,
				        bodyBorder: false,
				        sortableColumns: false,
				        enableColumnHide: false,
				        enableColumnMove: false,
				        draggable: false,
				        columnLines: false,
						store : Ext.create('Ext.data.JsonStore', {
							fields : smartChart.getFields(target),
							groupField: swReportInfo.xGroupName,
							data : swReportInfo.values
						}),
						width: swReportInfo.width,
						minHeight: swReportInfo.height,
				        features: [{
				            id: 'group',
				            ftype: 'groupingsummary',
				            groupHeaderTpl: swReportInfo.xGroupName + ' : {name}',
				            hideGroupedHeader: false,
				            enableGroupingMenu: false,
				            enableNoGroups: false
				        },{
				            id: 'summary',
				            ftype: 'summary'
				        }],
				        columns: smartChart.getColumns(target)
				    });
				}
			    var chartTarget = $(".js_work_report_view_page > #chart_target");
			    if(isEmpty(chartTarget)) chartTarget = $(".js_work_report_pane_page > .js_chart_target_pane");
	
				chartTarget.find("div:first > div").css("border-color", "#c7c7c7");
			    $(".js_work_report_pane_page").css("display", "inline-block");
	
				var repeatTimeout = 5;
				var intervalId = setInterval(function(){
					try{
						if(!isEmpty(chartTarget.find("tr.x-grid-row-summary > td.x-grid-cell")) || repeatTimeout == 0){
							clearInterval(intervalId);
							chartTarget.find("div.x-column-header").css("font-family", "dotum,Helvetica,sans-serif").css("font-size", "12px").css("font-weight", "bold").css("text-align", "center");
							chartTarget.find("div.x-grid-group-title").css("font-family", "dotum,Helvetica,sans-serif").css("font-size", "12px").css("font-weight", "bold");
							chartTarget.find("td.x-grid-cell").css("font-family", "dotum,Helvetica,sans-serif");//.css("font-size", "12px");
							chartTarget.find("tr.x-grid-row-summary > td.x-grid-cell").css("font-family", "dotum,Helvetica,sans-serif").css("font-size", "12px").css("font-weight", "bold");
						}
						repeatTimeout--;
					}catch(error){
						smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createMatrix interval]', null, error);
					}			
				}, 100);
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createMatrix]', null, error);
			}			
		},
		
		getGroupingView : function(target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				var groupingView = {};
				if(!isEmpty(swReportInfo.yGroupName)){
					groupingView['groupField'] = [swReportInfo.yGroupName];
					groupingView['groupSummary'] = [true];
					groupingView['groupColumnShow'] = [true];
					groupingView['groupText'] = ['<span style="color:blue"><b>{0}</b></span>'];
				}
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady getGroupView]', null, error);
			}			
			return groupingView;
		},
		
		createMatrixJqgrid : function(target){
			try{
				if(!isEmpty(target))
					swReportInfo = smartChart.reportInfos[target];
	
				$('#'+ swReportInfo.target).jqGrid({
					datatype: "local",
					height: 'auto',
		         	footerrow:false,
					autowidth: true,
					hoverrows: true,
					multiselect: false,         //전체선택 체크박스 유무, 테이블에서 row 체크를 멀티로 할 수 있는 옵션.
					viewsortcols: [false, 'vertical', false],
					grouping: true,//!isEmpty(smartChart.getGroupingView(target)),
	//				groupingView : smartChart.getGroupingView(target),
					groupingView : {
						groupField : ['사업부'], //그룹화 기준이 되는 컬럼명
						groupSummary : [true], //소계를 보인다.
						groupColumnShow : [true], //그룹화된 컬럼을 컬럼안에서 다시 표기한다.
						groupText : ['<span style="color:blue"><b>{0}</b></span>'] //그룹화된 이름에 <b> 태그를 추가했다.
					},
					colNames: smartChart.getColNames(target),
					colModel: smartChart.getColModel(target),
					loadError:function(xhr, status, error) {          //---데이터 못가져오면 실행 됨
						smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createMatrixJqgrid loadError]', null, error);
					},
					gridComplete : function(){
						try{
							var jqGridBox = $('#gbox_' + swReportInfo.target);
							jqGridBox.css('font-family', 'dotum,Helvetica,sans-serif');
							jqGridBox.find('.ui-jqgrid-view').css('background-color', '#ffffff');
							jqGridBox.find('tr.ui-jqgrid-labels>th').css("font-size", "12px").css('font-weight', 'bold').css('color', '#333333').css('text-align', 'center');
							jqGridBox.find('tr.ui-jqgrid-labels span.s-ico').hide();
							jqGridBox.find('tbody tr').removeClass('ui-widget-content');
						}catch(error){
							smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createMatrixJqgrid gridComplete]', null, error);
						}			
					}
				});
				
				if(!isEmpty(swReportInfo.groupHeaders)){
					$('#'+ swReportInfo.target).jqGrid('setGroupHeaders', {
						useColSpanStyle: true,
						groupHeaders: swReportInfo.groupHeaders
					});
				}
					  
				for(var i=0; i<swReportInfo.values.length; i++){
					$('#' + swReportInfo.target).jqGrid('addRowData', i+1, swReportInfo.values[i]);
				}			  
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createMatrixJqgrid]', null, error);
			}			
		},
		
		createChart : function(target){
			try{
				if(!isEmpty(target)){
					swReportInfo = smartChart.reportInfos[target];
				}
				
				var legendOption= {
						visible : false
				};
				if(swReportInfo.isShowLegend){
					legendOption = {
							position : 'right'						
					};
				}
	
				if(swReportInfo.chartType === swChartType.PIE){
					if(swReportInfo.values==null || swReportInfo.values.length==0){
						legendOption = false;
					}else{
						legendOption = {
								position: 'float',
								x : 100,
								y : 500
						};
					}
					for(var i=0; i< swReportInfo.groupNames.length; i++)
						Ext.create('Ext.chart.Chart',{						
							width: swReportInfo.height,
							height: swReportInfo.height+300,
							insetPadding: 25,
							shodow:true,
//							legend: {
//								position: 'float',
//								x : 100,
//								y : 500
//							},
							legend: legendOption,
					        html: '<span style="font-weight: bold; font-size: 13px; font-family: dotum,Helvetica,sans-serif;">' + swReportInfo.groupNames[i] + '</span>',
							animate: true,
							renderTo : Ext.get(swReportInfo.target),
							store : Ext.create('Ext.data.JsonStore', {
								fields : smartChart.getFields(target),
								data : swReportInfo.values
							}),
							shadow : true,
							axes : smartChart.getAxes(swReportInfo.chartType, target),
							series : smartChart.getSeriesForPIE(i, target)
					  	});
				}else if(swReportInfo.chartType === swChartType.GAUGE){
					for(var i=0; i<swReportInfo.groupNames.length; i++)
						Ext.create('Ext.chart.Chart', {
							width: 300,
							height: 200,
							minHeight: 300,
							minWidth: 200,
				            style: 'background:#fff',
				            animate: {
				                easing: 'elasticIn',
				                duration: 1000
				            },
				            renderTo: Ext.get(swReportInfo.target),
				            store : Ext.create('Ext.data.JsonStore', {
								fields : smartChart.getFields(target),
								data : swReportInfo.values
				            }),
				            
				            insetPadding: 25,
				            flex: 1,					
				            axes: smartChart.getAxes(swReportInfo.chartType, target),
				            series: [{
				                type: swReportInfo.chartType,
				                field: swReportInfo.groupNames[i],
				                donut: 30,
				                colorSet: ['#F49D10', '#ddd']
				            }]
				 				});
		
				}else if(swReportInfo.chartType === swChartType.SCATTER){
						Ext.create('Ext.chart.Chart', {
							width: swReportInfo.width,
							height: swReportInfo.height,
							animate: true,
							theme: 'Category2',
							resizable: true,
				            style: 'background:#fff',
				            renderTo: Ext.get(swReportInfo.target),
				            store : Ext.create('Ext.data.JsonStore', {
								fields : smartChart.getFields(target),
								data : swReportInfo.values
				            }),
				            
							legend : legendOption,
				            flex: 1,					
				            axes: smartChart.getAxes(swReportInfo.chartType, target),
				            series: smartChart.getSeries(swReportInfo.chartType, target)
						});
		
				}else if(swReportInfo.chartType === swChartType.COLUMN){
					Ext.create('Ext.chart.Chart',{
						width: swReportInfo.width,
						height: swReportInfo.height,
						animate: true,
						theme: 'Base',
						resizable: false,
						autoSize: true,
						insetPadding: 20,// radar
						renderTo : Ext.get(swReportInfo.target),
						store : Ext.create('Ext.data.JsonStore', {
							fields : smartChart.getFields(target),
							data : swReportInfo.values
						}),
						shadow : true,
						legend : legendOption,
						axes : smartChart.getAxes(swReportInfo.chartType, target),
						series : smartChart.getSeries(swReportInfo.chartType, target)
					});
				}else{
					Ext.create('Ext.chart.Chart',{
						width: swReportInfo.width,
						height: swReportInfo.height,
						animate: true,
						theme: 'Category2',
						resizable: false,
						autoSize: true,
						insetPadding: 20,// radar
						renderTo : Ext.get(swReportInfo.target),
						store : Ext.create('Ext.data.JsonStore', {
							fields : smartChart.getFields(target),
							data : swReportInfo.values
						}),
						shadow : true,
						legend : legendOption,
						axes : smartChart.getAxes(swReportInfo.chartType, target),
						series : smartChart.getSeries(swReportInfo.chartType, target)
					});
				}
				$(".js_work_report_view_page text[text='" + swReportInfo.xFieldName + "']").css("font-size", "13px");
				$(".js_work_report_view_page text[text='" + swReportInfo.yValueName + "']").css("font-size", "13px");
				$(".js_work_report_pane_page div.x-surface").css("vertical-align", "top");
				
			    $(".js_work_report_pane_page").css("display", "inline-block");
			}catch(error){
				smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-repprt Ext.onReady createChart]', null, error);
			}			
		    
		}
	};
}catch(error){
	smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-report Ext.OnReady]', null, error);
}			
});
}catch(error){
	smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-report script]', null, error);
}
