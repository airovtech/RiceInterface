<%@page import="net.smartworks.rice.model.TestReport"%>
<%@page import="net.smartworks.model.SortingField"%>
<%@page import="net.smartworks.rice.model.SummaryReportCond"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.model.RequestParams"%>
<%@page import="net.smartworks.model.filter.SearchFilter"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<script>
try{
	currentUser = {
		userId : "",
		name : "",
		longName : "",
		nickName : "",
		company : "",
		companyId : "",
		department : "",
		departmentId : "",
		isUseMail : "",
		minPicture : "",
		midPicture : "",
		orgPicture : "",
		locale : "ko",
		timeZone : "",
		timeOffset : "",
		signPicture : "",
	};
}catch(e){
}
</script>

<link href="http://meyerweb.com/eric/tools/css/reset/reset.css" rel="stylesheet" type="text/css" />

<link href="http://meyerweb.com/eric/tools/css/reset/reset.css" rel="stylesheet" type="text/css" />
<link href="../css/default.css" type="text/css" rel="stylesheet" />
<link href="../css/black/layout.css" type="text/css" rel="stylesheet" />
<link href="../css/black/detail.css" type="text/css" rel="stylesheet" />
<link href="../css/black/form.css" type="text/css" rel="stylesheet" />
<link href="../css/black/pop.css" type="text/css" rel="stylesheet" />
<link href="../css/black/media.css" type="text/css" rel="stylesheet"/>
<link href="../css/rice.css" type="text/css" rel="stylesheet" />

<link href="../css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" title="ui-theme" />
<link href="../css/ext/ext-all.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="../js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.effects.core.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.effects.explode.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.draggable.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.resizable.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.slider.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-timepicker-ko.js"></script>
<script type="text/javascript" src="../js/jquery/history/jquery.history.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.zclip.min.js"></script>
<script type="text/javascript" src="../js/jquery/jquery.simplemodal.1.4.2.min.js"></script>
<script type="text/javascript" src="../js/ext/ext-all.js"></script>

<script type="text/javascript" src="../js/sw/sw-language.js"></script>
<script type="text/javascript" src="../js/sw/sw-language-ko.js"></script>
<script type="text/javascript" src="../js/sw/sw-language-en.js"></script>
<script type="text/javascript" src="../js/sw/sw-common.js"></script>
<script type="text/javascript" src="../js/sw/sw-util.js"></script>
<script type="text/javascript" src="../js/sw/sw-iframe-autoheight.js"></script>
<script type="text/javascript" src="../js/sw/sw-report.js"></script>
<script type="text/javascript" src='../js/sw/sw-popup.js'></script>
<script type="text/javascript" src="../js/sw/sw-nav.js"></script>

<script type="text/javascript" src="../js/sw/sw-act-nav.js"></script>
<script type="text/javascript" src="../js/sw/sw-act-report.js"></script>
<script type="text/javascript" src="../js/sw/sw-act-search.js"></script>
<script type="text/javascript" src="../js/sw/sw-act-work.js"></script>
<script type="text/javascript" src="../js/sw/sw-act-space.js"></script>
<script type="text/javascript" src="../js/sw/sw-act-rice.js"></script>

<title></title>

</head>

<script type="text/javascript">
try{

	getIntanceList = function(paramsJson, progressSpan, isGray){
		smartPop.progressCont(progressSpan);
		console.log(JSON.stringify(paramsJson));
		$.ajax({
			url : "/RiceInterface/jsp/set_list_params.jsp?data=" + JSON.stringify(paramsJson),
			contentType : 'application/json',
			type : 'POST',
			success : function(data, status, jqXHR) {
				$('#test_list_page').html(data);
				if($('.js_select_list_type a[listType="testList"]').parent().hasClass('unselected')){
					$.ajax({url : '/RiceInterface/jsp/summary_chart.jsp', success : function(data, status, jqXHR) {
							$('.js_test_detail_page').html(data);
							smartPop.closeProgress();
						}
					});
				}else{
					smartPop.closeProgress();
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {
				smartPop.closeProgress();
			}
		});
	};
	
	selectListParam = function(progressSpan, isGray){
		var forms = $('form:visible');
		var paramsJson = {};
		for(var i=0; i<forms.length; i++){
			var form = $(forms[i]);
			paramsJson[form.attr('name')] =form.serializeObject();
		}
		getIntanceList(paramsJson, progressSpan, isGray);		
	};
	
	selectSearchDate = function(progressSpan){
		var searchDateFrom = $('input[name="txtSearchDateFrom"]').attr('value');
		var searchDateTo = $('input[name="txtSearchDateTo"]').attr('value');
		var removeSearchDate = $('.js_remove_search_date');
		if(!isEmpty(searchDateFrom) || !isEmpty(searchDateTo)){
			removeSearchDate.show();
		}else{
			removeSearchDate.hide();
		}
		if(isEmpty(searchDateTo)) searchDateTo = (new Date()).format('yyyy.mm.dd');
		var summaryType = $('.js_select_summary_type option:selected').attr('value');
		if(!isEmpty(searchDateFrom) && !isEmpty(searchDateTo)){
			
			var fromDate, toDate;
			try{
				fromDate = new Date(searchDateFrom);
			}catch(e){}
			try{
				toDate = new Date(searchDateTo);
			}catch(e){}
			if(fromDate!=null && toDate!=null){
				if(fromDate>toDate){
					smartPop.showInfo(smartPop.ERROR, smartMessage.get("eventOldEndDateError"));
					$('input[name="txtSearchDateTo"]').attr('value', '')
					return;
				}
			}
			var maximumDate = (new Date()).getTime();
			if(summaryType==='byDay') maximumDate = 60*24*60*60*1000;
			else if(summaryType==='byWeek') maximumDate = 365*24*60*60*1000;
			else if(summaryType==='byMonth') maximumDate = 4*365*24*60*60*1000;
			if((toDate.getTime()-fromDate.getTime())>maximumDate){
				smartPop.showInfo(smartPop.ERROR, "기간별 조회한계(일별:2달, 주별:1년, 월별:4년)를 초과 하였습니다. 다시 선택하여 주시기 바랍니다.");				
				return;
			}
		}
		selectListParam(progressSpan, false);
	};
	
	selectSummaryType = function(progressSpan){
		var forms = $('form:visible');
		var paramsJson = {};
		for(var i=0; i<forms.length; i++){
			var form = $(forms[i]);
			paramsJson[form.attr('name')] =form.serializeObject();
		}
		getIntanceList(paramsJson, progressSpan, false);		
	};
	
}catch(error){
}
</script>

<body>

	<%
		String listType = RequestParams.LIST_TYPE_TEST, selectorType = "", searchType = "", searchLotNo = "", searchDateFrom = "", searchDateTo = "";
		RequestParams params = new RequestParams();
		params.setPageSize(20);
		params.setCurrentPage(1);
		params.setSortingField(new SortingField(TestReport.FIELD_ID_DATETIME, false));
		params.setListType(RequestParams.LIST_TYPE_TEST);
		listType = params.getListType();
		selectorType = params.getSelectorType();
		searchType = params.getSearchType();
		searchLotNo = params.getSearchLotNo();
		searchDateFrom = params.getSearchDateFrom();
		searchDateTo = params.getSearchDateTo();
		
		request.setAttribute("requestParams", params);

	%>
	<!-- 목록영역  -->
	<div class="contents_space">
		<!-- 목록보기 -->
		<div>
			<!-- 목록보기 타이틀-->
			<div class="list_title_space mt15">
				<form name="frmSearchInstance" class="title_line_btns">
					<span class="po_left js_progress_span mt4"></span>
					
			  		<div <%if(!RequestParams.LIST_TYPE_SUMMARY.equals(listType) && !RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>style="display:none"<%} %> class="po_left mt3 js_search_datetime" onChange="selectSearchDate($(this).siblings('.js_progress_span:first'));return false;">
						<a href="" class="t_date linkline js_remove_search_date" <%if(SmartUtil.isBlankObject(searchDateFrom) && SmartUtil.isBlankObject(searchDateTo)){ %>style="display:none"<%} %>>삭제</a>
			  			<div class="icon_fb_space" style="display:inline-block">
			  				<input readonly="readonly" style="width:80px" type="text" name="txtSearchDateFrom" class="fieldline js_todaypicker" value="<%=SmartUtil.toNotNull(searchDateFrom) %>"/>
			  				<a href class="js_todaypicker_button"><span class="icon_fb_date"></span></a>
			  			</div>
			  			 ~ 
			  			<div class="icon_fb_space" style="display:inline-block">
			  				<input readonly="readonly" style="width:80px" type="text" name="txtSearchDateTo" class="fieldline js_todaypicker" value="<%=SmartUtil.toNotNull(searchDateTo) %>"/>
			  				<a href class="js_todaypicker_button"><span class="icon_fb_date"></span></a>
			  			</div>
			  		</div> 
					<div <%if(RequestParams.LIST_TYPE_SUMMARY.equals(listType) || RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>style="display:none"<%} %> class="srch_wh srch_wsize po_left ml10 mt3 js_search_lotno">
						<input name="txtSearchLotNo" class="nav_input" value="<%=SmartUtil.toNotNull(searchLotNo)%>" type="text" placeholder="검색">
						<button title="로트번호 검색" onclick="selectListParam($(this).parent().siblings('.js_progress_span:first'), false);return false;"></button>
					</div>
					<select <%if(RequestParams.LIST_TYPE_SUMMARY.equals(listType)){ %>style="display:none"<%} %> name="selSearchType" class="form_space po_left js_select_search_type">
						<option value="<%=RequestParams.SEARCH_TYPE_LOTNO %>" <%if(RequestParams.SEARCH_TYPE_LOTNO.equals(searchType)){%>selected<%} %>>로트번호 검색</option>
						<option value="<%=RequestParams.SEARCH_TYPE_DATETIME %>" <%if(RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>selected<%} %>>날짜 검색</option>
					</select>
					<select <%if(RequestParams.LIST_TYPE_TEST.equals(listType)){ %>style="display:none"<%} %> name="selSummaryType" class="form_space po_left js_select_summary_type">
						<option value="<%=SummaryReportCond.SELECTOR_DAILY %>" <%if(SummaryReportCond.SELECTOR_DAILY.equals(selectorType)){%>selected<%} %>>일별</option>
						<option value="<%=SummaryReportCond.SELECTOR_WEEKLY %>" <%if(SummaryReportCond.SELECTOR_WEEKLY.equals(selectorType)){%>selected<%} %>>주별</option>
						<option value="<%=SummaryReportCond.SELECTOR_MONTHLY %>" <%if(SummaryReportCond.SELECTOR_MONTHLY.equals(selectorType)){%>selected<%} %>>월별</option>
						<option value="<%=SummaryReportCond.SELECTOR_YEARLY %>" <%if(SummaryReportCond.SELECTOR_YEARLY.equals(selectorType)){%>selected<%} %>>년별</option>
					</select>
				</form>			
				<div class="title_line_options titl_section">
					<!-- 타이틀을 나타내는 곳 -->
					<div class="title list_type js_select_list_type">
						<span><a class="linkline" href="" listType="<%=RequestParams.LIST_TYPE_TEST%>">검사별</a></span> | 
						<span class="unselected"><a class="linkline" href="" listType="<%=RequestParams.LIST_TYPE_SUMMARY%>">기간별</a></span>
					</div>					
					<span class="js_progress_span"></span>
				</div>
			</div>
			<!-- 목록보기 타이틀-->
	
			<!-- 목록 테이블 -->
			<div class="list_contents">
				<div id='test_list_page' >
					<%if(RequestParams.LIST_TYPE_TEST.equals(listType)){ %>
						<jsp:include page="/jsp/test_list.jsp"/>
					<%}else if(RequestParams.LIST_TYPE_SUMMARY.equals(listType)){ %>
						<jsp:include page="/jsp/summary_list.jsp"/>
					<%} %>
				</div>
			</div>
			<!-- 목록 테이블 //-->
		</div>
		<!-- 목록 보기 -->
		<div class="js_test_detail_page"></div>	
	</div>
	<!-- 목록영역 // -->

<script>
	smartCommon.liveTodayPicker();
</script>

</body>
</html>
