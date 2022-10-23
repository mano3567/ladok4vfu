<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Ladok</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Ladok</h2>
        <hr class="mb-2"/>
        <h3>${status.keySet().size()}</h3>
        <g:each in="${status.keySet().sort{it.fullName}}" var="edu">
            <div class="row mb-1">
                <div class="col-lg-3">${edu.fullName}</div>
                <div class="col-lg-3">${status.get(edu).get('enabled')}</div>
                <div class="col-lg-3">${status.get(edu).get('expirationDate')}</div>
                <div class="col-lg-3">${status.get(edu).get('path')}</div>
            </div>
        </g:each>
    </body>
</html>
