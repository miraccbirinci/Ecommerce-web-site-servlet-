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

<a href="box"
   style="position: absolute; top: 20px; left: 20px; width: 32px; height: 32px;">
    <svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="shopping-cart"
         class="svg-inline--fa fa-shopping-cart fa-w-18" role="img" xmlns="http://www.w3.org/2000/svg"
         viewBox="0 0 576 512">
        <path fill="currentColor"
              d="M528.12 301.319l47.273-208C578.806 78.301 567.391 64 551.99 64H159.208l-9.166-44.81C147.758 8.021 137.93 0 126.529 0H24C10.745 0 0 10.745 0 24v16c0 13.255 10.745 24 24 24h69.883l70.248 343.435C147.325 417.1 136 435.222 136 456c0 30.928 25.072 56 56 56s56-25.072 56-56c0-15.674-6.447-29.835-16.824-40h209.647C430.447 426.165 424 440.326 424 456c0 30.928 25.072 56 56 56s56-25.072 56-56c0-22.172-12.888-41.332-31.579-50.405l5.517-24.276c3.413-15.018-8.002-29.319-23.403-29.319H218.117l-6.545-32h293.145c11.206 0 20.92-7.754 23.403-18.681z">
        </path>
    </svg>
</a>
<p style="position: absolute; top: 20px; right: 20px;">${sessionScope.get("username")}</p>
<div class="jumbotron text-center">
    <h1>Welcome to the Products page!</h1>
    <!--
        Burada user'ın giriş yapıp yapmadığı kontrol ediliyor.
    -->
    <c:choose>
        <c:when test="${sessionScope.get('username')}">
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
    <h1 style="text-align: center;">Our Products!</h1>
    <div id="products" class="row">
        <c:forEach var="product" items="${productList}">
            <div style="text-align: center" >
                <!--
                    Burada sitede bulunan bütün productların listelenmesi yapılıyor.
                    Her bir product'ın listeye eklenebilmesi için, bir user'a ihtiyaç duyuyor. Eğer user yoksa sepete
                    ürün eklenemiyor.
                    Ürünün sepete eklenmesi için, hangi user'ın giriş yaptığı ve hangi product'ın sepete eklenmek istendiği
                    bilgisi, her bir product ile bağlanıyor.
                -->
                <form action="${pageContext.request.contextPath}/box?userId=${userID}&productId=${product.getProductId()}" method="post"> <%--boxcontrollerin postunu cagir parametre olarak userid ve product id ver--%>
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
                        <div class="card-body" style="margin-bottom: 20px;">
                            <button type="submit">Add to Box</button>
                        </div>
                    </div>
                </form>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>