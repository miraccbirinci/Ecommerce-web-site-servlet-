<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Web Final</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<div>
    <div style="margin-left: 175px">
        <c:out value="${requestScope.get('error')}"/>
    </div>
    <p style="position: absolute; top: 20px; right: 20px;">${sessionScope.get("username")}</p>
    <div class="jumbotron text-center">
        <h1>Welcome to the Box page!</h1>

        <c:choose>
            <c:when test="${sessionScope.get('username') != 'Not logged in'}">
                <a style="position: absolute;" href="/web_final_war_exploded/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="/web_final_war_exploded">Click here to login.</a><br>
                <p>If you have no account</p>
                <a href="addUser.jsp">Register</a>
            </c:otherwise>
        </c:choose>

    </div>
    <div class="container">
        <form action="${pageContext.request.contextPath}/buy?boxId=${boxIdList[status.index].getBoxId()}" method="post">
            <div class="card-body row" style="margin-bottom: 20px; width: 100%; align-content: center;">
                <c:set var="price" value="${totalPrice}" /> <%--totalpricetaki valueyi price icerisine atiyoruz--%>
                <h4 class="col-md-10">Total: $ <c:out value="${price}" escapeXml="false" /></h4>
                <button class="col-md-2" type="submit" style="width: 190px;">Buy</button>

            </div>
        </form>
        <div class="row" style="margin-top: 10px">
            <c:forEach var="product" items="${boxList}" varStatus="status"> <%--boxlisti dolasarak icerisindeki her elemani product olarak aliyoruz--%>
                <div class="col-md-6" style="width:50%">
                    <!--Burada her kullanıcının kendine ait boxunu göstermek için döngü oluşturuyoruz.
                    Satın alma işlemi için, sepetteki her ürünün satın alma butonuna, hangi box'ın butonu
                    tıklanıldığı anlaşılması için, boxId ekleniyor.
                    -->
                    <div class="card" style="border: 1px solid darkgray; margin-bottom: 20px;">
                        <img class="card-img-top" style="height: 15vw; width: auto; object-fit: cover; margin-top: 10px;" src="${product.getImage()}" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">${product.getProductName()}</h5>
                            <p class="card-text">${product.getDescription()}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">ID ${product.getProductId()}</li>
                            <li class="list-group-item">ONLY $${product.getProductPrice()}</li>
                            <li class="list-group-item">In stock ${product.getStock()}</li>

                        </ul>
                    </div>

                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>