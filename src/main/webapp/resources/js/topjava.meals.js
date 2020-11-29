var ctx;
var filterForm;

$(function () {
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    filterForm = $('#filterForm');
    makeEditable();
});

function filter(){
    filtered = 'filtered';
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + 'filter',
        data: filterForm.serialize()
    }).done(function (data) {
        console.log(data);
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function releaseFilter(){
    filterForm.trigger('reset');
    filtered = undefined;
    updateTable();
}