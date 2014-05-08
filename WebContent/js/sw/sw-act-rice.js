try{
$(function() {
	
	$('a.js_select_paging').live("click", function(e){
		try{
			var input = $(targetElement(e)).parents('a.js_select_paging');
			input.find('input').attr('value', 'true');
			var progressSpan = input.siblings('span.js_progress_span:first');
			if(isEmpty(input.parents('.js_pop_instance_list_page'))){
				selectListParam(progressSpan, false);
			}else{
				popSelectListParam(progressSpan, false);
			}
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_paging]', null, error);
		}			
		return false;
	});
	
	$('a.js_select_current_page').live("click", function(e){
		try{
			var input = $(targetElement(e));
			var progressSpan = input.siblings('span.js_progress_span:first');
			input.siblings('input[name="hdnCurrentPage"]').attr('value', input.text());
			if(isEmpty(input.parents('.js_pop_instance_list_page'))){
				selectListParam(progressSpan, false);
			}else{
				popSelectListParam(progressSpan, false);
			}
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_currnet_page]', null, error);
		}			
		return false;
	});
	
	$('.js_select_page_size').live("change", function(e){
		try{
			var input = $(targetElement(e));
			var progressSpan = input.siblings('span.js_progress_span:first');
			if(isEmpty(input.parents('.js_pop_instance_list_page'))){
				selectListParam(progressSpan, false);
			}else{
				popSelectListParam(progressSpan, false);
			}
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_page_size]', null, error);
		}			
		return false;
	});
	
	$('a.js_select_field_sorting').live('click', function(e){
		try{
			var input = $(targetElement(e));
			var popInstanceList = input.parents('.js_pop_instance_list_page');
			var sortingField = $('form[name="frmSortingField"]').find('input[name="hdnSortingFieldId"]');
			var sortingIsAscending = $('form[name="frmSortingField"]').find('input[name="hdnSortingIsAscending"]');
			if(!isEmpty(popInstanceList)){
				sortingField = popInstanceList.find('form[name="frmSortingField"]').find('input[name="hdnSortingFieldId"]');
				sortingIsAscending = popInstanceList.find('form[name="frmSortingField"]').find('input[name="hdnSortingIsAscending"]');			
			}
			if(sortingField.attr('value') === input.attr('fieldId')){
				var isAscending = sortingIsAscending.attr('value');
				sortingIsAscending.attr('value', (isAscending === "true") ? "false" : "true");
			}else{
				sortingField.attr('value', input.attr('fieldId'));
				sortingIsAscending.attr('value', 'false');
			}
			var progressSpan = input.siblings('.js_progress_span:first');
			if(isEmpty(input.parents('.js_pop_instance_list_page'))){
				selectListParam(progressSpan, false);
			}else{
				popSelectListParam(progressSpan, false);
			}
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_field_sorting]', null, error);
		}			
		return false;
	});

	$('.js_select_search_type').live("change", function(e){
		try{
			var input = $(targetElement(e));
			var searchInstance = input.parents('form[name="frmSearchInstance"]');
			$('input[name="txtSearchDateFrom"]').attr('value', '');
			$('input[name="txtSearchDateTo"]').attr('value', '');
			$('input[name="txtSearchLotNo"]').attr('value', '');
			if(input.find('option:selected').attr('value') === "searchLotNo"){
				searchInstance.find('.js_search_lotno').show();
				searchInstance.find('.js_search_datetime').hide();
			}else{
				searchInstance.find('.js_search_lotno').hide();
				searchInstance.find('.js_search_datetime').show();
			}
			selectListParam(input.siblings('.js_progress_span:first'), false);
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_search_type]', null, error);
		}			
		return false;
	});
	
	$('.js_remove_search_date').live("click", function(e){
		try{
			var input = $(targetElement(e));
			
			var maximumDate = 0;
			var summaryType = $('.js_select_summary_type:visible option:selected').attr('value');
			if(summaryType==='byDay') maximumDate = 60*24*60*60*1000;
			else if(summaryType==='byWeek') maximumDate = 365*24*60*60*1000;
			else if(summaryType==='byMonth') maximumDate = 4*365*24*60*60*1000;
						
			$('input[name="txtSearchDateFrom"]').attr('value', maximumDate==0?'':(new Date((new Date()).getTime()-maximumDate)).format('yyyy.mm.dd'));
			$('input[name="txtSearchDateTo"]').attr('value', maximumDate==0?'':(new Date()).format('yyyy.mm.dd'));			
			selectSearchDate(input.parent().siblings('.js_progress_span:first'), input);
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_remove_search_date]', null, error);
		}			
		return false;
	});
	
	$('.js_select_test_report').live("click", function(e){
		try{
			smartPop.progressCenter();
			var input = $(targetElement(e));
			if(!input.hasClass('js_select_test_report')) input = input.parents('.js_select_test_report');
			var reportId = input.attr('reportId');
			$.ajax({url : "/RiceInterface/jsp/test_detail.jsp?reportId=" + reportId, success : function(data, status, jqXHR) {
					try{
						$('.js_test_detail_page').html(data);
						smartPop.closeProgress();
					}catch(error){
						smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_test_report test_detail.jsp]', null, error);
					}			
				}
			});
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_test_report]', null, error);
		}			
		return false;
	});
	
	$('.js_select_list_type').live("click", function(e){
		try{
			smartPop.progressCenter();
			var input = $(targetElement(e));
			var listType = input.attr('listType');
			var url = (listType === 'testList') ? "/RiceInterface/jsp/test_list.jsp" : "/RiceInterface/jsp/summary_list.jsp";
			$.ajax({url : url, data : { cleanup : 'true'}, success : function(data, status, jqXHR) {
					try{
						cleanupListParams(listType);
						$('#test_list_page').html(data);
						input.parent().removeClass('unselected').siblings().addClass('unselected');
						if(listType === 'testList'){
							$('.js_test_detail_page').html('');
							smartPop.closeProgress();
						}else{
							$.ajax({url : '/RiceInterface/jsp/summary_chart.jsp', success : function(data, status, jqXHR) {
									$('.js_test_detail_page').html(data);
									smartPop.closeProgress();
								}
							});
						}
					}catch(error){
						smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_list_type ' + url + ']', null, error);
					}			
				}
			});
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_list_type]', null, error);
		}			
		return false;
	});
	
	$('.js_select_summary_type').live("change", function(e){
		try{
			var input = $(targetElement(e));
			var selectedType = input.find('option:selected').attr('value');
			var fromDate = new Date();
			var fromStr = "";
			if(selectedType === "byDay"){
				fromStr = (new Date(fromDate.getTime()-60*24*60*60*1000)).format("yyyy.mm.dd");
			}else if(selectedType === "byWeek"){
				fromStr = new Date(fromDate.getTime()-365*24*60*60*1000).format("yyyy.mm.dd");
			}else if(selectedType === "byMonth"){
				fromStr = new Date(fromDate.getTime()-4*365*24*60*60*1000).format("yyyy.mm.dd");
			}
			$('input[name="txtSearchDateFrom"]').attr('value', fromStr);
			$('input[name="txtSearchDateTo"]').attr('value', fromStr===''?'':(new Date()).format('yyyy.mm.dd'));			
			selectSummaryType(input.siblings('.js_progress_span:first'), false);
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_search_type]', null, error);
		}			
		return false;
	});
	
	$('.js_pop_detail_chart').live('click', function(e){
		try{
			var input = $(targetElement(e));
			var fromDate = input.attr('fromDate');
			var toDate = input.attr('toDate');
			var selectorType = input.attr('selectorType');
			var testDate = input.attr('testDate');
			var top = input.offset().top+ input.height() ;
			smartPop.showDetailChart(fromDate, toDate, selectorType, testDate, top, window.innerWidth);
			return false;
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_pop_detail_chart]', null, error);
		}
	});
			
});

var cleanupListParams = function(listType){
	$('input[name="txtSearchDateTo"]').attr('value', '');
	$('input[name="txtSearchLotNo"]').attr('value', '');
	if(listType === 'testList'){
		$('input[name="txtSearchDateFrom"]').attr('value', '');
		$('.js_search_datetime').hide();
		$('.js_search_lotno').show();
		$('.js_select_search_type').show().find('option:first').attr('selected', 'selected');
		$('.js_select_summary_type').hide();
	}else if(listType === 'summaryList'){
		var today = new Date();
		var fromDate = new Date(today.getTime()-(4*365*24*60*60*1000));
		$('input[name="txtSearchDateFrom"]').attr('value', fromDate.format('yyyy.mm.dd'));
		$('input[name="txtSearchDateTo"]').attr('value', (new Date()).format('yyyy.mm.dd'));
		$('.js_search_datetime').show();
		$('.js_search_lotno').hide();
		$('.js_select_search_type').hide();
		$('.js_select_summary_type').show().find('option[value="byMonth"]').attr('selected', 'selected');
	}
};

}catch(error){
	smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice script]', null, error);
}