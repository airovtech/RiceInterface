<%@page import="net.smartworks.model.SortingField"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.sun.xml.internal.xsom.impl.scd.Iterators.Map"%>
<%@page import="net.smartworks.model.RequestParams"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%

	try{
		RequestParams requestParams = new RequestParams();
		
		JSONObject requestBody = new JSONObject(request.getParameter("data"));
 
		JSONObject frmSearchInstance = new JSONObject(requestBody.get("frmSearchInstance").toString());
		if(frmSearchInstance != null) {
			String selSearchType = "", txtSearchLotNo = "", txtSearchDateFrom = "", txtSearchDateTo = "";
			try{
				selSearchType = (String)frmSearchInstance.get("selSearchType");
			}catch(Exception e){}
			try{
				txtSearchLotNo = (String)frmSearchInstance.get("txtSearchLotNo");
			}catch(Exception e){}
			try{
				txtSearchDateFrom = (String)frmSearchInstance.get("txtSearchDateFrom");
			}catch(Exception e){}
			try{
				txtSearchDateTo = (String)frmSearchInstance.get("txtSearchDateTo");
			}catch(Exception e){}
			requestParams.setSearchType(selSearchType);
			requestParams.setSearchLotNo(txtSearchLotNo);
			requestParams.setSearchDateFrom(txtSearchDateFrom);
			requestParams.setSearchDateTo(txtSearchDateTo);
		}

		JSONObject frmSortingField = new JSONObject(requestBody.get("frmSortingField").toString());
		if(frmSortingField != null){
			String hdnSortingFieldId = (String)frmSortingField.get("hdnSortingFieldId");
			String hdnSortingIsAscending = (String)frmSortingField.get("hdnSortingIsAscending");
			SortingField sortingField = new SortingField();
			sortingField.setFieldId(hdnSortingFieldId);
			sortingField.setAscending(Boolean.parseBoolean(hdnSortingIsAscending));
			requestParams.setSortingField(sortingField);
		}

		JSONObject existListPaging = new JSONObject(requestBody.get("frmInstanceListPaging").toString());

		String hdnCurrentPage = (String)existListPaging.get("hdnCurrentPage");
		String selPageSize = (String)existListPaging.get("selPageSize");
		boolean hdnNext10=false,hdnNextEnd=false,hdnPrev10=false,hdnPrevEnd=false;
		try{
			if(existListPaging.get("hdnNext10")!=null) hdnNext10 = Boolean.parseBoolean((String)existListPaging.get("hdnNext10"));
		}catch(Exception e){}
		try{
			if(existListPaging.get("hdnNextEnd")!=null) hdnNextEnd = Boolean.parseBoolean((String)existListPaging.get("hdnNextEnd"));
		}catch(Exception e){}
		try{
			if(existListPaging.get("hdnPrev10")!=null) hdnPrev10 = Boolean.parseBoolean((String)existListPaging.get("hdnPrev10"));
		}catch(Exception e){}
		try{
			if(existListPaging.get("hdnPrevEnd")!=null) hdnPrevEnd = Boolean.parseBoolean((String)existListPaging.get("hdnPrevEnd"));
		}catch(Exception e){}
		if(hdnCurrentPage != null)
			requestParams.setCurrentPage(Integer.parseInt(hdnCurrentPage));
		if(selPageSize != null)
			requestParams.setPageSize(Integer.parseInt(selPageSize));
		if(hdnNext10)
			requestParams.setPagingAction(RequestParams.PAGING_ACTION_NEXT10);
		else if(hdnNextEnd)
			requestParams.setPagingAction(RequestParams.PAGING_ACTION_NEXTEND);
		else if(hdnPrev10)
			requestParams.setPagingAction(RequestParams.PAGING_ACTION_PREV10);
		else if(hdnPrevEnd)
			requestParams.setPagingAction(RequestParams.PAGING_ACTION_PREVEND);

		session.setAttribute("requestParams", requestParams);
		session.setAttribute("workId", "RiceInterface");
		
   	}catch (Exception e){
		// Exception Handling Required
		e.printStackTrace();
		// Exception Handling Required			
	}
%>

<jsp:include page="/jsp/test_list.jsp"></jsp:include>
