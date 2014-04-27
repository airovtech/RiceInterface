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
			url : "/RiceInterface/jsp/set_test_list_params.jsp?data=" + JSON.stringify(paramsJson),
			contentType : 'application/json',
			type : 'POST',
			success : function(data, status, jqXHR) {
				$('#test_list_page').html(data);
				smartPop.closeProgress();
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
		}
		selectListParam(progressSpan, false);
	};
	
}catch(error){
}
</script>

<body>

	<%
		RequestParams params = (RequestParams)request.getAttribute("requestParams");
		String searchType = "", searchLotNo = "", searchDateFrom = "", searchDateTo = "";
		if (params == null){
			String savedWorkId = (String)session.getAttribute("workId");
			if(!SmartUtil.isBlankObject(savedWorkId) && savedWorkId.equals("RiceInterface")){
				params = (RequestParams)session.getAttribute("requestParams");
			}
		}
		if (params != null){
			searchType = params.getSearchType();
			searchLotNo = params.getSearchLotNo();
			searchDateFrom = params.getSearchDateFrom();
			searchDateTo = params.getSearchDateTo();
		}
	%>
	<!-- 목록영역  -->
	<div class="contents_space">
		<div>
<%-- 			<jsp:include page="/jsp/test_chart.jsp">
				<jsp:param value="" name="testId"/>
			</jsp:include>
 --%>		</div>
		<!-- 목록보기 -->
		<div>
			<!-- 목록보기 타이틀-->
			<div class="list_title_space mt15">
				<form name="frmSearchInstance" class="title_line_btns">
					<span class="po_left js_progress_span mt4"></span>
					
			  		<div <%if(!RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>style="display:none"<%} %> class="po_left mt3 js_search_datetime" onChange="selectSearchDate($(this).siblings('.js_progress_span:first'));return false;">
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
					<div <%if(RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>style="display:none"<%} %> class="srch_wh srch_wsize po_left ml10 mt3 js_search_lotno">
						<input name="txtSearchLotNo" class="nav_input" value="<%=SmartUtil.toNotNull(searchLotNo)%>" type="text" placeholder="검색">
						<button title="로트번호 검색" onclick="selectListParam($(this).parent().siblings('.js_progress_span:first'), false);return false;"></button>
					</div>
					<select name="selSearchType" class="form_space po_left js_select_search_type">
						<option value="<%=RequestParams.SEARCH_TYPE_LOTNO %>" <%if(RequestParams.SEARCH_TYPE_LOTNO.equals(searchType)){%>selected<%} %>>로트번호 검색</option>
						<option value="<%=RequestParams.SEARCH_TYPE_DATETIME %>" <%if(RequestParams.SEARCH_TYPE_DATETIME.equals(searchType)){%>selected<%} %>>날짜 검색</option>
					</select>
				</form>
			
				<div class="title_line_options titl_section">
					<!-- 타이틀을 나타내는 곳 -->
					<div class="title">
						<span class="current"><a class="linkline" href="" viewType="listByTest">테스트별</a></span> | 
						<span class=""><a style="font-size:12px;color:#888888" class="linkline" href="" viewType="listByPeriod">기간별</a></span>
					</div>					
					<span class="js_progress_span"></span>
				</div>
			</div>
			<!-- 목록보기 타이틀-->
	
			<!-- 목록 테이블 -->
			<div class="list_contents">
				<div id='test_list_page' >
					<jsp:include page="/jsp/test_list.jsp">
						<jsp:param value="" name="workId"/>
					</jsp:include>
				</div>
			</div>
			<!-- 목록 테이블 //-->
		</div>
		<!-- 목록 보기 -->
	</div>
	<!-- 목록영역 // -->

<script>
	smartCommon.liveTodayPicker();
</script>

</body>
</html>
