<%@include file="includes/header.jsp" %>

<header class="masthead">
   <div class="container h-100">            
        <div class="row h-100">           
            <div class="col-lg-12 my-auto">
               <c:if test="${requestScope.errorMessage != null}">
                  <div class="alert alert-danger">
                     ${requestScope.errorMessage}
                  </div>
               </c:if>

               <form class="form-horizontal" method="POST">
                  <c:choose>
                     <c:when test="${requestScope.edit == 1}">
                        <input type="hidden" name="edit" value="1"/>
                     </c:when>
                  </c:choose>
                  <div class="form-group">
                     <label class="col-sm-2 control-label">Brand</label>
                     <div class="col-sm-10">
                        <c:choose>
                           <c:when test="${requestScope.edit == 1}">
                              <%-- <p class="control-label" style="text-align:left;">${requestScope.brand}</p> --%>
                              <output>${requestScope.brand}</output><input type="hidden" name="brand" value="${requestScope.brand}"/>
                           </c:when>
                           <c:otherwise>
                              <input type="text" class="form-control" name="brand" placeholder="Brand" 
                                 <c:if test="${requestScope.brand != null}"> value="${requestScope.brand}" </c:if>
                              />               
                           </c:otherwise>
                        </c:choose>
                     </div>
                  </div> 
                  <div class="form-group">
                     <label class="col-sm-2 control-label">Color</label>
                     <div class="col-sm-10">
                        <input type="text" class="form-control" name="color" placeholder="Color" 
                           <c:if test="${requestScope.color != null}"> value="${requestScope.color}" </c:if>
                        />
                     </div>
                  </div> 
                  <div class="form-group">
                     <label class="col-sm-2 control-label">Horsepower</label>
                     <div class="col-sm-10">
                        <input type="text" class="form-control" name="horsePower" placeholder="Horsepower" 
                           <c:if test="${requestScope.horsePower != null}"> value="${requestScope.horsePower}" </c:if>
                        />
                     </div>
                  </div> 
                  <div class="form-group">
                     <label class="col-sm-2 control-label">Price</label>
                     <div class="col-sm-10">
                        <input type="text" class="form-control" name="price" placeholder="Price" 
                           <c:if test="${requestScope.price != null}"> value="${requestScope.price}" </c:if>
                        />
                     </div>
                  </div>
                  <div class="form-group">
                     <div class="col-sm-offset-2 col-sm-10">
                        <button type="default" class="btn btn-default">
                           <c:choose>
                              <c:when test="${requestScope.edit == null}">
                                 Create
                              </c:when>
                              <c:otherwise>
                                 Update
                              </c:otherwise>
                           </c:choose>
                        </button>
                     </div>
                  </div>
               </form>
            </div>
        </div>
   </div>
</header>

<%@include file="includes/footer.jsp" %>