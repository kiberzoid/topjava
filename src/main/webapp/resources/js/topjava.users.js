var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    };
    makeEditable();
});

function changeEnabled(id) {
    var checked = ($("#checkbox" + id).prop("checked") === true);
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "checked",
        data: "id=" + id + "&enabled=" + checked
    }).done(function () {
        if (filtered) {
            filter();
        } else {
            updateTable();
        }
        successNoty("Updated");
    });
}