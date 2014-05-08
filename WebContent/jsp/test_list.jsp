<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.common.Order"%>
<%@page import="net.smartworks.model.SortingField"%>
<%@page import="net.smartworks.rice.model.TestReport"%>
<%@page import="net.smartworks.rice.model.TestReportCond"%>
<%@page import="net.smartworks.rice.manager.IUiManager"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.model.RequestParams"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%

	String cleanup = request.getParameter("cleanup");
	RequestParams requestParams = (RequestParams)request.getAttribute("requestParams");
	
	if(SmartUtil.isBlankObject(requestParams) || "true".equals(cleanup)){
		String savedWorkId = (String)session.getAttribute("workId");
		requestParams = (RequestParams)session.getAttribute("requestParams");
		if(!RequestParams.LIST_TYPE_TEST.equals(savedWorkId) || SmartUtil.isBlankObject(requestParams)){
			requestParams = new RequestParams();
			requestParams.setPageSize(20);
			requestParams.setCurrentPage(1);
			requestParams.setSortingField(new SortingField(TestReport.FIELD_ID_DATETIME, false));
			requestParams.setListType(RequestParams.LIST_TYPE_TEST);
		}
	}
	session.setAttribute("requestParams", requestParams);
	session.setAttribute("workId", RequestParams.LIST_TYPE_TEST);
	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	TestReportCond reportCond = new TestReportCond();
	if(RequestParams.SEARCH_TYPE_LOTNO.equals(requestParams.getSearchType()) && !SmartUtil.isBlankObject(requestParams.getSearchLotNo())){
		reportCond.setLotNoLike(requestParams.getSearchLotNo());
	}else if(RequestParams.SEARCH_TYPE_DATETIME.equals(requestParams.getSearchType())){
 		if(!SmartUtil.isBlankObject(requestParams.getSearchDateFrom())){
		 	reportCond.setDateTimeFrom(SmartUtil.convertDateStringToDate(requestParams.getSearchDateFrom()));
 		}
 		if(!SmartUtil.isBlankObject(requestParams.getSearchDateTo())){
		 	reportCond.setDateTimeTo(SmartUtil.convertDateStringToDate(requestParams.getSearchDateTo()));
		}
	 	if(reportCond.getDateTimeFrom()==null) requestParams.setSearchDateFrom("");
	 	if(reportCond.getDateTimeTo()==null) requestParams.setSearchDateTo("");
	}
	int totalSize = mgr.getTestReportSize(reportCond);
	if(totalSize<requestParams.getPageSize()*(requestParams.getCurrentPage()-1)){
		requestParams.setCurrentPage(1);
		session.setAttribute("requestParams", requestParams);
	}
	reportCond.setPageNo(requestParams.getCurrentPage());
	reportCond.setPageSize(requestParams.getPageSize());
	reportCond.setOrders(new Order[]{new Order(requestParams.getSortingField().getFieldId(), requestParams.getSortingField().isAscending())});
	TestReport[] reports = mgr.getTestReports(reportCond);
%>

<!-- 목록 테이블 -->
<table>
	<%	
	SortingField sortedField = new SortingField(TestReport.FIELD_ID_DATETIME, false);
	int pageSize = 20, totalPages = 1, currentPage = 1;
	if (!SmartUtil.isBlankObject(reports)) {
		sortedField = requestParams.getSortingField();
		if(sortedField==null) sortedField = new SortingField();
	%>
	
		<tr class="tit_bg">
	 		<th class="r_line" style="width:40px;">
				<span>번호</span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_LOTNO%>">로트 번호
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_LOTNO)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_SENSOR_SN%>">시리얼 번호
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_SENSOR_SN)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_DATETIME%>">날짜 및 시간
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_DATETIME)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">검사 수량</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_FQCOUNT%>">양품 수량
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_FQCOUNT)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th>
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_FAULTCOUNT%>">불량 수량
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_FAULTCOUNT)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
		</tr>	
	<%
		pageSize = requestParams.getPageSize();
		currentPage = requestParams.getCurrentPage();
		totalPages = (int)totalSize % pageSize;
		if(totalPages == 0)
			totalPages = (int)totalSize / pageSize;
		else
			totalPages = (int)totalSize / pageSize + 1;
		int currentCount = totalSize-(currentPage-1)*pageSize;
		for (TestReport report : reports) {
		%>
			<tr class="instance_list js_select_test_report" href="" reportId="<%=report.getId()%>">
				<td class="tc"><%=currentCount%></td>
				<%
				currentCount--;
				%>
				<td class="tc"><%=report.getLotNo() %></td>
				<td class="tc"><%=report.getFirstSensorId() %></td>
				<td class="tc"><%=SmartUtil.printDateTime(report.getDateTime())%></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getTotalTestCount()) %></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getFairQualityCount()) %></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getFaultCount()) %></td>
			</tr>
	<%
		}
	}else {
	%>
		<tr class="tit_bg">
	 		<th class="r_line" style="width:40px;">
				<span>번호</span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_LOTNO%>">로트 번호
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_LOTNO)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_SENSOR_SN%>">시리얼 번호
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_SENSOR_SN)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_DATETIME%>">날짜 및 시간
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_DATETIME)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th class="r_line">검사 수량</th>
	 		<th class="r_line">
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_FQCOUNT%>">양품 수량
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_FQCOUNT)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
	 		<th>
	 			<a href="" class="js_select_field_sorting" fieldId="<%=TestReport.FIELD_ID_FAULTCOUNT%>">불량 수량
			 		<span class="<%
					if(sortedField.getFieldId().equals(TestReport.FIELD_ID_FAULTCOUNT)){
						if(sortedField.isAscending()){ %>icon_in_up<%}else{ %>icon_in_down<%}}%>"></span>
				</a>
				<span class="js_progress_span"></span>
			</th>
		</tr>	
	<%
	}
	%>
</table>
<%
if(SmartUtil.isBlankObject(reports)){
%>
	<div class="tc mt5mb5">테스트 항목이 존재하지 않습니다.</div>
<%
}
if(!SmartUtil.isBlankObject(sortedField)){
%>
	<form name="frmSortingField">
		<input name="hdnSortingFieldId" type="hidden" value="<%=sortedField.getFieldId()%>">
		<input name="hdnSortingIsAscending" type="hidden" value="<%=sortedField.isAscending()%>">
	</form>
<%
}
%>
<!-- 목록 테이블 //-->

<form name="frmInstanceListPaging">
	<!-- 페이징 -->
	<div class="paginate">
		<%
		if (currentPage > 0 && totalPages > 0 && currentPage <= totalPages) {
			boolean isFirst10Pages = (currentPage <= 10) ? true : false;
			boolean isLast10Pages = (((currentPage - 1)  / 10) == ((totalPages - 1) / 10)) ? true : false;
			int startPage = ((currentPage - 1) / 10) * 10 + 1;
			int endPage = isLast10Pages ? totalPages : startPage + 10 - 1;
			if (!isFirst10Pages) {
		%>
				<a class="pre_end js_select_paging" href="" title="<fmt:message key='common.title.first_page'/>">
					<span class="spr"></span>
					<input name="hdnPrevEnd" type="hidden" value="false"> 
				</a>		
				<a class="pre js_select_paging" href="" title="<fmt:message key='common.title.prev_10_pages'/> ">
					<span class="spr"></span>
					<input name="hdnPrev10" type="hidden" value="false">
				</a>
			<%
			}
			for (int num = startPage; num <= endPage; num++) {
				if (num == currentPage) {
			%>
					<strong><%=num%></strong>
					<input name="hdnCurrentPage" type="hidden" value="<%=num%>"/>
				<%
				} else {
				%>
					<a class="num js_select_current_page" href=""><%=num%></a>
				<%
				}
			}
			if (!isLast10Pages) {
			%>
				<a class="next js_select_paging" title="<fmt:message key='common.title.next_10_pages'/> ">
					<span class="spr"></span>
					<input name="hdnNext10" type="hidden" value="false"/>
				</a>
				<a class="next_end js_select_paging" title="<fmt:message key='common.title.last_page'/> ">
					<span class="spr"><input name="hdnNextEnd" type="hidden" value="false"/></span> 
				</a>
		<%
			}
		}
		%>
		<span class="js_progress_span"></span>
	</div>
	
	<div class="num_box">
		<span class="js_progress_span"></span>
		<select class="js_select_page_size" name="selPageSize" title="<fmt:message key='common.title.count_in_page'/>">
			<option <%if (pageSize == 20) {%> selected <%}%>>20</option>
			<option <%if (pageSize == 30) {%> selected <%}%>>30</option>
			<option <%if (pageSize == 50) {%> selected <%}%>>50</option>
			<option <%if (pageSize == 100) {%> selected <%}%>>100</option>
		</select>
	</div>
	<!-- 페이징 //-->
</form>
