var ctx, mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    ctx = {
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function(data, type, row){
                        if (type === "display") {
                            return data.replace('T', ' ').substr(0, 16);
                        }
                        return data;
                    }
                },
                {
                    "data": "description",
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function(row, data){
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    };
    makeEditable();
});

var startDate = $('#startDate');
var endDate = $('#endDate');
var startTime = $('#startTime');
var endTime = $('#endTime');

startDate.datetimepicker({
    timepicker:false,
    format:'Y-m-d',
    onShow : function (){
        this.setOptions({
            maxDate: endDate.val() ? endDate.val() : false
        });
        console.log(this.maxDate);
    }
});

endDate.datetimepicker({
    timepicker:false,
    format:'Y-m-d',
    onShow : function (){
        this.setOptions({
            minDate: startDate.val() ? startDate.val() : false
        });
        console.log(this.minDate);
    }
});

startTime.datetimepicker({
    datepicker:false,
    format:'H:i',
    onShow : function (){
        this.setOptions({
            maxTime: endTime.val() ? endTime.val() : false
        })
    }
});

endTime.datetimepicker({
    datepicker:false,
    format:'H:i',
    onShow : function (){
        this.setOptions({
            minTime: startTime.val() ? startTime.val() : false
        })
    }
});

$('#dateTime').datetimepicker({
    format:'Y-m-d H:i'
})