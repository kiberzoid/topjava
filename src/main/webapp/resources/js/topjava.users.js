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

function changeEnabled(ch, userId) {
    var checked = ch.prop("checked");
    console.log(checked);
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "checked",
        data: {'id': userId, 'enabled': checked},
    }).done(function () {
        ch.closest('tr').attr("data-userEnabled", checked);
        checked ? successNoty("Enabled") : successNoty("Disabled");
    }).fail(function () {
        ch.prop("checked",!checked);
    });
}