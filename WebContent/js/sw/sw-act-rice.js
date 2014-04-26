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
			if(input.find('option:selected').attr('value') === "searchLotNo"){
				searchInstance.find('.js_search_lotno').show();
				searchInstance.find('.js_search_datetime').hide();
			}else{
				searchInstance.find('.js_search_lotno').hide();
				searchInstance.find('.js_search_datetime').show();
			}
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_select_search_type]', null, error);
		}			
		return false;
	});
	
	$('.js_remove_search_date').live("click", function(e){
		try{
			var input = $(targetElement(e));
			$('input[name="txtSearchDateFrom"]').attr('value', '');
			$('input[name="txtSearchDateTo"]').attr('value', '');			
			selectSearchDate(input.parent().siblings('.js_progress_span:first'));
		}catch(error){
			smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice js_remove_search_date]', null, error);
		}			
		return false;
	});
	


});
}catch(error){
	smartPop.showInfo(smartPop.ERROR, smartMessage.get('technicalProblemOccured') + '[sw-act-rice script]', null, error);
}