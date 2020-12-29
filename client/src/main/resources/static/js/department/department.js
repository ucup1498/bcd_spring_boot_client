/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('#accordionSidebar #department').addClass('active');
});


function deleteData(id, name) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-danger mr-3',
            cancelButton: 'btn btn-secondary'
        },
        buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You will delete '" + name + "' from database",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            $.post("department/delete", {
                id: id
            }, null);
            Toast.fire({
                icon: 'success',
                title: 'Data deleted',
                timer: 2000,
                timerProgressBar: true
            })
            setTimeout(function () {
                location.reload()
            }, 2000);
        }
    })
}

const Toast = Swal.mixin({
    toast: true,
    position: 'top-right',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

function deleteToast() {


    Toast.fire({
        icon: 'success',
        title: 'Data deleted successfully'
    })
}

function getFormData(id, name, manager, location, action) {

    $('form #id').val(id);
    $('form #name').val(name);
    $('form #manager').val(manager);
    $('form #location').val(location);

    switch (action) {
        case "add":
            $('#departmentModalTitle').html("Add New Department");
            $('#department-form').prop("action", "department/post");
            $('#department-form').prop("method", "post");
            break;
        case "detail":
            $('#departmentModalTitle').html("Department Info");
            break;
        case "edit":
            $('#departmentModalTitle').html("Edit Department");
            $('#department-form').prop("action", "department/update");
            $('#department-form').prop("method", "post");
            break;
        default:
            break;
    }
    setEnableForm(action);
}


function setEnableForm(action) {
    isDisable = action === 'detail';

    if (action === 'add') {
        $('form #id').prop("readonly", false);
    } else {
        $('form #id').prop("readonly", true);
    }
    $('form #name').prop("readonly", isDisable);
    $('form #manager').prop("readonly", isDisable);
    $('form #location').prop("readonly", isDisable);

    if (isDisable) {
        $('form #action-button').hide();
    } else {
        $('form #action-button').show();
    }
}

function submitForm() {
    if (document.getElementById("department-form").checkValidity()) {
        $('#departmentModal').modal('hide');
        Toast.fire({
            icon: 'success',
            title: 'Data Updated',
            timer: 2000
        })
    }
    setTimeout(function () {
        location.reload()
    }, 2000);
}

var job = {};
var table;

$(document).ready(function () {
    getAll();
    $('#jobForm').submit(function (event) {
        event.preventDefault();
        insert();
    });
});

function getAll() {
    table = $('#jobTable').DataTable({
        "sAjaxSource": "/job/get-all?keyword=",
        "sAjaxDataProp": "",
        "order": [[0, "asc"]],
        "columns": [
            {"data": "id"},
            {"data": "title"},
            {"data": "minSalary"},
            {"data": "maxSalary"},
            {
                "render": function (data, type, row, meta) {
                    console.log(row.id)
                    return "<button class='btn btn-sm btn-success' data-toggle='modal' data-target='#jobModal' \n\
                    onclick='setForm(\""+ row +"\")'>Edit</button>\n\
                    <button class='btn btn-sm btn-danger'>Delete</button>";
                }
            }
        ]
    });
}

function setForm(row) {
    console.log();
}

function getValueForm() {
    job.id = $('#id').val();
    job.title = $('#title').val();
    job.minSalary = $('#minSalary').val();
    job.maxSalary = $('#maxSalary').val();
}

function insert() {
    getValueForm();
    $.ajax({
        type: 'POST',
        url: '/job',
        contentType: "application/json",
        data: JSON.stringify(job),
        success: function (data) {
            table.destroy();
            getAll();
            $('#jobModal').modal('hide');
        },
        error: function (e) {
            console.log(e);
            alert('Failed');
        }
    });
}