
<%@page import="java.util.logging.Logger"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.logging.Level"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="Auditoria.ModelDb"%>
<%@page import="org.json.JSONArray"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link href="https://unpkg.com/tabulator-tables@4.8.2/dist/css/semantic-ui/tabulator_semantic-ui.min.css" rel="stylesheet">
        <script type="text/javascript" src="https://unpkg.com/tabulator-tables/dist/js/tabulator.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Front Auditoria</title>
    </head>
    <body>

        <%@ page import = "Auditoria.ServiceAuditoriums"%>     

        <% ServiceAuditoriums s = new ServiceAuditoriums();%>        
        <script>let datos=<%= s.generateSqlConvertJson() %></script>
      
        <div class="input-group mb-3">
            <input id="buscarData" type="text" class="form-control" placeholder="Buscar  ..." aria-label="Recipient's username" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button id='btnSearch' class="btn btn-outline-info" type="button">Buscar</button>
            </div>
        </div>
        <div id="table"></div>

        <script>

     
            var table = new Tabulator("#table", {
                locale: true,
                langs: {
                    "es-es": {
                        "columns": {
                            "name": "Name", //replace the title of column name with the value "Name"
                        },
                        "data": {
                            "loading": "Loading", //data loader text
                            "error": "Error", //data error text
                        },
                        "groups": {//copy for the auto generated item count in group header
                            "item": "item", //the singular  for item
                            "items": "items", //the plural for items
                        },
                        "pagination": {
                            "page_size": "Page Size", //label for the page size select element
                            "page_title": "Show Page", //tooltip text for the numeric page button, appears in front of the page number (eg. "Show Page" will result in a tool tip of "Show Page 1" on the page 1 button)
                            "first": "Primero", //text for the first page button
                            "first_title": "First Page", //tooltip text for the first page button
                            "last": "Ultimo",
                            "last_title": "Last Page",
                            "prev": "Anterior",
                            "prev_title": "Prev Page",
                            "next": "Siguiente",
                            "next_title": "Next Page",
                            "all": "All",
                        },
                        "headerFilters": {
                            "default": "filter column...", //default header filter placeholder text
                            "columns": {
                                "name": "filter name...", //replace default header filter text for column name
                            }
                        }
                    }
                },
                data: datos, //load row data from array
                layout: "fitColumns", //fit columns to width of table
                responsiveLayout: "hide", //hide columns that dont fit on the table
                tooltips: true, //show tool tips on cells
                addRowPos: "top", //when adding a new row, add it to the top of the table
                history: true, //allow undo and redo actions on the table
                pagination: "local", //paginate the data
                paginationSize: 5, //allow 5 rows per page of data
                movableColumns: true, //allow column order to be changed
                resizableRows: true, //allow row order to be changed
                initialSort: [//set the initial sort order of the data
                    {column: "name", dir: "asc"},
                ],
                columns: [//define the table columns
                    {title: "ID", field: "id"},
                    {title: "Usuario", field: "usuario"},
                    {title: "Accion", field: "accion"},
                    {title: "Fecha", field: "fecha_hora"},
                    {title: "Modulo", field: "modulo"},
                    {title: "IP Publica", field: "ippublica"},
                    {title: "IP Local", field: "iplocal"},
                    {title: "Descripcion", field: "descripcion"},
                ],

            });

            $("#buscarData").keyup(function ()
            {
                $filter = $(this).val();
                table.setFilter([
                    [
                        {field: "id", type: "like", value: $filter},
                        {field: "usuario", type: "like", value: $filter},
                        {field: "accion", type: "like", value: $filter},
                        {field: "fecha_hora", type: "like", value: $filter},
                        {field: "modulo", type: "like", value: $filter},
                        {field: "ippublica", type: "like", value: $filter},
                        {field: "iplocal", type: "like", value: $filter},
                        {field: "descripcion", type: "like", value: $filter},
                    ]
                ]);
            });

        </script>

    </body>
</html>
