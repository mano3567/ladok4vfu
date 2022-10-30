<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Ladok</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Ladok</h2>
        <hr class="mb-2"/>
        <g:form name="basicsImportForm" action="index" method="POST">
            <div class="row mb-1">
                <div class="col-lg-2"><label for="edu">Välj Lärosäte:</label></div>
                <div class="col-lg-10"><g:select name="edu" from="${edus}" value="${edu?.name}" optionKey="name" optionValue="fullName" noSelection="['':'Välj ...']" class="form-control"/></div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"></div>
                <div class="col-lg-10"><g:submitButton name="startL3BasicsImport" value="Update the Basics" class="btn btn-primary float-right"/></div>
            </div>
        </g:form>
        <hr class="mb-1"/>
        <g:form name="educationImportForm" action="index" method="POST">
            <div class="row mb-1">
                <div class="col-lg-2"><label for="edu">Välj Lärosäte:</label></div>
                <div class="col-lg-10"><g:select name="edu" from="${edus}" value="${edu?.name}" optionKey="name" optionValue="fullName" noSelection="['':'Välj ...']" class="form-control"/></div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"></div>
                <div class="col-lg-10"><g:submitButton name="startL3EducationsImport" value="Uppdatera Utbildningar" class="btn btn-primary float-right"/></div>
            </div>
        </g:form>
        <hr class="mb-1"/>
        <g:form name="educationEventTestForm" action="index" method="POST">
            <div class="row mb-1">
                <div class="col-lg-2"></div>
                <div class="col-lg-10"><g:submitButton name="testEvent" value="Uppdatera UtbildningsTillfällen" class="btn btn-primary float-right"/></div>
            </div>
        </g:form>
    </body>
</html>
